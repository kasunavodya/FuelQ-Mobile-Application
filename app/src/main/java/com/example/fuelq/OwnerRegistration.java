package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OwnerRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_registration);

        final Button BtnOwnerReg = findViewById(R.id.btn_reg);
        BtnOwnerReg.setOnClickListener(view -> {

            Intent activityIntent = new Intent(OwnerRegistration.this, ShedOwnerHome.class);
            OwnerRegistration.this.startActivity(activityIntent);

        });
    }
}