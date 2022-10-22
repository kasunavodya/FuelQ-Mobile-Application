package com.example.fuelq;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class CustomerLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_login);

        final Button btnLogin = findViewById(R.id.btn_edit);
        btnLogin.setOnClickListener(view -> {

            Intent activityIntent = new Intent(CustomerLogin.this, CustomerHome.class);
            CustomerLogin.this.startActivity(activityIntent);
        });

        final TextView linkReg = findViewById(R.id.txt_RegLink);
        linkReg.setOnClickListener(view -> {

            Intent activityIntent = new Intent(CustomerLogin.this, MainRegistration.class);
            CustomerLogin.this.startActivity(activityIntent);
        });
    }
}