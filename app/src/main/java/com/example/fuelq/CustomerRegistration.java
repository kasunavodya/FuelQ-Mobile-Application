package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class CustomerRegistration extends AppCompatActivity {

    private EditText customerVehicleNumber, customerPassword, customerEmail;
    private String customerVehicleType, customerFuelType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_registration);

        final List<String> fuelType = Arrays.asList("Octane 92", "Octane 95", "Super Diesel", "Auto Diesel", "Kerosene");
        final List<String> vehicleType = Arrays.asList("Car", "Van", "Bus", "Motor Bike", "Three wheeler");

        final Spinner spinnerFuelType = findViewById(R.id.customerFuelType);
        final Spinner spinnerVehicleType = findViewById(R.id.customerVehicleType);

        customerVehicleNumber = findViewById(R.id.customerVehicleNumber);
        customerPassword = findViewById(R.id.customerPassword);
        customerEmail = findViewById(R.id.customerEmail);

        final Button BtnCustomerReg = (Button) findViewById(R.id.btn_reg1);

        ArrayAdapter adapterFuelType = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, fuelType);
        adapterFuelType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFuelType.setAdapter(adapterFuelType);

        spinnerFuelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                customerFuelType = spinnerFuelType.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "You selected: " + customerFuelType, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adapterVehicleType = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, vehicleType);
        adapterVehicleType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerVehicleType.setAdapter(adapterVehicleType);

        spinnerVehicleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                customerVehicleType = spinnerVehicleType.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "You selected: " + customerVehicleType, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        BtnCustomerReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDataUsingVolley(customerEmail.getText().toString(), customerPassword.getText().toString(), customerVehicleNumber.getText().toString(), customerVehicleType, customerFuelType);
            }
        });

    }

    private void postDataUsingVolley(String customerEmail, String customerPassword, String customerVehicleNumber, String customerVehicleType, String customerFuelType){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String[] arr = new String[0];

            System.out.println("customerEmail"+ customerEmail);
            System.out.println("customerPassword"+ customerPassword);
            System.out.println("customerVehicleNumber"+ customerVehicleNumber);
            System.out.println("customerVehicleType"+ customerVehicleType);
            System.out.println("customerFuelType"+ customerFuelType);
            String URL = "http://192.168.1.2:5000/api/Customer";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", null);
            jsonObject.put("customerEmail", customerEmail);
            jsonObject.put("customerPassword", customerPassword);
            jsonObject.put("customerVehicleNumber", customerVehicleNumber);
            jsonObject.put("customerVehicleType", customerVehicleType);
            jsonObject.put("customerFuelType", customerFuelType);
            final String mRequestBody = jsonObject.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);
                    try {
                        System.out.println("Inside"+   response);
                        JSONObject singleObject = new JSONObject(response);
                        System.out.println("Inside" +   singleObject);
                        System.out.println("Inside" +   singleObject.getString("name"));
                    }catch (JSONException e){

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {

                        responseString = String.valueOf(response.statusCode);

                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




}