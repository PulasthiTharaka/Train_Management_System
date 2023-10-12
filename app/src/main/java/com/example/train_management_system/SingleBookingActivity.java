package com.example.train_management_system;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.train_management_system.Models.Booking;

public class SingleBookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        trainNameTextView.setText(trainName);
        bookingDateTextView.setText(bookingDate);
        ticketsTextView.setText(tickets);
        reservationDateTextView.setText(reservationDate);
        nameTextView.setText(name);
        nicTextView.setText(nic);
    }
}
