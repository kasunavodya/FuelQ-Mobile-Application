package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuelq.EndPoints.EndPointURL;
import com.example.fuelq.Model.Owner;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewOwnerFuelDetails extends AppCompatActivity {

    String OwnerAPI = EndPointURL.OWNER_BY_ID;
    TextView FuelStation, FuelLocation, OwnerName, OwnerContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_owner_fuel_details);

        FuelStation = findViewById(R.id.txt_fuelStation);
        FuelLocation = findViewById(R.id.txt_fuelLocation);
        OwnerName = findViewById(R.id.txt_fuelOwner);
        OwnerContact = findViewById(R.id.txt_fuelOwnerContact);

        getOwnerData();

        final ImageView imgEdit = findViewById(R.id.img_edit);
        imgEdit.setOnClickListener(view -> {
            Intent activityIntent = new Intent(ViewOwnerFuelDetails.this, UpdateFuelDetails.class);
            ViewOwnerFuelDetails.this.startActivity(activityIntent);
        });

        final ImageView imgDelete = findViewById(R.id.img_delete);
        imgDelete.setOnClickListener(view -> {
            Intent activityIntent = new Intent(ViewOwnerFuelDetails.this, DeleteConfirmation.class);
            ViewOwnerFuelDetails.this.startActivity(activityIntent);
        });

        final FloatingActionButton btnAdd = findViewById(R.id.floating_add);
        btnAdd.setOnClickListener(view -> {
            Intent activityIntent = new Intent(ViewOwnerFuelDetails.this, AddFuelDetails.class);
            ViewOwnerFuelDetails.this.startActivity(activityIntent);
        });
    }

    private void getOwnerData() {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, OwnerAPI,
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
}
