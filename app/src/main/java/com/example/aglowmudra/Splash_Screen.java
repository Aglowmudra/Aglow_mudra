package com.example.aglowmudra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.LogWriter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splash_Screen.this,Login.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);

        } catch (Exception e) {
            e.getStackTrace();
        }

    }
}
