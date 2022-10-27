/*
 * Class             :   Homepage of FuelQ Application
 * Implemented Date  :   18th October 2022
 */

package com.example.fuelq;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set the titleBar icon
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        final TextView txtTopic = findViewById(R.id.txt_topic);
        txtTopic.setOnClickListener(view -> {

            Context context = getApplicationContext();

            //Toast Message
            CharSequence message = "Welcome to FuelQ Application";
            int duration = Toast.LENGTH_SHORT; //How long the toast message will lasts
            Toast toast = Toast.makeText(context, message, duration);
            toast.show();

            //Navigate to the login page
            Intent activityIntent = new Intent(MainActivity.this, CustomerLogin.class);
            MainActivity.this.startActivity(activityIntent);
        });

    }
}