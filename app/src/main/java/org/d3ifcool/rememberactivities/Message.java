package org.d3ifcool.rememberactivities;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Asus on 9/24/2018.
 */

public class Message {
    public static void message (Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
