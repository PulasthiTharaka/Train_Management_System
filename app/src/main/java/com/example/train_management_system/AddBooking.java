package com.example.train_management_system;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.train_management_system.API.API;
import com.example.train_management_system.API.RetrofitInstance;
import com.example.train_management_system.Helpers.GetUserData;
import com.example.train_management_system.Helpers.MenuHandler;
import com.example.train_management_system.Helpers.MyDatabaseHelper;
import com.example.train_management_system.Models.Booking;
import com.example.train_management_system.Models.TrainDetails;
import com.example.train_management_system.Models.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AddBooking extends AppCompatActivity {

    private MenuHandler menuHandler;

    TextView button;

    Context context = this;

    private Spinner trainNameSpinner;

    private TextView singleBookingDateView, singleBookingDateReservation;
    private Calendar calendar;

    public ConstraintLayout addBooking;

    EditText train_name, reservation_date, no_of_tickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_add);
        menuHandler = new MenuHandler(this);
        trainNameSpinner = findViewById(R.id.trainNameSpinner);
        initComponents();
    }

    ;

    private void initComponents() {

        addBooking = findViewById(R.id.const_add);

        addBooking.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                trainNameSpinner = findViewById(R.id.trainNameSpinner);
                //singleBookingDateView = findViewById(R.id.singleBookingDate);
                no_of_tickets = findViewById(R.id.singleBookingTickets);
                singleBookingDateReservation = findViewById(R.id.singleBookingDateReservation);

                String trainName = trainNameSpinner.getSelectedItem().toString();
                //String bookingDate = singleBookingDateView.getText().toString();
                String tickets = no_of_tickets.getText().toString();
                String reservationDate = singleBookingDateReservation.getText().toString();

                MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, null, null, 0);
                SQLiteDatabase db = dbHelper.getReadableDatabase();

//                String tableName = "users";
//
//                boolean isTableNotEmpty = dbHelper.isTableEmpty(tableName);
//                Log.d("UserData", String.valueOf(isTableNotEmpty));
//
//                if (isTableNotEmpty) {
//                } else {
//                }
//                db.close();

                GetUserData getUserData = new GetUserData(context);
                List<User> userList = getUserData.GetUser();

                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                String formattedDate = sdf.format(currentDate);
                String nic="";
                String name ="";


                for (User user : userList) {
                        nic = user.getNic();
                        name = user.getFname() + " " + user.getLname();
                }
                Gson gson = new Gson();
                JsonObject booking = new JsonObject();
                booking.addProperty("nic", nic);
                booking.addProperty("trainName", trainName);
                booking.addProperty("bookingdate", formattedDate);
                booking.addProperty("name", name);
                booking.addProperty("nooftickets", tickets);
                booking.addProperty("role", "traveller");
                booking.addProperty("reservationdate", reservationDate);
                booking.addProperty("status", "active");


                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("All Details are Ok?")
                        .setConfirmText("Yes,Add Now!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Call<JsonObject> call = RetrofitInstance
                                        .get()
                                        .create(API.class)
                                        .addBooking(booking);

                                call.enqueue(new Callback<JsonObject>() {
                                    @Override
                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                        if (response.isSuccessful() && response.code() == 200) {
                                            JsonObject jsonObject = response.body();

                                            new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                                    .setTitleText("Success")
                                                    .setContentText("Booking Added Successfully")
                                                    .setConfirmText("Ok")
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sDialog) {
                                                            Intent intent = new Intent(AddBooking.this, Dashboard.class);
                                                            startActivity(intent);
                                                        }
                                                    })
                                                    .show();
                                        } else if(response.code() == 400){
                                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("Error")
                                                    .setContentText("Something went Wrong!")
                                                    .show();
                                        }else{
                                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("Error")
                                                    .setContentText("Something went Wrong!")
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
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    singleBookingDateView.setText(selectedDate);
                }
            }, year, month, day);

            datePickerDialog.show();
        }
    }

    public void showDatePicker_Reservation(View view) {
        singleBookingDateReservation = findViewById(R.id.singleBookingDateReservation);
        if (view.getId() == R.id.singleBookingDateReservation) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    singleBookingDateReservation.setText(selectedDate);
                    getTrainNames(selectedDate);
                }
            }, year, month, day);

            datePickerDialog.show();
        }
    }
//    private void updateDateLabel() {
//        String myFormat = "dd/MM/yyyy";
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//        singleBookingDateView.setText(sdf.format(calendar.getTime()));
//    }

    private void getTrainNames(String selectedDate) {

        Call<JsonArray> call = RetrofitInstance
                .get()
                .create(API.class)
                .getTrains();

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    JsonArray jsonArray = response.body();
                    if (jsonArray != null) {
                        Gson gson = new Gson();

                        Type trainListType = new TypeToken<List<TrainDetails>>() {
                        }.getType();
                        List<TrainDetails> trainList = gson.fromJson(jsonArray, trainListType);
                        List<String> trainNames = new ArrayList<>();

                        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, null, null, 0);

                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        for (TrainDetails train : trainList) {
                            ContentValues values = new ContentValues();
                            values.put("trainname", train.getTrainname());
                            values.put("date", train.getDate());
                            values.put("starttime", train.getStarttime());
                            values.put("endtime", train.getEndtime());
                            values.put("description", train.getDescription());
                            values.put("noofsheet", train.getNoofsheet());
                            values.put("ticketprice", train.getTicketprice());
                            values.put("status", train.getStatus());

                            long newRowId = db.insert("train_details", null, values);
                            Log.d("UserData", String.valueOf(newRowId));
                        }

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        Date selectedDateObject = null;
                        try {
                            selectedDateObject = sdf.parse(selectedDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Log.d("Dataset1", String.valueOf(selectedDateObject));

                        if (selectedDateObject != null) {
                            for (TrainDetails train : trainList) {
                                Date trainDateObject = null;
                                try {
                                    trainDateObject = sdf.parse(train.getDate());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Log.d("Dataset2", String.valueOf(trainDateObject));
                                if (trainDateObject != null && trainDateObject.equals(selectedDateObject)) {
                                    trainNames.add(train.getTrainname());
                                }
                            }
                        }
                        Log.d("Dataset", String.valueOf(trainNames));
                        if (trainNames != null && !trainNames.isEmpty()) {
                            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(AddBooking.this, android.R.layout.simple_spinner_item, trainNames);
                            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            trainNameSpinner.setAdapter(spinnerAdapter);
                        } else {
                            trainNameSpinner.setSelection(-1);
                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error")
                                    .setContentText("No Train Available for this Date!")
                                    .show();
                        }
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
