/*
 * Developer ID      :   IT19144986
 * Developer Name    :   H.M. Kasuni Navodya
 * Function          :   View Owner Fuel Details (FuelStation, FuelLocation, OwnerName, OwnerContact)
 * Implemented Date  :   19th October 2022
 */

package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ViewOwnerFuelDetails extends AppCompatActivity {

    //Variable List
    TextView FuelStation, FuelLocation, OwnerName, OwnerContact;

    private fuelAdapter adapter;
    ArrayList<Fuel> allFuelList;
    RecyclerView rcvMain;

    private String userEmail;

    //EndPoint list
    String FuelAPI = EndPointURL.GET_ALL_FUEL;
    String OwnerEmailAPI = EndPointURL.GET_OWNER_BY_EMAIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_owner_fuel_details);

        //Set the titleBar icon
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);

        //pass the email address
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("Email");

        //Get all UI components to variables
        FuelStation = findViewById(R.id.txt_fuelStation);
        FuelLocation = findViewById(R.id.txt_fuelLocation);
        OwnerName = findViewById(R.id.txt_fuelOwner);
        OwnerContact = findViewById(R.id.txt_fuelOwnerContact);

        rcvMain = findViewById(R.id.rcvMain);
        allFuelList = new ArrayList<>();

        buildRecyclerView();
        getOwnerData(userEmail);
        getAllFuelData();

        //Handle the floating button navigation
        final FloatingActionButton btnAdd = findViewById(R.id.floating_add);
        btnAdd.setOnClickListener(view -> {
            Intent activityIntent = new Intent(ViewOwnerFuelDetails.this, AddFuelDetails.class);
            ViewOwnerFuelDetails.this.startActivity(activityIntent);
        });

        //Handle the refresh button navigation
        final Button btnRefresh = findViewById(R.id.btn_refresh);
        btnRefresh.setOnClickListener(view -> {
            Intent activityIntent = new Intent(ViewOwnerFuelDetails.this, ViewOwnerFuelDetails.class);
            ViewOwnerFuelDetails.this.startActivity(activityIntent);
        });
    }

    /**********************************************************************************
     * @DeveloperID   :   IT19144986
     * @Developer     :   H.M. Kasuni Navodya
     * @Function      :   Display owner details by owner ID
     **********************************************************************************/

    private void getOwnerData(String ownerEmail) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String getAPI = OwnerEmailAPI + ownerEmail.trim();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, getAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Owner ownerModel = new Owner(
                                    jsonObject.getString("ownerName"),
                                    jsonObject.getString("ownerEmail"),
                                    jsonObject.getString("ownerPassword"),
                                    jsonObject.getString("ownerContact"),
                                    jsonObject.getString("ownerFuelStation"),
                                    jsonObject.getString("ownerLocation")
                            );

                            FuelStation.setText("Fuel " + ownerModel.getOwnerFuelStation());
                            FuelLocation.setText(ownerModel.getOwnerLocation() + " Fuel Station");
                            OwnerName.setText(ownerModel.getOwnerName());
                            OwnerContact.setText(ownerModel.getOwnerContact());

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
     * @DeveloperID   :   IT19144986
     * @Developer     :   H.M. Kasuni Navodya
     * @Function      :   Display All fuel details
     **********************************************************************************/

    private void getAllFuelData() {

        RequestQueue queue = Volley.newRequestQueue(ViewOwnerFuelDetails.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, FuelAPI, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                rcvMain.setVisibility(View.VISIBLE);
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
                        allFuelList.add(new Fuel(id, fuelType, fuelStation, arrivingDate, arrivingTime, arrivedLitres, remainLitres));
                        buildRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewOwnerFuelDetails.this, "Fail to get the fuel data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    //handle recyclerView
    private void buildRecyclerView() {

        adapter = new fuelAdapter(allFuelList, ViewOwnerFuelDetails.this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rcvMain.setHasFixedSize(true);
        rcvMain.setLayoutManager(manager);
        rcvMain.setAdapter(adapter);
    }
}