package org.d3ifcool.rememberactivities.Alarm;

import android.app.LoaderManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import org.d3ifcool.rememberactivities.Database.DBHelper;
import org.d3ifcool.rememberactivities.Database.RememberActivitiesContract;
import org.d3ifcool.rememberactivities.LihatKegiatanActivity;
import org.d3ifcool.rememberactivities.Model.Kegiatan;
import org.d3ifcool.rememberactivities.PopupActivity;
import org.d3ifcool.rememberactivities.R;

import java.util.ArrayList;

/**
 * Created by Yoga Wahyu Yuwono on 01/10/2018.
 */

public class AlarmRecivier extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        int id=intent.getIntExtra("id",0);
        String kegiatan=intent.getStringExtra("kegiatan");
        String jam=intent.getStringExtra("jam");
        double lat=intent.getDoubleExtra("lat",0);
        double lang=intent.getDoubleExtra("lang",0);
        final NotificationManager manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        final Intent notifinten=new Intent(context,LihatKegiatanActivity.class);
        notifinten.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        final PendingIntent pendingIntent= PendingIntent.getActivity(context,id,notifinten,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.logoooo);
        builder.setColor(ContextCompat.getColor(context,R.color.colorAccent));
        builder.setContentTitle(context.getString(R.string.app_name));
        builder.setContentText(kegiatan);
        builder.setVibrate(new long[]{1000,500,1000,500,1000,500});
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setContentIntent(pendingIntent);
        builder.setPriority(Notification.PRIORITY_HIGH);
        manager.notify(id,builder.build());

        final Intent popUp=new Intent(context,PopupActivity.class);
        popUp.putExtra("posisi",id);
        popUp.putExtra("namaKgt",kegiatan);
        popUp.putExtra("jamkgt",jam);
        popUp.putExtra("lat",lat);
        popUp.putExtra("lang",lang);
        context.startActivity(popUp);
    }
}

