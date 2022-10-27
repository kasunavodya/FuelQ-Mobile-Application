package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuelq.EndPoints.EndPointURL;
import com.example.fuelq.Model.Customer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewQueueDetails extends AppCompatActivity {

    private TextView txt_displayTotalBike, txt_displayTotalCar, txt_displayTotalVan, txt_displayTotalBus, txt_displayTotalThreeWheel, txt_displayTotalLorry, txtView_vehCount;
    String shedName;
    int bikeCount = 0, carCount = 0, vanCount = 0, busCount = 0, threeWheelCount = 0, lorryCount = 0;

    String GetQueueAPI = EndPointURL.GET_QUEUE_BY_SHEDNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_customer_queue_details);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);

        txt_displayTotalBike = findViewById(R.id.txt_displayTotalBike);
        txt_displayTotalCar = findViewById(R.id.txt_displayTotalCar);
        txt_displayTotalVan = findViewById(R.id.txt_displayTotalVan);
        txt_displayTotalBus = findViewById(R.id.txt_displayTotalBus);
        txt_displayTotalThreeWheel = findViewById(R.id.txt_displayTotalThreeWheel);
        txt_displayTotalLorry = findViewById(R.id.txt_displayTotalLorry);
        txtView_vehCount = findViewById(R.id.txtView_vehCount);

        //pass the shedName
        Intent intent = getIntent();
        shedName = intent.getStringExtra("ShedName");
        Log.e("api", "SHED NAME: " + shedName );

        getQueueData(shedName);

    }

    private void getQueueData(String shedName) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String getAPI = GetQueueAPI + shedName;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, getAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Customer customerModel = new Customer(
                                        jsonObject.getString("id"),
                                        jsonObject.getString("customerEmail"),
                                        jsonObject.getString("customerPassword"),
                                        jsonObject.getInt("customerVehicleNumber"),
                                        jsonObject.getString("customerVehicleType"),
                                        jsonObject.getString("customerFuelType"),
                                        jsonObject.getString("awaitingTime"),
                                        jsonObject.getInt("token"),
                                        jsonObject.getString("arrivalTimeQ"),
                                        jsonObject.getString("departTimeQ"),
                                        jsonObject.getInt("requestedLitres"),
                                        jsonObject.getString("shedQ")
                                );
                                if(customerModel.getCustomerVehicleType().equals("Bike")){
                                    bikeCount++;
                                }
                                else if(customerModel.getCustomerVehicleType().equals("Car")){
                                    carCount++;
                                }
                                else if(customerModel.getCustomerVehicleType().equals("Van")){
                                    vanCount++;
                                }
                                else if(customerModel.getCustomerVehicleType().equals("Bus")){
                                    busCount++;
                                }
                                else if(customerModel.getCustomerVehicleType().equals("Three-Wheel")){
                                    threeWheelCount++;
                                }
                                else if(customerModel.getCustomerVehicleType().equals("Lorry")){
                                    lorryCount++;
                                }
                            }

                            Log.e("API", "Car Count: " + carCount );
                            Log.e("API", "Bike Count: " + bikeCount );

                            txt_displayTotalBike.setText(String.valueOf(bikeCount));
                            txt_displayTotalCar.setText(String.valueOf(carCount));
                            txt_displayTotalVan.setText(String.valueOf(vanCount));
                            txt_displayTotalBus.setText(String.valueOf(busCount));
                            txt_displayTotalThreeWheel.setText(String.valueOf(threeWheelCount));
                            txt_displayTotalLorry.setText(String.valueOf(lorryCount));
                            txtView_vehCount.setText(String.valueOf(calculateTotVehicle()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api", "onErrorResponse: "+error.getLocalizedMessage());
            }
        });
        queue.add(stringRequest);
    }

    public int calculateTotVehicle(){
        return bikeCount + carCount + vanCount + busCount + threeWheelCount + lorryCount;
    }
}