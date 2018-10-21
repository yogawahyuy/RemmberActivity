package org.d3ifcool.rememberactivities.Alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import org.d3ifcool.rememberactivities.LihatKegiatanActivity;
import org.d3ifcool.rememberactivities.PopupActivity;
import org.d3ifcool.rememberactivities.R;

public class SecondAlarmRecivier extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int id=intent.getIntExtra("id",0);
        String kegiatan=intent.getStringExtra("kegiatan");
        String jam=intent.getStringExtra("jam");
        double lat=intent.getDoubleExtra("lat",0);
        double lang=intent.getDoubleExtra("lang",0);
        String catatan=intent.getStringExtra("catatan");
        String jamselesai=intent.getStringExtra("jamselesai");
        final NotificationManager manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        final Intent notifinten=new Intent(context,LihatKegiatanActivity.class);
        notifinten.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        final PendingIntent pendingIntent= PendingIntent.getActivity(context,id,notifinten,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.logoooo);
        builder.setColor(ContextCompat.getColor(context,R.color.colorAccent));
        builder.setContentTitle(context.getString(R.string.app_name));
        builder.setContentText("Second alarm recivier");
        builder.setContentIntent(pendingIntent);
        builder.setPriority(Notification.PRIORITY_HIGH);
        manager.notify(id,builder.build());

        final Intent popUp=new Intent(context,PopupActivity.class);
        popUp.putExtra("posisi",id);
        popUp.putExtra("namaKgt",kegiatan);
        popUp.putExtra("jamkgt",jam);
        popUp.putExtra("lat",lat);
        popUp.putExtra("lang",lang);
        popUp.putExtra("catatan",catatan);
        popUp.putExtra("jamselesai",jamselesai);
        context.startActivity(popUp);
    }
}
