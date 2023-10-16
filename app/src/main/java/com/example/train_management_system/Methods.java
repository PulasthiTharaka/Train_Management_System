package com.example.train_management_system;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.AccessControlContext;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Methods {
    private static Methods instance;

    public static Methods getInstance() {
        if (instance == null) {
            instance = new Methods();
        }
        return instance;
    }

    public static String getBaseURL() {
        return "http://192.168.8.197:80/api/";
    }
}
