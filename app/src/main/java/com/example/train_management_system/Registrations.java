package com.example.train_management_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Registrations extends AppCompatActivity {

    EditText firstName,last_name, nic, email, password, con_password, mobile;
    Context context = this;

    public ConstraintLayout registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrations);
        initComponents();
    }

    private void initComponents() {

        firstName = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        nic = findViewById(R.id.nic);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        con_password = findViewById(R.id.con_password);
        Log.d("name", String.valueOf(con_password));

        String enteredFirstname = firstName.getText().toString();
        String enteredLastname = last_name.getText().toString();
        String enteredNIC = nic.getText().toString();
        String enteredEmail = email.getText().toString();
        String enteredMobile = mobile.getText().toString();
        String enteredPassword = password.getText().toString();
        String enteredConPassword = con_password.getText().toString();
        Log.d("name", enteredConPassword);

        registration = findViewById(R.id.const_signup);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (enteredFirstname.isEmpty()|| enteredFirstname.equalsIgnoreCase("") ||
                        enteredLastname.isEmpty()|| enteredLastname.equalsIgnoreCase("") ||
                        enteredNIC.isEmpty()|| enteredNIC.equalsIgnoreCase("") ||
                        enteredEmail.isEmpty()|| enteredEmail.equalsIgnoreCase("") ||
                        enteredMobile.isEmpty()|| enteredMobile.equalsIgnoreCase("") ||
                        enteredPassword.isEmpty()|| enteredPassword.equalsIgnoreCase("") ||
                        enteredConPassword.isEmpty()|| enteredConPassword.equalsIgnoreCase("") )
                {
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText("Fields can not be blank!")
                            .show();
                }else if (!enteredPassword.equals(enteredConPassword)){
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText("Confirm password Not Match!")
                            .show();
                }else{
                    Intent intent = new Intent(Registrations.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}