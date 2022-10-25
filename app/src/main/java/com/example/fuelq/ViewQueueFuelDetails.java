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

    ArrayList<String> allFuelList;
    TextView inputFuelType, location, id, remainFuelLiters;
    String FuelAPI = EndPointURL.GET_ALL_FUEL;
    String OwnerAPI = EndPointURL.GET_ALL_OWNER;
    String mngId = "";
    String stationLocation = "";
//    Integer liters = 0;
    String liters = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_queue_fuel_details);
        id = findViewById(R.id.txt_fuelStation);
        inputFuelType = findViewById(R.id.txt_fuel);
        location = findViewById(R.id.txt_fuelLocation);
        remainFuelLiters = findViewById(R.id.txt_f_vol);

        //Get Customer email Address
        Intent intent = getIntent();
        String fuelStation = intent.getExtras().getString("fuelStation");
        String fuelType = intent.getExtras().getString("FuelType");
        allFuelList = new ArrayList<>();
        Log.i("station", "onCreate: " + fuelStation);
        Log.i("fuelType", "onCreate: " + fuelType);

        //Get fuel station details
        getAllFuelData(fuelType, fuelStation);
        getOwnerStationData(fuelStation);

        Log.i("liters", "onCreate: " + liters);
        Log.i("location", "onCreate: " + stationLocation);
        Log.i("MID", "onCreate: " + mngId);
        //Set Text Values
        id.setText(fuelStation);
        inputFuelType.setText(fuelType);
//        location.setText(stationLocation);


        final Button btnJoin = findViewById(R.id.btn_join);
        btnJoin.setOnClickListener(view -> {
            Intent activityIntent = new Intent(ViewQueueFuelDetails.this, ReqFuelVolume.class);
            ViewQueueFuelDetails.this.startActivity(activityIntent);
        });

        final Button btnExit = findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(view -> {
            Intent activityIntent = new Intent(ViewQueueFuelDetails.this, CustomerHome.class);
            ViewQueueFuelDetails.this.startActivity(activityIntent);
        });

    }

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


