package com.example.fuelq;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerLogin extends AppCompatActivity {

    EditText email, password;
    Button loginBtn;
    TextView linkText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_login);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);

        email = findViewById(R.id.editTxtEmailAddress);
        password = findViewById(R.id.editTxt_password);
        loginBtn = findViewById(R.id.btn_login);
        linkText = findViewById(R.id.txt_RegLink);

        UserDBHelper userDBHelper = new UserDBHelper(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();

                if(userEmail.equals("") || userPassword.equals("")){
                    Toast.makeText(CustomerLogin.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkUserPass = userDBHelper.checkUserEmailPassword(userEmail, userPassword);
                    Cursor userType = userDBHelper.getUserType(userEmail, userPassword);
                    String type = "";
                    if(userType.moveToFirst()){
                        type = userType.getString(0);
                    }
                    if(checkUserPass == true){
                        Toast.makeText(CustomerLogin.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        Log.i("USERTYPE", type);
                        if(type.equals("OWNER")){

                            Intent intent = new Intent(getApplicationContext(), ShedOwnerHome.class);
                            intent.putExtra("Email", email.getText().toString());
                            startActivity(intent);
                        } else{
                            Intent intent = new Intent(getApplicationContext(), CustomerHome.class);
                            startActivity(intent);
                        }
                    }else{
                        Toast.makeText(CustomerLogin.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        linkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent(CustomerLogin.this, MainRegistration.class);
                CustomerLogin.this.startActivity(activityIntent);
            }
        });
    }
}
