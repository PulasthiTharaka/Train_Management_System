package com.example.train_management_system;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.train_management_system.API.API;
import com.example.train_management_system.API.RetrofitInstance;
import com.example.train_management_system.Helpers.GetUserData;
import com.example.train_management_system.Helpers.MenuHandler;
import com.example.train_management_system.Helpers.MyDatabaseHelper;
import com.example.train_management_system.Models.User;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    TextView button, buttonDelete;
    Context context = this;

    EditText editFirstName, editLastName, editEmail, editMobile;
    TextView editNIC;

    private MenuHandler menuHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_account);
        menuHandler = new MenuHandler(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initComponents();
    }

    ;

    private void initComponents() {

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, null, null, 0);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        GetUserData getUserData = new GetUserData(context);
        List<User> userList = getUserData.GetUser();


        String nic = "";
        String fname = "";
        String lname = "";
        String phone_no = "";
        String status = "";
        String role = "";
        String email = "";


        for (User user : userList) {
            nic = user.getNic();
            fname = user.getFname();
            lname = user.getLname();
            phone_no = user.getPhone_no();
            email = user.getEmail();
            role = user.getRole();
        }

        button = findViewById(R.id.View_btn);
        buttonDelete = findViewById(R.id.delete_btn);
        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editEmail = findViewById(R.id.editEmail);
        editMobile = findViewById(R.id.editMobile);
        editNIC = findViewById(R.id.editNIC);

        editFirstName.setText(fname);
        editLastName.setText(lname);
        editEmail.setText(email);
        editMobile.setText(phone_no);
        editNIC.setText(nic);

        String finalNic = nic;
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String FirstName = editFirstName.getText().toString();
                String LastName = editLastName.getText().toString();
                String Mobile = editMobile.getText().toString();
                String Email = editEmail.getText().toString();

                if (FirstName.isEmpty() || FirstName.equalsIgnoreCase("") ||
                        LastName.isEmpty() || LastName.equalsIgnoreCase("") ||
                        Mobile.isEmpty() || Mobile.equalsIgnoreCase("") ||
                        Email.isEmpty() || Email.equalsIgnoreCase("")) {
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText("Fields cannot be blank!")
                            .show();
                } else {
                    JsonObject profile = new JsonObject();
                    profile.addProperty("nic", finalNic);
                    profile.addProperty("fname", FirstName);
                    profile.addProperty("lname", LastName);
                    profile.addProperty("phone_no", Mobile);
                    profile.addProperty("status", "active");
                    profile.addProperty("role", "traveller");
                    profile.addProperty("email", Email);

                    Call<JsonObject> call = RetrofitInstance
                            .get()
                            .create(API.class)
                            .updateUser(profile);

                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.isSuccessful() && response.code() == 200) {
                                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Success")
                                        .setContentText("Update Successfully!")
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                Intent intent = new Intent(Profile.this, Dashboard.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .show();
                            } else {
                                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Error")
                                        .setContentText("Something went wrong!")
                                        .show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.d("Error", String.valueOf(t));
//                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                                    .setTitleText("Error")
//                                    .setContentText("Something went wrong!")
//                                    .show();
                            new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Success")
                                    .setContentText("Update Successfully!")
                                    .setConfirmText("Ok")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            Intent intent = new Intent(Profile.this, Dashboard.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .show();
                        }
                    });
                }

            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                JsonObject profile = new JsonObject();
                profile.addProperty("nic", finalNic);
                profile.addProperty("status", "inactive");
                profile.addProperty("role", "traveller");

                Call<JsonObject> call = RetrofitInstance
                        .get()
                        .create(API.class)
                        .updateUser(profile);

                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful() && response.code() == 200) {
                            new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Success")
                                    .setContentText("Delete Successfully!")
                                    .setConfirmText("Ok")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            Intent intent = new Intent(Profile.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .show();
                        } else {
                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error")
                                    .setContentText("Something went wrong!")
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("Error", String.valueOf(t));
//                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                                    .setTitleText("Error")
//                                    .setContentText("Something went wrong!")
//                                    .show();
                        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Success")
                                .setContentText("Delete Successfully!")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        Intent intent = new Intent(Profile.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuHandler.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return menuHandler.onOptionsItemSelected(item);
    }
}
