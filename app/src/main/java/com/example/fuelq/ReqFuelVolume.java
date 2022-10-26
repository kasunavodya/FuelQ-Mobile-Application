/*
 * Developer ID      :   IT19016108
 * Developer Name    :   Alwis T.A.D.T.N.D
 * Function          :   Handling the remaining fuel volume with requested fuel volume
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuelq.EndPoints.EndPointURL;
import com.example.fuelq.Model.Fuel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ReqFuelVolume extends AppCompatActivity {

    //Variable List
    String mngId = "";
    TextView cusMongoID;
    EditText requestedFuelVolume;
    String email = "";
    String customerId = "";
    Fuel FuelModel;
    Integer remainLiters = 0;
    Integer finalLiters = 0;

    //EndPoint
    String CustomerAPI = EndPointURL.GET_ALL_CUSTOMER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.req_fuel_volume);

        //Get all UI components to variables
        requestedFuelVolume = findViewById(R.id.editTxt_f_vol);
        cusMongoID = findViewById(R.id.txt_customerMongoID);

        //get customer information
        getAllCustomerInfo();

        //Set the titleBar icon
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);

        //Get Mongo DB address
        Intent intent = getIntent();
        String fuelId = intent.getExtras().getString("Key");
        email = intent.getExtras().getString("Email");
        remainLiters = intent.getIntExtra("remainLiters", 0);

        String customerMDBId = cusMongoID.getText().toString();

        //Get the remaining fuel volume
        getFuelDataForRemainingVol(fuelId);

        //Handling the fuel pump button to update the fuel volume
        final Button btnPump = findViewById(R.id.btn_pump);
        btnPump.setOnClickListener(view -> {
            if(requestedFuelVolume.equals("")){
                Toast.makeText(this, "Please Enter a Value!", Toast.LENGTH_SHORT).show();
            }else{
                finalLiters = remainLiters - Integer.parseInt(requestedFuelVolume.getText().toString());
                Log.i("FUELCOUNT", "onCreate: " + requestedFuelVolume.getText().toString());
                updateFuelRemainVol(fuelId, finalLiters);
            }
        });
    }

    /**********************************************************************************
     * @DeveloperID   :   IT19016108
     * @Developer     :   Alwis T.A.D.T.N.D
     * @Function      :   Get customer details by id
     **********************************************************************************/

    private void getAllCustomerInfo() {
        RequestQueue queue = Volley.newRequestQueue(ReqFuelVolume.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, CustomerAPI, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject responseObj = response.getJSONObject(i);
                        String custId = responseObj.getString("id");
                        String custEmail = responseObj.getString("customerEmail");

                        if(email.equals(custEmail)){
                                customerId = custId;
                                cusMongoID.setText(customerId);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReqFuelVolume.this, "Fail to get the fuel data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    /**********************************************************************************
     * @DeveloperID   :   IT19016108
     * @Developer     :   Alwis T.A.D.T.N.D
     * @Function      :   Get fuel data for remaining fuel volume
     **********************************************************************************/

    private void getFuelDataForRemainingVol(String id) {

        RequestQueue queue = Volley.newRequestQueue(this);

        String getfuelByIdAPI = EndPointURL.UPDATE_FUEL_BY_ID + id.trim();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, getfuelByIdAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Fuel fuelModel = new Fuel(
                                    jsonObject.getString("id"),
                                    jsonObject.getString("fuelType"),
                                    jsonObject.getString("fuelStation"),
                                    jsonObject.getString("arrivingDate"),
                                    jsonObject.getString("arrivingTime"),
                                    jsonObject.getString("arrivedLitres"),
                                    jsonObject.getString("remainLitres")
                            );
                            FuelModel = fuelModel;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api", "onErrorResponse: "+error.getLocalizedMessage());
            }
        });
        queue.add(stringRequest);
    }


    /**********************************************************************************
     * @DeveloperID   :   IT19016108
     * @Developer     :   Alwis T.A.D.T.N.D
     * @Function      :   Updating the remaining fuel volume
     **********************************************************************************/

    public void updateFuelRemainVol(String id, Integer remainVol){
        RequestQueue queue = Volley.newRequestQueue(this);

        String updatefuelVolumeByIdAPI = EndPointURL.UPDATE_FUEL_BY_ID;
        updatefuelVolumeByIdAPI = updatefuelVolumeByIdAPI + id.trim();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, updatefuelVolumeByIdAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("api", "onResponse: "+response);
                        Toast.makeText(ReqFuelVolume.this, "Fuel Details Updated!", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api", "onErrorResponse: "+error.getLocalizedMessage());
            }
        }) {
            @Override
            public byte[] getBody() {
                String body="{\"id\":" + "\"" + FuelModel.getId() + "\"," +
                       "\"fuelType\":" + "\"" + FuelModel.getFuelType() + "\"," +
                        "\"fuelStation\":" + "\"" + FuelModel.getFuelStation() + "\"," +
                        "\"arrivingDate\":" + "\"" + FuelModel.getArrivingDate() + "\"," +
                        "\"arrivingTime\":" + "\"" + FuelModel.getArrivingTime() + "\"," +
                        "\"arrivedLitres\":" + "\"" + FuelModel.getArrivedLitres() + "\"," +
                        "\"remainLitres\":" + "\"" + Integer.toString(remainVol) + "\"" +"}";
                Log.e("UPDATE DATA", "getBody: "+body.getBytes());
                return body.getBytes();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers=new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        queue.add(stringRequest);
    }

}
