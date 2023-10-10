package com.example.train_management_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.train_management_system.API.API;
import com.example.train_management_system.API.RetrofitInstance;
import com.example.train_management_system.Helpers.MyDatabaseHelper;
import com.example.train_management_system.Models.Booking;
import com.example.train_management_system.Models.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {

    private ListView bookingListView;
    private BookingListAdapter bookingListAdapter;
    private List<Booking> bookingList;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize your ListView and adapter
        bookingListView = findViewById(R.id.bookingListView);

        Call<JsonArray> call = RetrofitInstance
                .get()
                .create(API.class)
                .getBookings();

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Log.d("Dataset2", String.valueOf(response));
                Log.d("Dataset3", String.valueOf(response.code()));
                if (response.isSuccessful() && response.code() == 200) {
                    JsonArray jsonArray = response.body();
                    Log.d("Dataset", String.valueOf(jsonArray));
                    if (jsonArray != null) {
                        Gson gson = new Gson();
                        //Booking booking = gson.fromJson(jsonArray.toString(), Booking.class);


                        Type bookingListType = new TypeToken<List<Booking>>() {}.getType();
                        List<Booking> bookingList = gson.fromJson(jsonArray, bookingListType);
                        bookingListAdapter = new BookingListAdapter(context, bookingList);
                        Log.d("Dataset", String.valueOf(bookingList));

                        // Set the adapter to the ListView
                        bookingListView.setAdapter(bookingListAdapter);
                    }
                } else {
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText("Something went Wrong1!")
                            .show();
                }
            }
            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error")
                        .setContentText("Something went Wrong2!")
                        .show();
            }
        });

    }
}