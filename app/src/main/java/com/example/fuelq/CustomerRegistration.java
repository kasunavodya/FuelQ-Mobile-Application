package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_registration);

        final Button BtnCustomerReg = (Button) findViewById(R.id.btn_reg1);
        BtnCustomerReg.setOnClickListener(view -> {

            Intent activityIntent = new Intent(CustomerRegistration.this, CustomerHome.class);
            CustomerRegistration.this.startActivity(activityIntent);

        });
    }
}