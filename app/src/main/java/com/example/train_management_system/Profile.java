package com.example.train_management_system;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.train_management_system.Helpers.MenuHandler;

import java.util.Calendar;

public class Profile extends AppCompatActivity {

    TextView button;

    private MenuHandler menuHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_account);
        menuHandler = new MenuHandler(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initComponents();
    };

    private void initComponents() {

        button = findViewById(R.id.View_btn);

        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


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
