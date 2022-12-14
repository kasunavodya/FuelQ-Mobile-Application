/*
 * Developer ID      :   IT19144986
 * Developer Name    :   H.M. Kasuni Navodya
 * Function          :   Handle the fuel details delete confirmation
 * Implemented Date  :   21st October 2022
 */

package com.example.fuelq;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuelq.EndPoints.EndPointURL;

public class DeleteConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_delete);

        //Set the titleBar icon
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);

        //Handle the delete button navigation
        final Button BtnDelete = findViewById(R.id.btn_delete);
        BtnDelete.setOnClickListener(view -> {
            Intent activityIntent = new Intent(DeleteConfirmation.this, ViewOwnerFuelDetails.class);
            DeleteConfirmation.this.startActivity(activityIntent);
            Toast.makeText(DeleteConfirmation.this, "Data Deleted Successfully", Toast.LENGTH_LONG).show();
            exitFuelApi();
        });

        //Handle the cancel button navigation
        final Button BtnCancel = findViewById(R.id.btn_cancel);
        BtnCancel.setOnClickListener(view -> {
            Intent activityIntent = new Intent(DeleteConfirmation.this, ViewOwnerFuelDetails.class);
            DeleteConfirmation.this.startActivity(activityIntent);
        });
    }

    /**********************************************************************************
     * @DeveloperID   :   IT19144986
     * @Developer     :   H.M. Kasuni Navodya
     * @Function      :   Delete the selected fuel details from database.
     **********************************************************************************/
    private void exitFuelApi() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String deleteURL = EndPointURL.DELETE_FUEL_BY_ID;

        StringRequest stringRequest =  new StringRequest(Request.Method.DELETE, deleteURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Response Status Code: "+response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("That didn't work!");
                        System.out.println("That didn't work! +" + error.getLocalizedMessage());
                    }
                });

        queue.add(stringRequest);
    }
}