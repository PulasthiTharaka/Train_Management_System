package com.example.train_management_system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_booking, parent, false);
        }

        TextView bookNameTextView = convertView.findViewById(R.id.bookingNameTextView);

        Booking booking = BookingList.get(position);

        // Customize how you display the booking data in the ListView
        bookNameTextView.setText(booking.getName() + " " + booking.getTrainname());

        return convertView;
    }
}
