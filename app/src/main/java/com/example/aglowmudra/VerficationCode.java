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

import Notifcation.Message;
import okhttp3.Call;
import okhttp3.Callback;
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
    EditText Passsword, ConformPassword;

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
                Log.d("TAG","Value is creted");
                if (isValidate() && iS_VALIDATE()) {
//                    Toast.makeText(VerficationCode.this, "working in utton", Toast.LENGTH_SHORT).show();
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SendOtpApi();
                        }
                    });
                    thread.start();
                }

            }
        });

    }

    public void initi() {
        button = (Button) findViewById(R.id.register);
        Code_value = (EditText) findViewById(R.id.otp);
        Passsword = findViewById(R.id.EnterPassword);
        ConformPassword = findViewById(R.id.Confirmpassword);

    }

    public void SendOtpApi() {
        Log.d("TAG", "Value is creted in otp");
        String Phone_No = Code_value.getText().toString();
        String Passwordotp = Passsword.getText().toString();

        String url = "https://drfin.in/aglowcredit/api/verifyOtp";
        RequestBody formBody = new FormBody.Builder()
                .add("mobile", Phone_number)
                .add("otp", Phone_No)
                .add("password", Passwordotp)
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

            Log.d("TAG", "VAlue is response223" + responseString);
            try {
                JSONObject json = new JSONObject(responseString);

                String result_code = json.getString("result_code");

                Log.d("TAG", "Value for code" + result_code);

                if (result_code.equals("400")) {
                    Log.d("TAG","Value is creted in 400 code Value");

                    String text="OTP did not match";
                    Code_value.setText(text);
//                    Message.Messageshow(VerficationCode.this,"otp is not match");

//                    Toast.makeText(VerficationCode.this, "Check your OTP", Toast.LENGTH_SHORT).show();
                    Log.d("TAG","Value is creted in 401 code Value");
                } else {
                    Log.d("TAG","Value is creted in 200 code Value");
                    Intent intent = new Intent(VerficationCode.this, AglowHomeActivity.class);
                    startActivity(intent);

                }
            } catch (Exception e) {
                e.getStackTrace();

            }


            response.body().close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private boolean isValidate() {
        String Password = Passsword.getText().toString();
        String cpassword = ConformPassword.getText().toString();
        if (!Password.contentEquals(cpassword)) {
            ConformPassword.setError("Please Match the Password");
            return false;
        }

        return true;
    }

    private boolean iS_VALIDATE() {
        if (Code_value.getText().toString().isEmpty()) {
            Code_value.setError("Please Enter Otp");
            return false;
        }
        return true;
    }

}