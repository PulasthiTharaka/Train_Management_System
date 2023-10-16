package com.example.train_management_system.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.example.train_management_system.AddBooking;
import com.example.train_management_system.Dashboard;
import com.example.train_management_system.MainActivity;
import com.example.train_management_system.Profile;
import com.example.train_management_system.R;
import com.example.train_management_system.Registrations;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MenuHandler {

    private Context context;

    public MenuHandler(Context context) {
        this.context = context;
    }

    public void onCreateOptionsMenu(Menu menu) {
        ((Activity) context).getMenuInflater().inflate(R.menu.nav_bar, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                handleProfileMenuItem();
                return true;
            case R.id.action_signout:
                handleSignOutMenuItem();
                return true;
            default:
                return false;
        }
    }

    private void handleProfileMenuItem() {
        Intent intent = new Intent(context, Profile.class);
        context.startActivity(intent);
    }

    private void handleSignOutMenuItem() {
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Are You Sure?")
                .setContentText("Log Out the System")
                .setConfirmText("Ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    }
                })
                .show();
    }
}
