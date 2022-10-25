package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuelq.EndPoints.EndPointURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class AddFeedback extends AppCompatActivity {

    TextView autoFeedback;
    EditText userFeedback;
    RatingBar rbStars;
    String star;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_feedback);

        autoFeedback = findViewById(R.id.txt_autoStarFeedback);
        rbStars = findViewById(R.id.ratingBar_feedback);
        userFeedback = findViewById(R.id.editText_userFeedback);

        rbStars.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if(rating==0)
            {
               autoFeedback.setText("Very Dissatisfied");
            }
            else if(rating==1)
            {
                autoFeedback.setText("Dissatisfied");
            }
            else if(rating==2 || rating==3)
            {
                autoFeedback.setText("OK");
            }
            else if(rating==4)
            {
                autoFeedback.setText("Satisfied");
            }
            else if(rating==5)
            {
                autoFeedback.setText("Very Satisfied");
            }
            else
            {

            }
        });

        final Button Rate = findViewById(R.id.btn_rate);
        Rate.setOnClickListener(view -> {
            Intent activityIntent = new Intent(AddFeedback.this, AddFeedback.class);
            AddFeedback.this.startActivity(activityIntent);

            // calling a method to post the data and passing our name and job.
            postDataUsingVolley(autoFeedback.getText().toString(), userFeedback.getText().toString());
            Toast.makeText(AddFeedback.this, "Thank you for the feedback", Toast.LENGTH_LONG).show();
        });
    }

    private boolean postDataUsingVolley(String autoFeedback, String userFeedback) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String URL = EndPointURL.GET_ALL_FEEDBACK;

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", null);
            jsonObject.put("starFeedback", autoFeedback);
            jsonObject.put("userFeedback", userFeedback);
            final String mRequestBody = jsonObject.toString();


            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
                Log.i("LOG_VOLLEY", response);
                try {
                    JSONObject singleObject = new JSONObject(response);
                } catch (JSONException e) {

                }
            }, error -> Log.e("LOG_VOLLEY", error.toString())) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


}
