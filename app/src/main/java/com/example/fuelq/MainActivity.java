package com.example.fuelq;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    String api = "http://192.168.1.2:5000/api/Customer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        getData();
    }

    private void getData() {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.e("api", "onErrorResponse: "+response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api", "onErrorResponse: "+error.getLocalizedMessage());
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }




}