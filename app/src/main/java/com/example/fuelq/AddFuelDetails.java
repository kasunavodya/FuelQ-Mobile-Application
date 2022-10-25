/*
 * Developer ID      :   IT19144986
 * Developer Name    :   H.M. Kasuni Navodya
 * Function          :   Add Fuel Details (fuelType, arrivingDate, arrivingTime, arrivedLitres)
 * Implemented Date  :   19th October 2022
*/

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

    //Variable List
    private EditText arrivingDate, arrivingTime, arrivedLitres;
    private String fuelType;

    //EndPoint
    String URL = EndPointURL.GET_ALL_FUEL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_fuel_details);

        //Set the titleBar icon
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);

        //Set the spinner details
        final List<String> ownerfuelType = Arrays.asList("Petrol", "Diesel");
        final Spinner spinnerFuelType = findViewById(R.id.spinner_fuelType);

        //Get all UI components to variables
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
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        if(arrivingDate.equals("") || arrivingTime.equals("") || arrivedLitres.equals("")) {
            Toast.makeText(AddFuelDetails.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
        }else{
            final Button btnAddDetails = findViewById(R.id.btn_addDetails);
            btnAddDetails.setOnClickListener(view -> {
                Intent activityIntent = new Intent(AddFuelDetails.this, ViewOwnerFuelDetails.class);
                AddFuelDetails.this.startActivity(activityIntent);

                // calling a method to post the data and passing our name and job.
                postDataUsingVolley(arrivingDate.getText().toString(), arrivingTime.getText().toString(), arrivedLitres.getText().toString(), fuelType);
                Toast.makeText(AddFuelDetails.this, "Data Registered Successfully", Toast.LENGTH_LONG).show();
            });
        }
    }

    /**********************************************************************************
     * @DeveloperID   :   IT19144986
     * @Developer     :   H.M. Kasuni Navodya
     * @Function      :   Submit the Fuel Details to database
     **********************************************************************************/
    private boolean postDataUsingVolley(String arrivingDate, String arrivingTime, String arrivedLitres, String fuelType) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

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