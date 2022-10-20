package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_delete);

        final Button BtnDelete = findViewById(R.id.btn_delete);
        BtnDelete.setOnClickListener(view -> {

            Intent activityIntent = new Intent(DeleteConfirmation.this, ViewOwnerFuelDetails.class);
            DeleteConfirmation.this.startActivity(activityIntent);
        });

        final Button BtnCancel = findViewById(R.id.btn_cancel);
        BtnCancel.setOnClickListener(view -> {

            Intent activityIntent = new Intent(DeleteConfirmation.this, ViewOwnerFuelDetails.class);
            DeleteConfirmation.this.startActivity(activityIntent);
        });
    }
}