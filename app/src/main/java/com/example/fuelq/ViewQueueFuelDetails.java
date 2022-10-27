/*
 * Developer ID      :   IT19016108
 * Developer Name    :   Alwis T.A.D.T.N.D
 * Function          :   View queue Fuel Details
 * Implemented Date  :   17th October 2022
 */

package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuelq.EndPoints.EndPointURL;
import com.example.fuelq.Model.Fuel;
import com.example.fuelq.Model.Owner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewQueueFuelDetails extends AppCompatActivity {

    //Variable List
    ArrayList<String> allFuelList;
   private TextView inputFuelType, location, id, remainFuelLiters, mongoID;
   private String stationLocation = "";
   private String liters = "";
   private String mngId = "";
   private Button availability;

    //EndPoint list
    String FuelAPI = EndPointURL.GET_ALL_FUEL;
    String OwnerAPI = EndPointURL.GET_ALL_OWNER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_queue_fuel_details);

        //Get all UI components to variables
        id = findViewById(R.id.txt_fuelStation);
        inputFuelType = findViewById(R.id.txt_fuel);
        location = findViewById(R.id.txt_fuelLocation);
        remainFuelLiters = findViewById(R.id.txt_f_vol);
        mongoID = findViewById(R.id.txt_mongoid);
        availability = findViewById(R.id.btn_available);

        //Get Customer email Address
        Intent intent = getIntent();
        String fuelStation = intent.getExtras().getString("fuelStation");
        String fuelType = intent.getExtras().getString("FuelType");
        String custEmail = intent.getExtras().getString("Email");
        String availabilityCheck = intent.getExtras().getString("Availability");
        System.out.println("Available status" + availabilityCheck);

        allFuelList = new ArrayList<>();

        //Get fuel station details
        getAllFuelData(fuelType, fuelStation);
        getOwnerStationData(fuelStation);

        //Set Text Values
        id.setText(fuelStation);
        inputFuelType.setText(fuelType);
        availability.setText(availabilityCheck);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);

        //Handle the join queue button navigation
        final Button btnJoin = findViewById(R.id.btn_join);
        btnJoin.setOnClickListener(view -> {
            String fuelMngId = mongoID.getText().toString();
            String remain = remainFuelLiters.getText().toString();
            System.out.println(remain + "remain liters que");
            if(remain.equals("0")){
                Toast.makeText(this, "No fuel remaining, please try next time!", Toast.LENGTH_SHORT).show();
            }else {
                Intent joinIntent = new Intent(ViewQueueFuelDetails.this, ReqFuelVolume.class);
                joinIntent.putExtra("Key", fuelMngId);
                joinIntent.putExtra("Email", custEmail);
                joinIntent.putExtra("remainLitres", remain);
                joinIntent.putExtra("fuelStation", fuelStation);
                ViewQueueFuelDetails.this.startActivity(joinIntent);
            }
        });

        //Handle the exit queue button navigation
        final Button btnExit = findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(view -> {
            Intent existIntent = new Intent(ViewQueueFuelDetails.this, CustomerHome.class);
            ViewQueueFuelDetails.this.startActivity(existIntent);
        });

    }



    /**********************************************************************************
     * @DeveloperID   :   IT19016108
     * @Developer     :   Alwis T.A.D.T.N.D
     * @Function      :   Get fuel details according the selected fuel station
     **********************************************************************************/

    private void getAllFuelData(String fuel, String station) {

        RequestQueue queue = Volley.newRequestQueue(ViewQueueFuelDetails.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, FuelAPI, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject responseObj = response.getJSONObject(i);
                        String id = responseObj.getString("id");
                        String fuelType = responseObj.getString("fuelType");
                        String fuelStation = responseObj.getString("fuelStation");
                        String arrivingDate = responseObj.getString("arrivingDate");
                        String arrivingTime = responseObj.getString("arrivingTime");
                        String arrivedLitres = responseObj.getString("arrivedLitres");
                        String remainLitres = responseObj.getString("remainLitres");

                        if(fuel.equals(fuelType) && station.equals(fuelStation)) {
                              mngId = id;
                              liters = remainLitres;
                              remainFuelLiters.setText(liters);
                              mongoID.setText(mngId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewQueueFuelDetails.this, "Fail to get the fuel data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    /**********************************************************************************
     * @DeveloperID   :   IT19016108
     * @Developer     :   Alwis T.A.D.T.N.D
     * @Function      :   Get the relevant station details
     **********************************************************************************/

    private void getOwnerStationData(String station) {

        RequestQueue queue = Volley.newRequestQueue(ViewQueueFuelDetails.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, OwnerAPI, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject responseObj = response.getJSONObject(i);
                        String id = responseObj.getString("id");
                        String ownerFuelStation = responseObj.getString("ownerFuelStation");
                        String ownerLocation = responseObj.getString("ownerLocation");

                        if (station.equals(ownerFuelStation)) {
                            stationLocation = ownerLocation;
                            location.setText(stationLocation);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewQueueFuelDetails.this, "Fail to get the station data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }
}


