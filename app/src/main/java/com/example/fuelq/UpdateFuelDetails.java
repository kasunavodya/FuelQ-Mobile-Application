package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuelq.EndPoints.EndPointURL;
import com.example.fuelq.Model.Fuel;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class UpdateFuelDetails extends AppCompatActivity {

    private EditText arrivingDate, arrivingTime, arrivedLitres, fuelType;
    Fuel FuelModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_fuel_details);

        arrivingDate = findViewById(R.id.editTxt_date);
        arrivingTime = findViewById(R.id.editTxt_time);
        arrivedLitres = findViewById(R.id.editTxt_arriving);

        fuelType = findViewById(R.id.editTxt_fuelType);
        fuelType.setEnabled(false);

        String fuelId = "635588d3527d5ccc8c7c00ed";
        //updateQueueDetails(fuelId);
        getFuelData();

        final Button btnAddDetails = findViewById(R.id.btn_update);
        btnAddDetails.setOnClickListener(view -> {
            updateQueueDetails(fuelId);
            Intent activityIntent = new Intent(UpdateFuelDetails.this, ViewOwnerFuelDetails.class);
            UpdateFuelDetails.this.startActivity(activityIntent);
        });
    }

    private void getFuelData() {

        RequestQueue queue = Volley.newRequestQueue(this);

        String getfuelByIdAPI = EndPointURL.GET_FUEL_BY_ID;

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
                            fuelType.setText(fuelModel.getFuelType());
                            arrivingDate.setText(fuelModel.getArrivingDate());
                            arrivingTime.setText(fuelModel.getArrivingTime());
                            arrivedLitres.setText(fuelModel.getArrivedLitres());
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

    public void updateQueueDetails(String id){
        RequestQueue queue = Volley.newRequestQueue(this);

        String updatefuelByIdAPI = EndPointURL.UPDATE_FUEL_BY_ID;
        updatefuelByIdAPI = updatefuelByIdAPI + id.trim();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, updatefuelByIdAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("api", "onResponse: "+response);
                        Toast.makeText(UpdateFuelDetails.this, "Fuel Details Updated!", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api", "onErrorResponse: "+error.getLocalizedMessage());
            }
        }) {

//            @Override
//            protected Map<String, String> getParams(){
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("id", FuelModel.getId());
//                params.put("fuelType", FuelModel.getFuelType());
//                params.put("fuelStation", FuelModel.getFuelType());
//                params.put("arrivingDate", arrivingDate.getText().toString());
//                params.put("arrivingTime", arrivingTime.getText().toString());
//                params.put("arrivedLitres", arrivedLitres.getText().toString());
//                params.put("remainLitres", FuelModel.getArrivedLitres());
//                return params;
//            }

            @Override
            public byte[] getBody() {
                String body="{\"id\":" + "\"" + FuelModel.getId() + "\"," +
//                        "\"fuelType\":" + "\"" + FuelModel.getFuelType() + "\"," +
                        "\"fuelStation\":" + "\"" + FuelModel.getFuelStation() + "\"," +
                        "\"arrivingDate\":" + "\"" + arrivingDate.getText().toString() + "\"," +
                        "\"arrivingTime\":" + "\"" + arrivingTime.getText().toString() + "\"," +
                        "\"arrivedLitres\":" + "\"" + arrivedLitres.getText().toString() + "\"," +
                        "\"remainLitres\":" + "\"" + FuelModel.getArrivedLitres() + "\"" +"}";
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