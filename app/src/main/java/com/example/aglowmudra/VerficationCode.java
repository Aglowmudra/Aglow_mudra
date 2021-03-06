package com.example.aglowmudra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONObject;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VerficationCode extends AppCompatActivity {
    Button Register;
    EditText Code_value;
    String Phone_number;
    EditText Passsword, ConformPassword;
    TextView Resend;
    String value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfication_code);
        initi();
        Intent intent = getIntent();
        Phone_number = intent.getStringExtra("Phone_number");
        Log.d("TAG", "VAlue od code phone" + Phone_number);
        CountTime();
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "Value is creted");
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
        Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Resend.setVisibility(View.GONE);
                count();
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Resend_Otp();


                    }
                });
                thread.start();

            }
        });

    }

    public void initi() {
        Register = (Button) findViewById(R.id.register);
        Code_value = (EditText) findViewById(R.id.otp);
        Passsword = findViewById(R.id.EnterPassword);
        ConformPassword = findViewById(R.id.Confirmpassword);
        Resend = findViewById(R.id.resendotp);

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
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
//                            Toast.makeText(PasswordLogin.this, "invalid id or password", Toast.LENGTH_LONG).show();
                            Code_value.setError("please Enter Correct OTP");

                        }
                    });
                } else {
                    Log.d("TAG", "Value is creted in 200 code Value");
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

    public void count() {
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
//                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
//                mTextField.setText("done!");
            }
        }.start();

    }

    public void Resend_Otp() {
        Intent intent = getIntent();
        Phone_number = intent.getStringExtra("Phone_number");
       Log.d("TAG","VAlue is created"+Phone_number);

        String url = "https://drfin.in/aglowcredit/api/sendOtp";
        RequestBody formBody = new FormBody.Builder()
                .add("mobile", Phone_number)
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

            Log.d("TAG", "VAlue is response in Resend Actcivity" + responseString);

        }catch (Exception e){
            e.getStackTrace();
        }
    }



    public void CountTime() {
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                Resend.setText("                   " + millisUntilFinished / 1000);
            }

            public void onFinish() {

                Resend.setVisibility(View.VISIBLE);
                Resend.setText("Rsend OTP");
            }
        }.start();
    }
}