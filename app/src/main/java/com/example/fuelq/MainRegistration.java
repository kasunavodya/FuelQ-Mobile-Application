package com.example.fuelq;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_registration);

        final Button reg1Btn = (Button) findViewById(R.id.btn_customerReg);
        reg1Btn.setOnClickListener(view -> {

            Context context = getApplicationContext();
            CharSequence message = "Welcome to FuelQ Application";
            //Display string
            int duration = Toast.LENGTH_SHORT; //How long the toast message will lasts
            Toast toast = Toast.makeText(context, message, duration);
            toast.show();

            Intent activityIntent = new Intent(MainRegistration.this, CustomerRegistration.class);
            MainRegistration.this.startActivity(activityIntent);

        });
    }
}