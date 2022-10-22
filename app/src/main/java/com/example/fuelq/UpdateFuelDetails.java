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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuelq.EndPoints.EndPointURL;
import com.example.fuelq.Model.Fuel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateFuelDetails extends AppCompatActivity {

    private EditText arrivingDate, arrivingTime, arrivedLitres;
    String fuelType;
    Fuel FuelModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_fuel_details);

        final List<String> ownerfuelType = Arrays.asList("Octane 92", "Octane 95", "Super Diesel", "Auto Diesel", "Kerosene");

        final Spinner spinnerFuelType = findViewById(R.id.spinner_fuelType);

        arrivingDate = findViewById(R.id.editTxt_date);
        arrivingTime = findViewById(R.id.editTxt_time);
        arrivedLitres = findViewById(R.id.editTxt_arriving);

        String fuelId = "63526fcce77068ba135982e0";
        updateQueueDetails(fuelId);

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

        final Button btnAddDetails = findViewById(R.id.btn_update);
        btnAddDetails.setOnClickListener(view -> {
            Intent activityIntent = new Intent(UpdateFuelDetails.this, ViewOwnerFuelDetails.class);
            UpdateFuelDetails.this.startActivity(activityIntent);
        });
    }

    public void updateQueueDetails(String id){
        RequestQueue queue = Volley.newRequestQueue(this);

        String updatefuelByIdAPI = EndPointURL.UPDATE_FUEL_BY_ID;
        updatefuelByIdAPI = updatefuelByIdAPI + id.trim();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, updatefuelByIdAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("api", "onResponse: "+response);
                        Toast.makeText(UpdateFuelDetails.this, "Database Updated!", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api", "onErrorResponse: "+error.getLocalizedMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", FuelModel.getId());
                params.put("fuelType", FuelModel.getFuelType());
                params.put("arrivingDate", FuelModel.getArrivingDate());
                params.put("arrivingTime", FuelModel.getArrivingTime());
                params.put("arrivedLitres", FuelModel.getArrivedLitres());
                return params;
            }
        };

        queue.add(stringRequest);
    }
}