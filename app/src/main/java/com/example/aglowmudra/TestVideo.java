package com.example.aglowmudra;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
public class TestVideo extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        getvideos();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public void getvideos(){
        Log.d("TAG","VAlue is created by getvodes");
      List<Video> videoList = new ArrayList<Video>();

      String[] projection = new String[] {
              MediaStore.Video.Media._ID,
              MediaStore.Video.Media.DISPLAY_NAME,
              MediaStore.Video.Media.DURATION,
              MediaStore.Video.Media.SIZE
      };
      String selection = MediaStore.Video.Media.DURATION +
              " >= ?";
      String[] selectionArgs = new String[] {
              String.valueOf(TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES))};

      String sortOrder = MediaStore.Video.Media.DISPLAY_NAME + " ASC";

      try (Cursor cursor = getApplicationContext().getContentResolver().query(
              MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
              projection,
              selection,
              selectionArgs,
              sortOrder
      )) {
          // Cache column indices.
          int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
          Log.d("TAG","VAlue of idColumn"+idColumn);
          int nameColumn =
                  cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
          Log.d("TAG","VAlue of nameColumn"+nameColumn);

          int durationColumn =
                  cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
          Log.d("TAG","VAlue of durationColumn"+durationColumn);
          int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
          Log.d("TAG","VAlue of sizeColumn"+sizeColumn);

          while (cursor.moveToNext()) {
              Log.d("TAG","VAlue is created by curse");
              // Get values of columns for a given video.
              long id = cursor.getLong(idColumn);
              Log.d("TAG","Value of id"+id);
              String name = cursor.getString(nameColumn);
              Log.d("TAG","Value of nameColumn"+nameColumn);
              int duration = cursor.getInt(durationColumn);
              Log.d("TAG","Value of duration"+duration);
              int size = cursor.getInt(sizeColumn);
              Log.d("TAG","Value of size"+size);

              Uri contentUri = ContentUris.withAppendedId(
                      MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);

              // Stores column values and the contentUri in a local object
              // that represents the media file.
              videoList.add(new Video(contentUri, name, duration, size));
          }
          for (int i=0;i<videoList.size();i++){
              Log.d("TAG","Value of videos"+videoList.get(i));

          }
      }


  }


}

