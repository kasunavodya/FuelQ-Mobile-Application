package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ReqFuelVolume extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.req_fuel_volume);

        final Button btnPump = findViewById(R.id.btn_pump);
        btnPump.setOnClickListener(view -> {

            Intent activityIntent = new Intent(ReqFuelVolume.this, ViewFuelDetails.class);
            ReqFuelVolume.this.startActivity(activityIntent);

        });
    }
}
