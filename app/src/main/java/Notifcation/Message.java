package Notifcation;

import android.content.Context;
import android.widget.Toast;

public class Message {
    public static void Messageshow( Context context,String Message){
        Toast.makeText(context, ""+Message, Toast.LENGTH_SHORT).show();


    }
}
