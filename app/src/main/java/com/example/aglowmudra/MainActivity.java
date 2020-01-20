package com.example.aglowmudra;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
  private String encodedImageString = null;
  private static final int PICK_IMAGE_REQUEST = 100;
  ArrayList<String> arrayList = new ArrayList<>();

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    intent.setType("image/*");
    startActivityForResult(intent, PICK_IMAGE_REQUEST);

  }

  @SuppressLint("MissingSuperCall")
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case PICK_IMAGE_REQUEST:
        if (resultCode == RESULT_OK) {

            Uri selectedImage = data.getData();
            Log.d("TAG", "value of selectpath" + selectedImage);
            Bitmap bitmap;
            try {


                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                String image = ConvertBitmapToString(resizedBitmap);
                Log.d("TAG","AVlue f dydtem in android after jai shree"+image);
            } catch (FileNotFoundException e) {
                e.getStackTrace();
            }


            // oethod 1
  /*     try {
          Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
            Log.d("TAG", "VAlue for Btmap" + bitmap);
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] bytesArray=byteArrayOutputStream.toByteArray();
            String encoded=Base64.encodeToString(bytesArray,Base64.DEFAULT);
            Log.d("TAG","Value is creted by encoded"+encoded);


          } catch (IOException e) {
            e.printStackTrace();
          }
          Bitmap bm = BitmapFactory.decodeFile("storage/emulated/0/DCIM/Camera/IMG_20200115_232429.jpg");
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
          byte[] b = baos.toByteArray();

          String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
          Log.d("TAG","value of bitmap incount function"+encodedImage);

        }

        }*/
        }
        }
  }
    public static String ConvertBitmapToString(Bitmap bitmap){
        String encodedImage = "";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        try {
            encodedImage= URLEncoder.encode(Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedImage;
    }

}



