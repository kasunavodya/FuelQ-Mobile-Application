package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class GetFuelDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_fuel_details);

        final Button btnEdit = findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(view -> {
            Intent activityIntent = new Intent(GetFuelDetails.this, UpdateFuelDetails.class);
            GetFuelDetails.this.startActivity(activityIntent);
        });

        Button btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(view -> {
            Intent activityIntent = new Intent(GetFuelDetails.this, DeleteConfirmation.class);
            GetFuelDetails.this.startActivity(activityIntent);
        });
    }
}
