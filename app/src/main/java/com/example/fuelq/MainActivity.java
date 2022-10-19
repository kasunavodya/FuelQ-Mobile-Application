package com.example.fuelq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String URL = "https://localhost:7060/api/Customer";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> Log.e("Rest Response", response.toString()),
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());
                    }
                }
        );

        requestQueue.add(objectRequest);

        final TextView TitleView = (TextView) findViewById(R.id.txt_topic);
        TitleView.setOnClickListener(view -> {

            Context context = getApplicationContext();
            CharSequence message = "Welcome to FuelQ Application";
            //Display string
            int duration = Toast.LENGTH_SHORT; //How long the toast message will lasts
            Toast toast = Toast.makeText(context, message, duration);
            toast.show();

            Intent activityIntent = new Intent(MainActivity.this, CustomerLogin.class);
            MainActivity.this.startActivity(activityIntent);

        });
    }
}