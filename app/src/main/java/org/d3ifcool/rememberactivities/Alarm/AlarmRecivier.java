package org.d3ifcool.rememberactivities.Alarm;

import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import org.d3ifcool.rememberactivities.Database.DBHelper;
import org.d3ifcool.rememberactivities.Database.RememberActivitiesContract;
import org.d3ifcool.rememberactivities.PopupActivity;
import org.d3ifcool.rememberactivities.R;

/**
 * Created by Yoga Wahyu Yuwono on 01/10/2018.
 */

public class AlarmRecivier extends BroadcastReceiver implements LoaderManager.LoaderCallbacks<Cursor> {
    MediaPlayer player;
   DBHelper newMydbHelper;
    NotificationCompat.Builder notifBuilder;
    private static final int EXISTING_LOADER=0;
    private Uri mCurentUri;
    Context context1;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        context1=context;
        newMydbHelper=new DBHelper(context);
        notifBuilder=new NotificationCompat.Builder(context);
        mCurentUri=intent.getData();
        intent.setData(mCurentUri);
        notifikasi.notifTemplate(context,"Remember Activities","Ayo cek kegiatan anda!");
        Toast.makeText(context, "Alarm aktif!", Toast.LENGTH_LONG).show();
        player = MediaPlayer.create(context, R.raw.apple_ring);
        player.start();
        Intent popup=new Intent("android.intent.action.MAIN");
        popup.setClass(context,PopupActivity.class);
        popup.setData(mCurentUri);
        popup.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(popup);
    }

    private void notifikasi(Context context){
        Calendar calendar=Calendar.getInstance();
        int jam=calendar.get(Calendar.HOUR);
        SQLiteDatabase db=newMydbHelper.getWritableDatabase();
        Cursor c;
        String sql="select * from "+ RememberActivitiesContract.myContractEntry.Table_Name+ " where "+RememberActivitiesContract.myContractEntry.jam_mulai+" ='"+jam+"'";
        c=db.rawQuery(sql,null);
        try{
            if (c.moveToFirst()){
                /*
                int name=c.getColumnIndex(myContract.myContractEntry.NAME);
                notifBuilder.setSmallIcon(R.mipmap.ic_launcher_foregrou);
                notifBuilder.setContentTitle("Rememeber Activities");
                notifBuilder.setContentText(c.getString(name));
                Intent intent=new Intent(,detailviewActivity.class);

            */
                int name=c.getColumnIndex(RememberActivitiesContract.myContractEntry.NAME);
                notifikasi.notifTemplate(context,"Remember Activities",c.getString(name));
            }
        }catch (Exception e){
            throw new IllegalArgumentException("Gagagl "+e);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String [] projection= {
                RememberActivitiesContract.myContractEntry.UID,
                RememberActivitiesContract.myContractEntry.NAME
        };
        return new CursorLoader(context1,mCurentUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor==null||cursor.getCount()<1){
            return;
        }
        if (cursor.moveToFirst()){RememberActivitiesContract.myContractEntry.UID);
            int nameColumnIndex = cursor.getColumnIndex(RememberActivitiesContract.myContractEntry.NAME);
            String id=cursor.getString(idClumndIndex);
            String nmkgt = cursor.getString(nameColumnIndex);
            notifikasi.notifTemplate(context1,"Remember Activities",nmkgt);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

