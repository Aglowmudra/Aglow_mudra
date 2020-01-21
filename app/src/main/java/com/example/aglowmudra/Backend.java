package com.example.aglowmudra;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class Backend extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAG","Value is cretec service"+flags +" "+startId);
        return startId;

    }
}
