package com.example.train_management_system.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "train.db";
    private static final int DATABASE_VERSION = 2;
    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                //+ "uid TEXT,"
                + "fname TEXT,"
                + "lname TEXT,"
                + "nic TEXT,"
                + "phone_no TEXT,"
                + "status TEXT,"
                + "role TEXT,"
                + "password TEXT,"
                + "email TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS train_details ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "trainname TEXT,"
                + "date TEXT,"
                + "starttime TEXT,"
                + "endtime TEXT,"
                + "description TEXT,"
                + "noofsheet TEXT,"
                + "ticketprice TEXT,"
                + "status TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS train_details");
        onCreate(db);
    }

    public boolean isTableEmpty(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = { "COUNT(*)" };

        Cursor cursor = db.query(tableName, projection, null, null, null, null, null);

        int rowCount = 0;
        if (cursor.moveToFirst()) {
            rowCount = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return rowCount > 0;
    }

}
