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

public class ShedOwnerHome extends AppCompatActivity {

    //Local Variable List
    private String email;

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
            Intent activityIntent = new Intent(ShedOwnerHome.this, ViewQueueDetails.class);
            ShedOwnerHome.this.startActivity(activityIntent);
        });
    }
}