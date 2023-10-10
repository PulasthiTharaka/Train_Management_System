package com.example.train_management_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.train_management_system.API.API;
import com.example.train_management_system.API.RetrofitInstance;
import com.example.train_management_system.Helpers.MyDatabaseHelper;
import com.example.train_management_system.Models.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        registration = findViewById(R.id.const_signup);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                Log.d("name", enteredFirstname);

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
                    Gson gson = new Gson();
                    JsonObject user = new JsonObject();
                    user.addProperty("fname", enteredFirstname);
                    user.addProperty("lname", enteredLastname);
                    user.addProperty("nic", enteredNIC);
                    user.addProperty("phone_no", enteredMobile);
                    user.addProperty("status", "active");
                    user.addProperty("role", "traveller");
                    user.addProperty("password", enteredPassword);
                    user.addProperty("email", enteredEmail);


                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("All Details are Ok?")
                            .setConfirmText("Yes,Register!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    Call<JsonObject> call = RetrofitInstance
                                            .get()
                                            .create(API.class)
                                            .register(user);

                                    call.enqueue(new Callback<JsonObject>() {
                                        @Override
                                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                            if (response.isSuccessful() && response.code() == 200) {
                                                JsonObject jsonObject = response.body();

                                                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                                        .setTitleText("Success")
                                                        .setContentText("Please Login To the System")
                                                        .setConfirmText("Ok")
                                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sDialog) {
                                                                Intent intent = new Intent(Registrations.this, MainActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        })
                                                        .show();
                                            } else if(response.code() == 400){
                                                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                                        .setTitleText("Error")
                                                        .setContentText("User with this NIC is already registered!")
                                                        .show();
                                            }else{
                                                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                                        .setTitleText("Error")
                                                        .setContentText("Something went Wrong1!")
                                                        .show();
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<JsonObject> call, Throwable t) {
                                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("Error")
                                                    .setContentText("Something went Wrong!")
                                                    .show();
                                        }
                                    });
                                }
                            })
                            .show();
                }
            }
        });

    }
}