package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewQueueFuelDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_queue_fuel_details);

        final Button btnJoin = (Button) findViewById(R.id.btn_join);
        btnJoin.setOnClickListener(view -> {
            Intent activityIntent = new Intent(ViewQueueFuelDetails.this, ReqFuelVolume.class);
            ViewQueueFuelDetails.this.startActivity(activityIntent);
        });

        final Button btnExit = (Button) findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(view -> {
            Intent activityIntent = new Intent(ViewQueueFuelDetails.this, CustomerHome.class);
            ViewQueueFuelDetails.this.startActivity(activityIntent);
        });
    }
}
