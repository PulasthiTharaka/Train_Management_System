package com.example.train_management_system;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.train_management_system.Helpers.MenuHandler;


public class AddBooking extends AppCompatActivity {

    private MenuHandler menuHandler;

    TextView button;

    private TextView singleBookingDateView;
    private Calendar calendar;

    EditText train_name,booking_date, reservation_date,no_of_tickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_add);
        menuHandler = new MenuHandler(this);
        initComponents();
    };

    private void initComponents() {

        button = findViewById(R.id.add_btn);

        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
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
        return menuHandler.onOptionsItemSelected(item);
    }

    public void showDatePicker(View view) {
        singleBookingDateView = findViewById(R.id.singleBookingDate);
        if (view.getId() == R.id.singleBookingDate) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    // Handle the selected date here
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    singleBookingDateView.setText(selectedDate);
                }
            }, year, month, day);

            datePickerDialog.show();
        }
    }

    private void updateDateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        singleBookingDateView.setText(sdf.format(calendar.getTime()));
    }

}
