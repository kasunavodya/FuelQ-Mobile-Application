package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ViewFuelDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_fuel_details);

        final Button btnWait = (Button) findViewById(R.id.btn_wait);
        btnWait.setOnClickListener(view -> {

            Intent activityIntent = new Intent(ViewFuelDetails.this, FullOrExit.class);
            ViewFuelDetails.this.startActivity(activityIntent);

        });

        final Button btnExit2 = (Button) findViewById(R.id.btn_exit);
        btnExit2.setOnClickListener(view -> {

            Intent activityIntent = new Intent(ViewFuelDetails.this, CustomerHome.class);
            ViewFuelDetails.this.startActivity(activityIntent);

        });
    }
}
