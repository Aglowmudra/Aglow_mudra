package com.example.aglowmudra;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    public static final int REQUEST_READ_CONTACTS = 79;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", "Value is created by system ");
        } else {
            Log.d("TAG", "Value when is created");
            requestPermission();
        }


    }


    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_CONTACTS) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // show UI part if you want here to show some rationale !!!
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA},
                    REQUEST_READ_CONTACTS);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("TAG", "VAlue is created in persion not granted");
        if (requestCode == REQUEST_READ_CONTACTS) {
            Log.d("TAG", "value fisrt step");
            for (int i = 0, len = permissions.length; i < len; i++) {
                Log.d("TAG", "value" + permissions);
                String permission = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    boolean showRational = shouldShowRequestPermissionRationale(permission);
                    Log.d("TAG", "Value is credted" + showRational);
                    if (!showRational) {
                        if (showRational == false) {
                            Log.d("TAG", "permission is  granted");
                            requestPermission();

                        }

                    } else {
                        Log.d("TAG", "permission is not granted");
                    }


                }


            }


        }

    }
}
