/*
 * Developer ID      :   IT19016108
 * Developer Name    :   Alwis T.A.D.T.N.D
 * Function          :   Function to wait or exit from the queue
 * Implemented Date  :   20th October 2022
 */

package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class FullOrExit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_or_exit);

        //Set the titleBar icon
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);

        //Handling full button
        final Button btnFull = findViewById(R.id.btn_full);
        btnFull.setOnClickListener(view -> {

            Intent activityIntent = new Intent(FullOrExit.this, AddFeedback.class);
            FullOrExit.this.startActivity(activityIntent);
        });

        //Handling exit button
        final Button btnExit2 = findViewById(R.id.btn_exit);
        btnExit2.setOnClickListener(view -> {

            Intent activityIntent = new Intent(FullOrExit.this, CustomerHome.class);
            FullOrExit.this.startActivity(activityIntent);
        });
    }

}
