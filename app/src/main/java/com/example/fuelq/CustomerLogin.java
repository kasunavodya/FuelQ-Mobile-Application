package com.example.fuelq;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
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
    }
}