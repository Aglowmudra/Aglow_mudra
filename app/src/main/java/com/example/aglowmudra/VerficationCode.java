package com.example.aglowmudra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VerficationCode extends AppCompatActivity {
    Button button;
    EditText Code_value;
    String Phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfication_code);
        initi();
        Intent intent = getIntent();
        Phone_number = intent.getStringExtra("Phone_number");
        Log.d("TAG", "VAlue od code phone" + Phone_number);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SendOtpApi();
                    }
                });
                thread.start();

            }
        });

    }

    public void initi() {
        button = (Button) findViewById(R.id.otpSend);
        Code_value = (EditText) findViewById(R.id.OTPText);

    }

    public void SendOtpApi() {
        Log.d("TAG", "Value is creted in otp");
        String Phone_No = Code_value.getText().toString();
     /*   OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "mobile="+Phone_number+"&otp="+Phone_No);
        Request request = new Request.Builder()
                .url("https://drfin.in/aglowcredit/api/verifyOtp")
                .post(body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .addHeader("Postman-Token", "7aea7212-a815-4434-a703-f0d66866f57b")
                .build();*/
       /* OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "mobile=8010616827&otp=1234");
        Request request = new Request.Builder()
                .url("https://drfin.in/aglowcredit/api/verifyOtp")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .build(); */
   /*     OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
        RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"mobile\"\r\n\r\n8010616827\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"otp\"\r\n\r\n1234\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
        Request request = new Request.Builder()
                .url("https://drfin.in/aglowcredit/api/verifyOtp")
                .post(body)
                .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "910f37a9-4930-ae8f-89c7-d37fe824c48d")
                .build();


*/
        String url = "https://drfin.in/aglowcredit/api/verifyOtp";
        RequestBody formBody = new FormBody.Builder()
                .add("mobile", Phone_number)
                .add("otp", Phone_No)
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
            Log.d("TAG", "VAlue is response2" + responseString);
            if (response.message().contains("is_active"))
            if (response.code()==200){
                Intent intent=  new Intent(this,MainActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(this, "Check Otp", Toast.LENGTH_SHORT).show();
            }
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(responseString);

            try {
                JSONObject json = new JSONObject(String.valueOf(response));
                Log.d("TAG", "VAlue is creted3245267" + json.getJSONObject("userdata").getString("is_active"));
            } catch (JsonIOException e) {
                e.getStackTrace();
            }
//            txtString.setText(json.getJSONObject("data").getString("first_name")+ " "+json.getJSONObject("data").getString("last_name"));


            response.body().close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
