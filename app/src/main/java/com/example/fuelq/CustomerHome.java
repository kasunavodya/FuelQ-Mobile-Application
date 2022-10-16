package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_home);

        final Button btnCheck = (Button) findViewById(R.id.btn_check);
        btnCheck.setOnClickListener(view -> {

            Intent activityIntent = new Intent(CustomerHome.this, ViewQueueFuelDetails.class);
            CustomerHome.this.startActivity(activityIntent);

        });
    }
}