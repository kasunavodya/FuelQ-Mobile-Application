package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ViewQueueFuelDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_queue_fuel_details);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);

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
}
