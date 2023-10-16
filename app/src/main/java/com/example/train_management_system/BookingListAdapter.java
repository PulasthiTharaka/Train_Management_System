package com.example.train_management_system;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.train_management_system.Models.Booking;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Callback;

public class BookingListAdapter extends BaseAdapter {

    private Context context;
    private List<Booking> BookingList;

    public BookingListAdapter(Context context, List<Booking> BookingList) {
        this.context = context;
        this.BookingList = BookingList;
    }

    @Override
    public int getCount() {
        return BookingList.size();
    }

    @Override
    public Object getItem(int position) {
        return BookingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_booking, parent, false);
        //}

        TextView bookTrain = convertView.findViewById(R.id.bookingTrain);
        TextView bookDate = convertView.findViewById(R.id.bookingDate);
        TextView bookTicket = convertView.findViewById(R.id.bookingTicket);
        TextView reservationDate = convertView.findViewById(R.id.reservationDate);

        TextView View_btn = convertView.findViewById(R.id.View_btn);

        Booking booking = BookingList.get(position);

        // Customize how you display the booking data in the ListView
        bookTrain.setText(booking.getTrainname());
        bookDate.setText("Booked Date - "+booking.getBookingdate());
        bookTicket.setText("Tickets - "+booking.getNooftickets());
        reservationDate.setText("Reserve Date - "+booking.getReservationdate());
        View_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleBookingActivity.class);
                intent.putExtra("trainName", booking.getTrainname());
                intent.putExtra("bookingDate", booking.getBookingdate());
                intent.putExtra("tickets", booking.getNooftickets());
                intent.putExtra("reservationDate", booking.getReservationdate());
                intent.putExtra("name", booking.getName());
                intent.putExtra("nic", booking.getNic());

                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
