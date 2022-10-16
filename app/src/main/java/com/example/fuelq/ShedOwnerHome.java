package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ShedOwnerHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shed_owner_home);

        final Button BtnFuelDetails = (Button) findViewById(R.id.btn_ownerFuel);
        BtnFuelDetails.setOnClickListener(view -> {

            Intent activityIntent = new Intent(ShedOwnerHome.this, ViewOwnerFuelDetails.class);
            ShedOwnerHome.this.startActivity(activityIntent);

        });

        final Button BtnQueueDetails = (Button) findViewById(R.id.btn_queue);
        BtnQueueDetails.setOnClickListener(view -> {

            Intent activityIntent = new Intent(ShedOwnerHome.this, ViewQueueDetails.class);
            ShedOwnerHome.this.startActivity(activityIntent);

        });
    }
}