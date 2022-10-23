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

public class OwnerRegistration extends AppCompatActivity {

    EditText ownerName, ownerEmail, ownerPassword, ownerRePassword, ownerContact, ownerFuelStation;
    String ownerLocation;
    ShedOwnerDBHelper shedOwnerDBHelper;
    UserDBHelper userDBHelper;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_registration);

        ownerName = (EditText) findViewById(R.id.editTextTextPersonName);
        ownerEmail = (EditText) findViewById(R.id.editTxtEmailAddress);
        ownerPassword = (EditText) findViewById(R.id.editTxt_password);
        ownerRePassword = (EditText) findViewById(R.id.editTxt_password2);
        ownerContact = (EditText) findViewById(R.id.editTxtVehNumber);
        ownerFuelStation = (EditText) findViewById(R.id.editTxtStationName);

        final List<String> locationType = Arrays.asList("Colombo", "Dehiwala", "Mount Lavinia", "Moratuwa", "Sri Jayawardenepura Kotte", "Negombo", "Kandy", "Kalmunai", "Vavuniya", "Galle", "Trincomalee", "Batticaloa", "Jaffna", "Matale", "Katunayake", "Dambulla", "Kolonnawa", "Anuradhapura", "Ratnapura");
        final Spinner spinnerLocationType = findViewById(R.id.spinnerLocation);

        signUp = (Button) findViewById(R.id.btn_reg);

        //Initialize DBHelpers
        shedOwnerDBHelper = new ShedOwnerDBHelper(this);
        userDBHelper = new UserDBHelper(this);

        ArrayAdapter adapterLocation = new ArrayAdapter(this, android.R.layout.simple_spinner_item, locationType);
        adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocationType.setAdapter(adapterLocation);

        spinnerLocationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ownerLocation = spinnerLocationType.getSelectedItem().toString();
                //Toast.makeText(getApplicationContext(), "You selected: " + customerFuelType, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        signUp.setOnClickListener(view -> {
            if(ownerName.getText().toString().equals("") || ownerEmail.getText().toString().equals("") || ownerPassword.getText().toString().equals("") || ownerContact.getText().toString().equals("") || ownerFuelStation.getText().toString().equals("") || ownerRePassword.getText().toString().equals("") || ownerLocation.equals(""))
            {
                Toast.makeText(this, "Please Enter all the Fields", Toast.LENGTH_SHORT).show();
            } else {
                if (ownerPassword.getText().toString().equals(ownerRePassword.getText().toString())) {
                    Boolean checkOwner = userDBHelper.checkUserExist(ownerEmail.getText().toString());
                    if (checkOwner == false) {
                        Boolean insertOwner = shedOwnerDBHelper.insertData(ownerEmail.getText().toString(), ownerName.getText().toString(), ownerContact.getText().toString(), ownerFuelStation.getText().toString(), ownerLocation);
                        Boolean insertUser = userDBHelper.insertData(ownerEmail.getText().toString(), ownerPassword.getText().toString(), "OWNER");
                        // calling a method to post the data and passing our name and job.
                        postDataUsingVolley(ownerName.getText().toString(), ownerEmail.getText().toString(), ownerPassword.getText().toString(), ownerContact.getText().toString(), ownerFuelStation.getText().toString(), ownerLocation);
                        if (insertOwner == true && insertUser == true) {
                            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent ownerIntent = new Intent(OwnerRegistration.this, CustomerLogin.class);
                            OwnerRegistration.this.startActivity(ownerIntent);
                        } else {
                            Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "User already exist! please sign in", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Password not matching", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final TextView linkLogin = findViewById(R.id.txt_loginLink);
        linkLogin.setOnClickListener(view -> {
            Intent activityIntent = new Intent(OwnerRegistration.this, CustomerLogin.class);
            OwnerRegistration.this.startActivity(activityIntent);
        });
    }

    private boolean postDataUsingVolley(String ownerName, String ownerEmail, String ownerPassword, String ownerContact, String ownerFuelStation, String ownerLocation){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String URL = EndPointURL.GET_ALL_OWNER;

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", null);
            jsonObject.put("ownerName", ownerName);
            jsonObject.put("ownerEmail", ownerEmail);
            jsonObject.put("ownerPassword", ownerPassword);
            jsonObject.put("ownerContact", ownerContact);
            jsonObject.put("ownerFuelStation", ownerFuelStation);
            jsonObject.put("ownerLocation", ownerLocation);
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