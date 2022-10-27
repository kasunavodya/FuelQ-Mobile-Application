/*
 * Developer ID      :   IT19144986
 * Developer Name    :   H.M. Kasuni Navodya
 * Class             :   AShedOwnerHome.java
 * Implemented Date  :   18th October 2022
 */

package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuelq.EndPoints.EndPointURL;
import com.example.fuelq.Model.Owner;
import org.json.JSONException;
import org.json.JSONObject;

public class ShedOwnerHome extends AppCompatActivity {

    //Local Variable List
    private String email, shedName;

    String OwnerEmailAPI = EndPointURL.GET_OWNER_BY_EMAIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shed_owner_home);

        //Set the titleBar icon
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_home_24);

        //pass the email address
        Intent intent = getIntent();
        email = intent.getStringExtra("Email");
        Log.e("api", "EMAIL: " + email );

        //Handle the 'BtnFuelDetails' button navigation
        final Button BtnFuelDetails = findViewById(R.id.btn_ownerFuel);
        BtnFuelDetails.setOnClickListener(view -> {
            Intent activityIntent = new Intent(ShedOwnerHome.this, ViewOwnerFuelDetails.class);
            activityIntent.putExtra("Email", email);
            ShedOwnerHome.this.startActivity(activityIntent);
        });

        //Handle the 'BtnQueueDetails' button navigation
        final Button BtnQueueDetails = findViewById(R.id.btn_queue);
        BtnQueueDetails.setOnClickListener(view -> {
            getOwnerData(email);
            //Intent activityIntent = new Intent(ShedOwnerHome.this, ViewQueueDetails.class);
            //ShedOwnerHome.this.startActivity(activityIntent);
        });
    }

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
                            shedName = ownerModel.getOwnerFuelStation();
                            Log.e("api", "SHED NAME HOME: " + shedName );
                            Intent activityIntent = new Intent(ShedOwnerHome.this, ViewQueueDetails.class);
                            activityIntent.putExtra("ShedName", shedName);
                            ShedOwnerHome.this.startActivity(activityIntent);

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