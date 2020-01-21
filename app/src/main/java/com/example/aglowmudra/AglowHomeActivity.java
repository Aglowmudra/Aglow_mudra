package com.example.aglowmudra;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AglowHomeActivity extends AppCompatActivity {
    final ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> phone = new ArrayList<>();
    double latitute;
    double longitude;
    String val1 = "";
    String val2 = "";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aglow_home);

        FragmentPolicy fragmentPolicy = new FragmentPolicy();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentPolicy.show(fragmentManager, "policy_mode");


    }



    }

