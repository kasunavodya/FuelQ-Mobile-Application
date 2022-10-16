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

        final ImageView imgEdit = (ImageView) findViewById(R.id.img_edit);
        imgEdit.setOnClickListener(view -> {
            Intent activityIntent = new Intent(ViewOwnerFuelDetails.this, UpdateFuelDetails.class);
            ViewOwnerFuelDetails.this.startActivity(activityIntent);
        });

        final ImageView imgDelete = (ImageView) findViewById(R.id.img_delete);
        imgDelete.setOnClickListener(view -> {
            Intent activityIntent = new Intent(ViewOwnerFuelDetails.this, DeleteConfirmation.class);
            ViewOwnerFuelDetails.this.startActivity(activityIntent);
        });
    }
}