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
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuelq.EndPoints.EndPointURL;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class CustomerRegistration extends AppCompatActivity {

    private EditText customerEmail, customerPassword, customerVehicleNumber;
    private String customerVehicleType, customerFuelType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_registration);

        final List<String> fuelType = Arrays.asList("Octane 92", "Octane 95", "Super Diesel", "Auto Diesel", "Kerosene");
        final List<String> vehicleType = Arrays.asList("Car", "Van", "Bus", "Motor Bike", "Three wheeler");

        final Spinner spinnerVehicleType = findViewById(R.id.spinnerVehType);
        final Spinner spinnerFuelType = findViewById(R.id.spinnerFuelType);

        customerEmail = findViewById(R.id.editTxtEmailAddress);
        customerPassword = findViewById(R.id.editTxt_password);
        customerVehicleNumber = findViewById(R.id.editTxtVehNumber);

        ArrayAdapter adapterFuelType = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, fuelType);
        adapterFuelType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFuelType.setAdapter(adapterFuelType);

        spinnerFuelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                customerFuelType = spinnerFuelType.getSelectedItem().toString();
                //Toast.makeText(getApplicationContext(), "You selected: " + customerFuelType, Toast.LENGTH_LONG).show();
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
                //Toast.makeText(getApplicationContext(), "You selected: " + customerVehicleType, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Button BtnCustomerReg = findViewById(R.id.btn_reg1);
        BtnCustomerReg.setOnClickListener(view -> {
            Intent activityIntent = new Intent(CustomerRegistration.this, CustomerHome.class);
            CustomerRegistration.this.startActivity(activityIntent);

            // calling a method to post the data and passing our name and job.
            postDataUsingVolley(customerEmail.getText().toString(), customerPassword.getText().toString(), customerVehicleNumber.getText().toString(), customerVehicleType, customerFuelType);
            Toast.makeText(CustomerRegistration.this, "Data Registered Successfully", Toast.LENGTH_LONG).show();
        });
    }

    private boolean postDataUsingVolley(String customerEmail, String customerPassword, String customerVehicleNumber, String customerVehicleType, String customerFuelType){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String URL = EndPointURL.CUSTOMER;

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", null);
            jsonObject.put("customerEmail", customerEmail);
            jsonObject.put("customerPassword", customerPassword);
            jsonObject.put("customerVehicleNumber", customerVehicleNumber);
            jsonObject.put("customerVehicleType", customerVehicleType);
            jsonObject.put("customerFuelType", customerFuelType);
            final String mRequestBody = jsonObject.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
                Log.i("LOG_VOLLEY", response);
                try {
                    JSONObject singleObject = new JSONObject(response);
                }catch (JSONException e){

                }
            }, error -> Log.e("LOG_VOLLEY", error.toString())) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
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
        return false;
    }
}