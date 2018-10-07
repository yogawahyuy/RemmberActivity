package org.d3ifcool.rememberactivities.Alarm;

import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import org.d3ifcool.rememberactivities.R;

/**
 * Created by Yoga Wahyu Yuwono on 01/10/2018.
 */

public class AlarmRecivier extends BroadcastReceiver implements LoaderManager.LoaderCallbacks<Cursor> {

    @Override
    public void onReceive(Context context, Intent intent) {


        //untuk notifikasi
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.alarm);
        builder.setContentTitle(context.getString(R.string.app_name));


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
