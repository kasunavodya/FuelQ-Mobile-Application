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

    private EditText ownerName, ownerEmail, ownerPassword, ownerContact, ownerFuelStation;
    private String ownerLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_registration);

        final List<String> locationType = Arrays.asList("Colombo", "Dehiwala", "Mount Lavinia", "Moratuwa", "Sri Jayawardenepura Kotte", "Negombo", "Kandy", "Kalmunai", "Vavuniya", "Galle", "Trincomalee", "Batticaloa", "Jaffna", "Matale", "Katunayake", "Dambulla", "Kolonnawa", "Anuradhapura", "Ratnapura");

        final Spinner spinnerLocation = findViewById(R.id.spinnerLocation);
        ownerName = findViewById(R.id.editTextTextPersonName);
        ownerEmail = findViewById(R.id.editTxtEmailAddress);
        ownerPassword = findViewById(R.id.editTxt_password);
        ownerContact = findViewById(R.id.editTxtVehNumber);
        ownerFuelStation = findViewById(R.id.editTxtStationName);

        ArrayAdapter adapterLocation = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, locationType);
        adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerLocation.setAdapter(adapterLocation);

        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ownerLocation = spinnerLocation.getSelectedItem().toString();
                //Toast.makeText(getApplicationContext(), "You selected: " + customerFuelType, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Button BtnOwnerReg = findViewById(R.id.btn_reg);
        BtnOwnerReg.setOnClickListener(view -> {
            Intent activityIntent = new Intent(OwnerRegistration.this, ShedOwnerHome.class);
            OwnerRegistration.this.startActivity(activityIntent);

            // calling a method to post the data and passing our name and job.
            postDataUsingVolley(ownerName.getText().toString(), ownerEmail.getText().toString(), ownerPassword.getText().toString(), ownerContact.getText().toString(), ownerFuelStation.getText().toString(), ownerLocation);
            Toast.makeText(OwnerRegistration.this, "Data Registered Successfully", Toast.LENGTH_LONG).show();
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