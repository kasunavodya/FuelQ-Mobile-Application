package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AddFuelDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_fuel_details);

        final Button btnAddDetails = findViewById(R.id.btn_addDetails);
        btnAddDetails.setOnClickListener(view -> {
            Intent activityIntent = new Intent(AddFuelDetails.this, ViewOwnerFuelDetails.class);
            AddFuelDetails.this.startActivity(activityIntent);
        });
    }
}
