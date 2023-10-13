package com.example.train_management_system;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class Loading extends AppCompatActivity {

    private static final long DELAY_MILLIS = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening_screen);
        // Use a Handler to delay the launch of the main activity
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                signInPageLoad();
            }
        }, DELAY_MILLIS);
    }

    public void signInPageLoad(){
        Intent intent = new Intent(Loading.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
