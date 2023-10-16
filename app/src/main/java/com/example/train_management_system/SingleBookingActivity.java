package com.example.train_management_system;

import android.content.Context;
import android.content.Intent;
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
import com.example.train_management_system.Helpers.MenuHandler;
import com.example.train_management_system.Models.Booking;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleBookingActivity extends AppCompatActivity {

    Context context = this;
    private MenuHandler menuHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuHandler = new MenuHandler(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_single_booking);

        Intent intent = getIntent();
        String trainName = intent.getStringExtra("trainName");
        String bookingDate = intent.getStringExtra("bookingDate");
        String tickets = intent.getStringExtra("tickets");
        String reservationDate = intent.getStringExtra("reservationDate");
        String name = intent.getStringExtra("name");
        String nic = intent.getStringExtra("nic");

        // Update the views in the single item layout with the booking details
        TextView trainNameTextView = findViewById(R.id.singleBookingTrain);
        TextView bookingDateTextView = findViewById(R.id.singleBookingDate);
        EditText ticketsTextView = findViewById(R.id.singleBookingTickets);
        TextView reservationDateTextView = findViewById(R.id.singleBookingReservationDate);
        TextView nameTextView = findViewById(R.id.singleBookingName);
        TextView nicTextView = findViewById(R.id.singleBookingNic);
        TextView updateButton = findViewById(R.id.View_btn);

        trainNameTextView.setText(trainName);
        bookingDateTextView.setText(bookingDate);
        ticketsTextView.setText(tickets);
        reservationDateTextView.setText(reservationDate);
        nameTextView.setText(name);
        nicTextView.setText(nic);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ticketCount = ticketsTextView.getText().toString();

                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                String formattedDate = sdf.format(currentDate);

                try {

                    // Parse the reservationDate and formattedDate to Date objects
                    Date reservationDateObj = sdf.parse(reservationDate);
                    Date formattedDateObj = sdf.parse(formattedDate);

                    long dateDifference = reservationDateObj.getTime() - formattedDateObj.getTime();

                    long daysDifference = dateDifference / (1000 * 60 * 60 * 24);

                    if (daysDifference < 5) {
                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error")
                                .setContentText("You Can not Update data!")
                                .show();
                    } else {
                        if (ticketCount.isEmpty() || ticketCount.equalsIgnoreCase("")) {
                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error")
                                    .setContentText("Fields cannot be blank!")
                                    .show();
                        }
                        else if(Integer.parseInt(ticketCount)>4){
                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error")
                                    .setContentText("Cannot book more than 04 tickets!")
                                    .show();
                        }else{
                            JsonObject booking = new JsonObject();
                            booking.addProperty("nic", nic);
                            booking.addProperty("trainname", trainName);
                            booking.addProperty("bookingdate", bookingDate);
                            booking.addProperty("name", name);
                            booking.addProperty("nooftickets", ticketCount);
                            booking.addProperty("role", "traveller");
                            booking.addProperty("reservationdate", reservationDate);
                            booking.addProperty("status", "active");

                            Call<JsonObject> call = RetrofitInstance
                                    .get()
                                    .create(API.class)
                                    .updateBooking(booking);

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
                                                        Intent intent = new Intent(SingleBookingActivity.this, Dashboard.class);
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
                                                    Intent intent = new Intent(SingleBookingActivity.this, Dashboard.class);
                                                    startActivity(intent);
                                                }
                                            })
                                            .show();
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
