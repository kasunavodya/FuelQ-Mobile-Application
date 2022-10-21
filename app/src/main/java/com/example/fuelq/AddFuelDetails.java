package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Arrays;
import java.util.List;

public class AddFuelDetails extends AppCompatActivity {

    private EditText arrivingDate, arrivingTime, arrivedLitres;
    private String fuelType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_fuel_details);

        final List<String> ownerfuelType = Arrays.asList("Octane 92", "Octane 95", "Super Diesel", "Auto Diesel", "Kerosene");

        final Spinner spinnerFuelType = findViewById(R.id.spinner_fuelType);

        arrivingDate = findViewById(R.id.editTxt_date);
        arrivingTime = findViewById(R.id.editTxtTime);
        arrivedLitres = findViewById(R.id.editTxt_arriving);

        ArrayAdapter adapterFuelType = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, ownerfuelType);
        adapterFuelType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFuelType.setAdapter(adapterFuelType);

        spinnerFuelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fuelType = spinnerFuelType.getSelectedItem().toString();
                //Toast.makeText(getApplicationContext(), "You selected: " + customerFuelType, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Button btnAddDetails = findViewById(R.id.btn_addDetails);
        btnAddDetails.setOnClickListener(view -> {
            Intent activityIntent = new Intent(AddFuelDetails.this, ViewOwnerFuelDetails.class);
            AddFuelDetails.this.startActivity(activityIntent);

            // calling a method to post the data and passing our name and job.
            postDataUsingVolley(arrivingDate.getText().toString(), arrivingTime.getText().toString(), arrivedLitres.getText().toString(), fuelType);
            Toast.makeText(AddFuelDetails.this, "Data Registered Successfully", Toast.LENGTH_LONG).show();
        });
    }

    private boolean postDataUsingVolley(String arrivingDate, String arrivingTime, String arrivedLitres, String fuelType) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String URL = EndPointURL.GET_ALL_FUEL;

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", null);
            jsonObject.put("arrivingDate", arrivingDate);
            jsonObject.put("arrivingTime", arrivingTime);
            jsonObject.put("arrivedLitres", arrivedLitres);
            jsonObject.put("fuelType", fuelType);
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