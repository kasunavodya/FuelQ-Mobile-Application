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
import android.widget.TextView;
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

public class CustomerRegistration extends AppCompatActivity{

    Button signUp;
    EditText email, pass, repass, vehicleNo;
    CustomerDBHelper customerDBHelper;
    UserDBHelper userDBHelper;
    String valueForType, valueForFuel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_registration);

        //initialize the fields and buttons
        email = (EditText) findViewById(R.id.editTxtEmailAddress);
        pass = (EditText) findViewById(R.id.editTxt_password);
        repass = (EditText) findViewById(R.id.editTxt_password2);
        vehicleNo = (EditText) findViewById(R.id.editTxtVehNumber);

        final List<String> vehicleType = Arrays.asList("Bike", "Car", "Van", "Bus", "Three-Wheel", "Lorry");
        final List<String> fuelType = Arrays.asList("Auto Diesel", "Super Diesel", "Octane 95", "Octane 92");

        final Spinner spinnerVehicleType = findViewById(R.id.spinnerVehType);
        final Spinner spinnerFuelType = findViewById(R.id.spinnerFuelType);

        signUp = (Button) findViewById(R.id.btn_reg1);

        //Initialize DBHelpers
        customerDBHelper = new CustomerDBHelper(this);
        userDBHelper = new UserDBHelper(this);

        ArrayAdapter vehAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, vehicleType);
        vehAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVehicleType.setAdapter(vehAdapter);

        ArrayAdapter fuelAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, fuelType);
        fuelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFuelType.setAdapter(fuelAdapter);
        spinnerFuelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                valueForType = spinnerVehicleType.getSelectedItem().toString();
                valueForFuel = spinnerFuelType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Create Customer Account
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fetch all data
                String emailAdd = email.getText().toString();
                String password = pass.getText().toString();
                String repassword = repass.getText().toString();
                String vehNo = vehicleNo.getText().toString();
                String vehType = valueForType;
                String fuel = valueForFuel;

                if (emailAdd.equals("") || password.equals("") || repassword.equals("")) {
                    Toast.makeText(CustomerRegistration.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(repassword)) {
                        Boolean checkCustomer = customerDBHelper.checkUserExist(emailAdd);
                        if (checkCustomer == false) {
                            Boolean insertCust = customerDBHelper.insertData(emailAdd, vehNo, vehType, fuel);
                            postDataUsingVolley(emailAdd, password, vehNo,  vehType, fuel);
                            Boolean insertUser = userDBHelper.insertData(emailAdd, password, "CUSTOMER");

                            if (insertCust == true && insertUser == true) {
                                Toast.makeText(CustomerRegistration.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                final TextView btnReg = findViewById(R.id.btn_reg1);
                                btnReg.setOnClickListener(view -> {

                                    Intent activityIntent = new Intent(CustomerRegistration.this, CustomerHome.class);
                                    CustomerRegistration.this.startActivity(activityIntent);
                                });
                            } else {
                                Toast.makeText(CustomerRegistration.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CustomerRegistration.this, "User already exist! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CustomerRegistration.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        final TextView linkLogin = findViewById(R.id.txt_loginLink);
        linkLogin.setOnClickListener(view -> {

            Intent activityIntent = new Intent(CustomerRegistration.this, CustomerLogin.class);
            CustomerRegistration.this.startActivity(activityIntent);
        });
    }

    private boolean postDataUsingVolley(String customerEmail, String customerPassword, String customerVehicleNumber, String customerVehicleType, String customerFuelType){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String URL = EndPointURL.GET_ALL_CUSTOMER;

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