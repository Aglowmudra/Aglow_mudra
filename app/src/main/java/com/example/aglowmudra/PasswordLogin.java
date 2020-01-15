package com.example.aglowmudra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import Fragment.FragmentPolicy;
import Fragment.WrongPassword;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PasswordLogin extends AppCompatActivity {
    String Phone_number;
    Button click;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_login);
        Intent intent = getIntent();
        Phone_number = intent.getStringExtra("Phone_number");
        Log.d("TAG", "Value in Password" + Phone_number);
        initi();
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate()) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            CheckPassword();
                        }
                    });
                    thread.start();
                }
            }
        });
    }

    public void initi() {
        click = findViewById(R.id.LoginP);
        password = findViewById(R.id.PasswordLo);


    }

    public void CheckPassword() {
        String Pass = password.getText().toString();
        Log.d("TAG", "VAlue is creted Password" + Pass);
        String url = "https://drfin.in/aglowcredit/api/login";
        RequestBody formBody = new FormBody.Builder()
                .add("mobile", Phone_number)
                .add("password", Pass)
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
                String State = json.getString("result_code");
                Log.d("TAG", "value is frrrrrr54688" + State);
                if (State.equals("200")){
                    startActivity(new Intent(getApplication(),AglowHomeActivity.class));

                }
               else {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
//                            Toast.makeText(PasswordLogin.this, "invalid id or password", Toast.LENGTH_LONG).show();
                        password.setError("please Enter Correct Password");

                        }
                    });
                }
            } catch (Exception e) {
                e.getStackTrace();

            }


        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    private boolean isValidate() {

        if (password.getText().toString().isEmpty()) {
            password.setError("Please Fill The Password");
            return false;
        }
        return true;
    }


}
