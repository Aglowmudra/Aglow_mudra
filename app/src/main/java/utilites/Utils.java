package utilites;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public   class Utils {
    public static SharedPreferences sharedpreferences;
    public static SharedPreferences.Editor editor;

    public static SharedPreferences getSharedpreferences(Activity activity) {
        SharedPreferences pref_parent_id = PreferenceManager.getDefaultSharedPreferences(activity);
        return pref_parent_id;
    }

    public static SharedPreferences.Editor getSharedPrefEditor(SharedPreferences pref_parent_id) {
        SharedPreferences.Editor editor_parent_id = pref_parent_id.edit();
        return editor_parent_id;
    }
}



