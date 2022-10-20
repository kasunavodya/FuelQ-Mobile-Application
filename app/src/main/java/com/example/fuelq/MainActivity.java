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

        final TextView txtTopic = findViewById(R.id.txt_topic);
        txtTopic.setOnClickListener(view -> {

            Context context = getApplicationContext();
            CharSequence message = "Welcome to FuelQ Application";

            //Display string
            int duration = Toast.LENGTH_SHORT; //How long the toast message will lasts
            Toast toast = Toast.makeText(context, message, duration);
            toast.show();

            Intent activityIntent = new Intent(MainActivity.this, MainRegistration.class);
            MainActivity.this.startActivity(activityIntent);
        });

    }
}