package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ShedOwnerHome extends AppCompatActivity {

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shed_owner_home);

        Intent intent = getIntent();
        email = intent.getStringExtra("Email");
        Log.e("api", "EMAIL: " + email );

        final Button BtnFuelDetails = findViewById(R.id.btn_ownerFuel);
        BtnFuelDetails.setOnClickListener(view -> {

            Intent activityIntent = new Intent(ShedOwnerHome.this, ViewOwnerFuelDetails.class);
            activityIntent.putExtra("Email", email);
            ShedOwnerHome.this.startActivity(activityIntent);

        });

        final Button BtnQueueDetails = findViewById(R.id.btn_queue);
        BtnQueueDetails.setOnClickListener(view -> {

            Intent activityIntent = new Intent(ShedOwnerHome.this, ViewQueueDetails.class);
            ShedOwnerHome.this.startActivity(activityIntent);

        });
    }
}