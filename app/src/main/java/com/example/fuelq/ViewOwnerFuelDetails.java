package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewOwnerFuelDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_owner_fuel_details);

        final ImageView reg1Btn = (ImageView) findViewById(R.id.img_edit);
        reg1Btn.setOnClickListener(view -> {
            Intent activityIntent = new Intent(ViewOwnerFuelDetails.this, UpdateFuelDetails.class);
            ViewOwnerFuelDetails.this.startActivity(activityIntent);
        });
    }
}