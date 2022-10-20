package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.appcompat.app.AppCompatActivity;

public class FullOrExit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_or_exit);

        final Button btnFull = findViewById(R.id.btn_full);
        btnFull.setOnClickListener(view -> {

            Intent activityIntent = new Intent(FullOrExit.this, AddFeedback.class);
            FullOrExit.this.startActivity(activityIntent);
        });

        final Button btnExit2 = findViewById(R.id.btn_exit);
        btnExit2.setOnClickListener(view -> {

            Intent activityIntent = new Intent(FullOrExit.this, CustomerHome.class);
            FullOrExit.this.startActivity(activityIntent);
        });
    }

}
