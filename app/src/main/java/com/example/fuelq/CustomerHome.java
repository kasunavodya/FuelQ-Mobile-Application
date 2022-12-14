/*
 * Developer ID      :   IT19016108
 * Developer Name    :   Alwis T.A.D.T.N.D
 * Function          :   Function for searching the fuel queue
 * Implemented Date  :   16th October 2022
 */

package com.example.fuelq;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import java.util.List;

public class CustomerHome extends AppCompatActivity {

    //EndPoint list
    String api = EndPointURL.GET_ALL_CUSTOMER;
    String OwnerAPI = EndPointURL.GET_ALL_OWNER;
    String FuelAPI = EndPointURL.GET_ALL_FUEL;

    //Variable list
    Spinner fuelStationSpinner;
    ArrayList<String> fuelStationsList, sample;
    String SelectedFuelStation;
    CustomerDBHelper customerDBHelper;
    String fuelType = "";
    String selectedFuelType = "";
    TextView availability, fuelStationSelected;
    Boolean ava = false;
    String status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_home);

        customerDBHelper = new CustomerDBHelper(this);
        fuelStationSpinner = findViewById(R.id.spinner_station);
        availability = findViewById(R.id.txt_fuelAvailability);
        fuelStationSelected = findViewById(R.id.txt_selectedFuelStation);

        //Set the titleBar icon
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);

        final Button btnCheck = findViewById(R.id.btn_check);

        //Get Customer email Address
        Intent intent = getIntent();
        String email = intent.getExtras().getString("email");

        //Initialize the array list
        fuelStationsList = new ArrayList<String>();

        //fetch all fuel station info
        getAllFuelInfo();

        //method to get fuel type
        fuelType = customerDBHelper.getFuelType(email);

        btnCheck.setOnClickListener(view -> {
            getRemainLiters(fuelType, fuelStationSelected.getText().toString());
            Intent activityIntent = new Intent(CustomerHome.this, ViewQueueFuelDetails.class);
            activityIntent.putExtra("fuelStation", fuelStationSelected.getText().toString());
            activityIntent.putExtra("FuelType", fuelType);
            activityIntent.putExtra("Email", email);
            activityIntent.putExtra("Availability", availability.getText().toString());
            CustomerHome.this.startActivity(activityIntent);
        });
    }

    private void getRemainLiters(String fuelType, String fuelStation) {
        RequestQueue queue = Volley.newRequestQueue(CustomerHome.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, FuelAPI, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject responseObj = response.getJSONObject(i);
                        String fuelRemLiters = responseObj.getString("remainLitres");
                        String station = responseObj.getString("fuelStation");
                        String fType = responseObj.getString("fuelType");
                        if(fuelType.equals(fType) && station.equals(fuelStation)){
                            if(fuelRemLiters.equals("0")){
                                ava = false;
                            }else{
                                ava = true;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CustomerHome.this, "Fail to get the fuel stations data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    /**********************************************************************************
     * @DeveloperID   :   IT19016108
     * @Developer     :   Alwis T.A.D.T.N.D
     * @Function      :   Get all fuel stations to spinner
     **********************************************************************************/

    private void getAllFuelInfo() {
        RequestQueue queue = Volley.newRequestQueue(CustomerHome.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, OwnerAPI, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject responseObj = response.getJSONObject(i);
                        String fuelStation = responseObj.getString("ownerFuelStation");

                        fuelStationsList.add(fuelStation);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                fuelStationSpinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fuelStationsList));
                fuelStationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        SelectedFuelStation = fuelStationSpinner.getSelectedItem().toString();
                        fuelStationSelected.setText(SelectedFuelStation);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                Log.i("LOCATIONS", "onResponse: " + fuelStationsList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CustomerHome.this, "Fail to get the fuel stations data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }
}