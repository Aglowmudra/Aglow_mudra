package com.example.aglowmudra;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.os.Build;
import android.os.Bundle;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import java.util.ArrayList;

import okhttp3.FormBody;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utilites.Utils;


public class Login extends AppCompatActivity {
    public static final int REQUEST_READ_CONTACTS = 79;
    private static final String TODO = "";
    TelephonyManager telephonyManager;
    ArrayList arrayList;
    ListView list;
    Button Send_Click;
    EditText Phone_number;
    final ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> phone = new ArrayList<>();
    ArrayList<String> smses = new ArrayList<>();
    ProgressBar progressBar;
    String imeiNumber1 = "";
    String DeviceModel;
    String DeviceName;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Utils.sharedPreferences = getSharedPreferences(Utils.myPref, MODE_PRIVATE);
        Utils.editor = Utils.sharedPreferences.edit();

        Log.d("TAG", "Created Function");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG","permission is checked");
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE},
                    REQUEST_READ_CONTACTS);

        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            Log.d("TAG", "Value is created by system 234567");
            requestPermission();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", "Value is created by system ");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                Log.d("TAG", "Value is creted by 1234" + arrayList);

            }
        } else {
            Log.d("TAG", "Value when is created");
            requestPermission();
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            }

        } catch (Exception e) {
            e.getStackTrace();

        }
        initi();
        try {
            // getsms();

        } catch (Exception e) {
            e.getStackTrace();
        }

        DeviceModel = android.os.Build.MODEL;
        Log.d("TAG", "Model Name" + DeviceModel);
        DeviceName = android.os.Build.MANUFACTURER;
        Log.d("TAG", "Device Name " + DeviceName);
//        getAllContacts();

        Send_Click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(Login.this, "woking", Toast.LENGTH_LONG).show();
                if (isValidate() && isValidatenumber()) {
                    progressBar.setVisibility(View.VISIBLE);
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            LoginAttempt1();

                        }

                    });
                    thread.start();
                }
            }
        });

    }

    private boolean isValidate() {
        if (Phone_number.getText().toString().isEmpty()) {
            Phone_number.setError("Enter Your Phone Number");
            return false;
        }
        return true;
    }

    private boolean isValidatenumber() {
        if (Phone_number.getText().length() != 10) {
            Toast.makeText(this, "Enter a valid Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_CONTACTS) &&
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) &&
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE) &&
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
        ) {
            // show UI part if you want here to show some rationale !!!
            Log.d("TAG", "Value is Creted permission jnot Granted");
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE},
                    REQUEST_READ_CONTACTS);

        } else {

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

                    requestPermission();
                    boolean showRational = shouldShowRequestPermissionRationale(permission);
                    Log.d("TAG", "Value is credted" + showRational);
                    if (showRational) {

                        requestPermission();


                    } else {
                        Log.d("TAG", "permission is not granted");
                    }


                }


            }


        }


    }


    public void initi() {
        Send_Click = findViewById(R.id.Sendffff);
        Phone_number = findViewById(R.id.PhoneNumber);
        progressBar = findViewById(R.id.Prgogressbar);

    }




    // GFunction for Login
    public void LoginAttempt1() {
        Log.d("TAG", "Value is  Login Attempt");
        String Phone_no = Phone_number.getText().toString();
        Log.d("TAG", "VAlue is creted by Login" + Phone_no);

        String url = "https://drfin.in/aglowcredit/api/sendOtp";
        RequestBody formBody = new FormBody.Builder()
                .add("mobile", Phone_no)
                .build();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();


        try {
            Response response = client.newCall(request).execute();
            Log.d("TAG", "Alue is response1V" + response);
            String responseString = response.body().string();

            Log.d("TAG", "VAlue is response22323" + responseString);
            try {
                JSONObject json = new JSONObject(responseString);
                //  String authvalue=json.getString("auth_token");
                //  Log.d("TAG","VAlue is created by auth"+authvalue);
                String Auth_token = json.getJSONObject("userdata").getString("auth_token");

                Log.d("TAG", "Value for code" + Auth_token);

                Utils.editor.putString("Auth_token", Auth_token);
                Utils.editor.commit();
                String value = Utils.sharedPreferences.getString("Auth_token", "");
                Log.d("TAG", "VAlue of refference" + value);

                String Active = json.getJSONObject("userdata").getString("is_active");
                Log.d("TAG", "value is frrrrrr54688" + Active);

                if (Active.equals("1")) {
                    Log.d("TAG", "VAlue is in password Activity");
                    Intent intent = new Intent(Login.this, PasswordLogin.class);
                    intent.putExtra("Phone_number", Phone_no);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);

                    finish();
                } else {
                    Log.d("TAG", "VAlue is in Verfication Activity");
                    Intent intent = new Intent(Login.this, VerficationCode.class);
                    Log.d("TAG", "Value is creted after Intent");
                    intent.putExtra("Phone_number", Phone_no);
                    startActivity(intent);

                    progressBar.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.getStackTrace();

            }


        } catch (Exception e) {
            e.getStackTrace();
        }
    }



}
