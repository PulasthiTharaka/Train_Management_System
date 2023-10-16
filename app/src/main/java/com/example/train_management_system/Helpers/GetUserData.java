package com.example.train_management_system.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.train_management_system.Models.User;

import java.util.ArrayList;
import java.util.List;

public class GetUserData {

    private Context context;

    public GetUserData(Context context) {
        this.context = context;
    }
    public List<User> GetUser(){

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context,null,null,0);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "id",
                "fname",
                "lname",
                "nic",
                "phone_no",
                "status",
                "role",
                "password",
                "email"
        };

        Cursor cursor = db.query(
                "users",
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List<User> userList = new ArrayList<>();

        while (cursor.moveToNext()) {
            User user = new User();
            user.setId(cursor.getString(cursor.getColumnIndexOrThrow("id")));
            user.setFname(cursor.getString(cursor.getColumnIndexOrThrow("fname")));
            user.setLname(cursor.getString(cursor.getColumnIndexOrThrow("lname")));
            user.setNic(cursor.getString(cursor.getColumnIndexOrThrow("nic")));
            user.setPhone_no(cursor.getString(cursor.getColumnIndexOrThrow("phone_no")));
            user.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
            user.setRole(cursor.getString(cursor.getColumnIndexOrThrow("role")));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));

            userList.add(user);
        }

        cursor.close();
        dbHelper.close();
        return userList;
    }
}
