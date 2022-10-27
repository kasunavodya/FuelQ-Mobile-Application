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
import com.example.fuelq.Model.Customer;
import com.example.fuelq.Model.Fuel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ReqFuelVolume extends AppCompatActivity {

    //Variable List
    String mngId = "";
    TextView cusMongoID, sheadRemainLiters, tokenNumber;
    EditText requestFuelVolume;
    String email = "";
    String customerId = "";
    //Fuel FuelModel;
    Fuel FuelModel = new Fuel();
    Customer CustomerModel = new Customer();
    String remainLiters;
    Integer finalLiters = 0;
    Integer custToken = 0;

    //EndPoint
    String CustomerAPI = EndPointURL.GET_ALL_CUSTOMER;
    String UpdateCustomerAPI = EndPointURL.UPDATE_CUSTOMER_BY_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.req_fuel_volume);

        //Get all UI components to variables
        requestFuelVolume  = findViewById(R.id.editTxt_f_vol);
        cusMongoID         = findViewById(R.id.txt_customerMongoID);
        sheadRemainLiters  = findViewById(R.id.txt_remainLiters);
        tokenNumber = findViewById(R.id.txt_token);

        //Set the titleBar icon
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);

        //Get Mongo DB address
        Intent intent = getIntent();
        String fuelId = intent.getExtras().getString("Key");
        email = intent.getExtras().getString("Email");
        remainLiters = intent.getExtras().getString("remainLitres");
        String fuelStation = intent.getExtras().getString("fuelStation");

        sheadRemainLiters.setText(remainLiters);

        //get customer information
        getAllCustomerInfo(fuelStation);

        String customerMDBId = cusMongoID.getText().toString();

        //Get the remaining fuel volume
        getFuelDataForRemainingVol(fuelId);

        //Handling the fuel pump button to update the fuel volume
        final Button btnPump = findViewById(R.id.btn_pump);
        btnPump.setOnClickListener(view -> {
            if(requestFuelVolume.getText().toString().equals("")){
                Toast.makeText(this, "Please Enter a Value!", Toast.LENGTH_SHORT).show();
            }else{
                finalLiters = Integer.parseInt(sheadRemainLiters.getText().toString()) - Integer.parseInt(requestFuelVolume.getText().toString());
                if(finalLiters > 0){
                    getCustomerData(cusMongoID.getText().toString());
                    updateFuelRemainVol(fuelId, Integer.toString(finalLiters));
                    Integer number =Integer.parseInt(tokenNumber.getText().toString()) + 1;
                    updateCustomerInfo(cusMongoID.getText().toString(), number.toString(), fuelStation);
                    System.out.println(cusMongoID.getText().toString() + " MongoCustomer");
                }else {
                    Toast.makeText(this, "No enough liters available!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**********************************************************************************
     * @DeveloperID   :   IT19016108
     * @Developer     :   Alwis T.A.D.T.N.D
     * @Function      :   Get customer details by id
     **********************************************************************************/

    private void getAllCustomerInfo(String fuelStation) {
       RequestQueue queue = Volley.newRequestQueue(ReqFuelVolume.this);
    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, CustomerAPI, null, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject responseObj = response.getJSONObject(i);
                    String custId = responseObj.getString("id");
                    String custEmail = responseObj.getString("customerEmail");
                    String token = responseObj.getString("token");
                    String station = responseObj.getString("shedQ");

                    if(email.equals(custEmail)){
                        customerId = custId;
                    }
                    if((Integer.parseInt(token) >= custToken) && station.equals(fuelStation)) {
                        custToken = Integer.parseInt(token);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            cusMongoID.setText(customerId);
            tokenNumber.setText(custToken.toString());
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

    public void updateFuelRemainVol(String id, String remainVol){
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
//                        "\"remainLitres\":" + "\"" + Integer.toString(remainVol) + "\"" +"}";
                        "\"remainLitres\":" + "\"" + remainVol + "\"" +"}";
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

    public void updateCustomerInfo(String id, String newToken, String station){
        RequestQueue queue = Volley.newRequestQueue(this);

        String updateCustomerdata = EndPointURL.UPDATE_CUSTOMER_BY_ID;
        updateCustomerdata = updateCustomerdata + id.trim();

        System.out.println(id + " customer ID " + newToken + " toekn " + station + " station ");

        //Define data and time
          DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm:ss");
          LocalDateTime arrTime = LocalDateTime.now();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, updateCustomerdata,
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
                String body="{\"id\":" + "\"" + CustomerModel.getId() + "\"," +
                        "\"customerEmail\":" + "\"" + CustomerModel.getCustomerEmail() + "\"," +
                        "\"customerPassword\":" + "\"" + CustomerModel.getCustomerPassword() + "\"," +
                        "\"customerVehicleNumber\":" + "\"" + CustomerModel.getCustomerVehicleNumber() + "\"," +
                        "\"customerVehicleType\":" + "\"" + CustomerModel.getCustomerVehicleType() + "\"," +
                        "\"customerFuelType\":" + "\"" + CustomerModel.getCustomerFuelType() + "\"," +
                        "\"awaitingTime\":" + "\"" + CustomerModel.getAwaitingTime() + "\"," +
                        "\"token\":" + "\"" + Integer.parseInt(newToken) + "\"," +
                        "\"arrivalTimeQ\":" + "\"" + dtf.format(arrTime) + "\"," +
                        "\"departTimeQ\":" + "\"" + CustomerModel.getDepartTimeQ() + "\"," +
                        "\"requestedLitres\":" + "\"" + CustomerModel.getRequestedLitres() + "\"," +
                        "\"shedQ\":" + "\"" + station + "\"" +"}";
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

    private void getCustomerData(String id) {

        RequestQueue queue = Volley.newRequestQueue(this);

        String getCustomerByIdAPI = EndPointURL.UPDATE_CUSTOMER_BY_ID + id.trim();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, getCustomerByIdAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Customer customerModel = new Customer(
                                    jsonObject.getString("id"),
                                    jsonObject.getString("customerEmail"),
                                    jsonObject.getString("customerPassword"),
                                    jsonObject.optInt("customerVehicleNumber"),
                                    jsonObject.getString("customerVehicleType"),
                                    jsonObject.getString("customerFuelType"),
                                    jsonObject.getString("awaitingTime"),
                                    jsonObject.optInt("token"),
                                    jsonObject.getString("arrivalTimeQ"),
                                    jsonObject.getString("departTimeQ"),
                                    jsonObject.optInt("requestedLitres"),
                                    jsonObject.getString("shedQ")
                            );
                            CustomerModel = customerModel;

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

}
