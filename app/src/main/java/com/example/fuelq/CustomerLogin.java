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

        final TextView SignInView = (TextView) findViewById(R.id.txt_signIn2);
        SignInView.setOnClickListener(view -> {

            Intent activityIntent = new Intent(CustomerLogin.this, MainRegistration.class);
            CustomerLogin.this.startActivity(activityIntent);
        });

        final Button btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(view -> {

            Intent activityIntent = new Intent(CustomerLogin.this, CustomerHome.class);
            CustomerLogin.this.startActivity(activityIntent);
        });
    }
}