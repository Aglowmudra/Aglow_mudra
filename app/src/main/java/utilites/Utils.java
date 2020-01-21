package utilites;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.aglowmudra.Login;

import java.io.ByteArrayOutputStream;

public   class Utils {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static String myPref = "MyPrefrences";


    public static SharedPreferences getSharedPref(Activity activity) {
        SharedPreferences pref_parent_id = PreferenceManager.getDefaultSharedPreferences(activity);
        return pref_parent_id;
    }
    public static SharedPreferences getSharedPref(Context context) {
        SharedPreferences pref_parent_id = PreferenceManager.getDefaultSharedPreferences(context);
        return pref_parent_id;
    }
    public static SharedPreferences.Editor getSharedPrefEditor(SharedPreferences pref_parent_id) {
        SharedPreferences.Editor editor_parent_id = pref_parent_id.edit();
        return editor_parent_id;
    }


}



