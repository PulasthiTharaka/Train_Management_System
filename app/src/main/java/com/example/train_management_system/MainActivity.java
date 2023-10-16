package com.example.train_management_system;


import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.train_management_system.API.API;
import com.example.train_management_system.API.RetrofitInstance;
import com.example.train_management_system.Helpers.MenuHandler;
import com.example.train_management_system.Helpers.MyDatabaseHelper;
import com.example.train_management_system.Methods;
import com.example.train_management_system.Models.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends AppCompatActivity {

    TextView registration;
    EditText nic, password;
    Context context = this;
    public Menu menu;
    public ConstraintLayout signIn;

    private MenuHandler menuHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.opening_screen);
        initComponents();
    }


    private void initComponents() {

        signIn = findViewById(R.id.const_signIn);
        nic = findViewById(R.id.et_nic);
        password = findViewById(R.id.et_password);
        registration = findViewById(R.id.tv_btn_sign_up);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredNic = nic.getText().toString();
                String enteredPassword = password.getText().toString();

                if (enteredNic.isEmpty()|| enteredNic.equalsIgnoreCase("")) {

                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText("Please Enter User Name!")
                            .show();
                }else if(enteredPassword.isEmpty()|| enteredPassword.equalsIgnoreCase("")){
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText("Please Enter Password!")
                            .show();
                }else{

                    Gson gson = new Gson();
                    JsonObject user = new JsonObject();
                    user.addProperty("nic", enteredNic);
                    user.addProperty("password", enteredPassword);

                    Log.d("name", String.valueOf(user));

                    Call<JsonObject> call = RetrofitInstance
                            .get()
                            .create(API.class)
                            .login(user);


                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.isSuccessful() && response.code() == 200) {
                                JsonObject jsonObject = response.body();

                                if (jsonObject != null) {
                                    User user = gson.fromJson(jsonObject.toString(), User.class);
                                    Log.d("Dataset23", String.valueOf(jsonObject));
                                    Log.d("Dataset24", String.valueOf(user));
                                    Log.d("Dataset24", String.valueOf(user.getFname()));
                                    // Initialize the database helper
                                    MyDatabaseHelper dbHelper = new MyDatabaseHelper(context,null,null,0);

                                    // Create or open the database (this will trigger the onCreate method if the database doesn't exist)
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                                    ContentValues values = new ContentValues();
                                    //values.put("uid", user.getId());
                                    values.put("fname", user.getFname());
                                    values.put("lname", user.getLname());
                                    values.put("nic", user.getNic());
                                    values.put("phone_no", user.getPhone_no());
                                    values.put("status", user.getStatus());
                                    values.put("role", user.getRole());
                                    values.put("password", user.getPassword());
                                    values.put("email", user.getEmail());

                                    long newRowId = db.insert("users", null, values);
                                    Log.d("UserData", String.valueOf(newRowId));

                                    // Close the database
                                    dbHelper.close();

                                    Intent intent = new Intent(MainActivity.this, Dashboard.class);
                                    startActivity(intent);
                                }
                            } else {
                                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Error")
                                        .setContentText("Invalid User Name Or Password!")
                                        .show();
                            }
                        }
                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.d("Dataset22", String.valueOf(t));
                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error")
                                    .setContentText("Something went Wrong!")
                                    .show();
                        }
                    });
                }
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registrations.class);
                startActivity(intent);
            }
        });

    }
}