package com.example.fuelq;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
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
                    String userType = userDBHelper.getUserType(userEmail, userPassword);
                    if(checkUserPass == true){
                        Toast.makeText(CustomerLogin.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        if(userType == "OWNER"){
                            Intent intent = new Intent(getApplicationContext(), ShedOwnerHome.class);
                            startActivity(intent);
                        } else{
                            Intent intent = new Intent(getApplicationContext(), ShedOwnerHome.class);
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
