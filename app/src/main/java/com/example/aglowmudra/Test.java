package com.example.aglowmudra;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;

public class Test extends AppCompatActivity {


    public static ArrayList<Model_images> al_images = new ArrayList<>();
    boolean boolean_folder;
    ArrayList<String> allImage=new ArrayList<>();


    private static final int REQUEST_PERMISSIONS = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testtwo);

        try {
            fn_imagespath();
        } catch (IOException e) {
            e.printStackTrace();
        }
count1();
    }
    public ArrayList<Model_images> fn_imagespath() throws IOException {
        Log.d("TAG","Function Start");
        al_images.clear();

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null;
        String Imagepath="null";
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Log.d("TAG","VAlue is created system in room"+uri);
        Log.d("TAG","start one");
        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        Log.d("TAG","start second");
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");
        Log.d("TAG","start Third");
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        Log.d("TAG","start fourth");
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            Log.d("TAG","while Loop start ");
            absolutePathOfImage = cursor.getString(column_index_data);
            Log.e("TAG","Value of Path"+absolutePathOfImage);
            for (int i=0;i<absolutePathOfImage.length();i++){
                Log.d("TAG","VAlue loop in loop");
                Imagepath=absolutePathOfImage;

                Log.d("TAG","VAluein  loop in loop"+Imagepath);
            }
            String VAlue=Imagepath;
            Log.d("TAG","VAlue is creted in the SSSS"+VAlue);
            Log.e("Folder", cursor.getString(column_index_folder_name));

            for (int i = 0; i < al_images.size(); i++) {
                Log.d("TAG","For loop started");
                if (al_images.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))) {
                    boolean_folder = true;
                    int_position = i;
                    break;
                } else {
                    boolean_folder = false;
                }
            }


            if (boolean_folder) {

                ArrayList<String> al_path = new ArrayList<>();
                al_path.addAll(al_images.get(int_position).getAl_imagepath());
                al_path.add(absolutePathOfImage);
                al_images.get(int_position).setAl_imagepath(al_path);

            } else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                Model_images obj_model = new Model_images();
                obj_model.setStr_folder(cursor.getString(column_index_folder_name));
                obj_model.setAl_imagepath(al_path);

                al_images.add(obj_model);
                Log.d("TAG","Image of path"+al_images.get(0).al_imagepath);



            }


        }


        for (int i = 0; i < al_images.size(); i++) {
            Log.e("FOLDER", al_images.get(i).getStr_folder());
            for (int j = 0; j < al_images.get(i).getAl_imagepath().size(); j++) {
                Log.e("FILE", al_images.get(i).getAl_imagepath().get(j));


            }
        }

        return al_images;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        try {
                            fn_imagespath();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(Test.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }

            }
        }
    }
public void count1(){


String[] value= al_images.get(0).al_imagepath.toArray(new String[0]);
   Log.d("TAG","VAlue is creted by system"+value);
    Bitmap bm = BitmapFactory.decodeFile(value[0]);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
    byte[] b = baos.toByteArray();
    Log.d("TAG","value of bitmap incount function"+b);
    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
    Log.d("TAG","value of bitmap incount function"+encodedImage);
    allImage.add(encodedImage);

    String help=allImage.get(0);



}
}
