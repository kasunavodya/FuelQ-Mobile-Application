package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GetFuelDetails extends AppCompatActivity implements View.OnClickListener{

    Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_fuel_details);

        btnEdit = findViewById(R.id.btn_edit);

//        btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent activityIntent = new Intent(GetFuelDetails.this, UpdateFuelDetails.class);
//                GetFuelDetails.this.startActivity(activityIntent);
//            }
//        });

        btnEdit.setOnClickListener(this);

//        final Button btnEdit = findViewById(R.id.btn_edit);
//        btnEdit.setOnClickListener(view -> {
//            Intent activityIntent = new Intent(GetFuelDetails.this, UpdateFuelDetails.class);
//            GetFuelDetails.this.startActivity(activityIntent);
//        });
//
//        Button btnDelete = findViewById(R.id.btn_delete);
//        btnDelete.setOnClickListener(view -> {
//            Intent activityIntent = new Intent(GetFuelDetails.this, DeleteConfirmation.class);
//            GetFuelDetails.this.startActivity(activityIntent);
//        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_edit:
                Toast.makeText(this, "Edit Button Clicked", Toast.LENGTH_SHORT).show();
                //Intent activityIntent = new Intent(GetFuelDetails.this, UpdateFuelDetails.class);
                //GetFuelDetails.this.startActivity(activityIntent);
                break;
            default:
                break;
        }

    }
}
