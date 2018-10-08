package org.d3ifcool.rememberactivities;

import android.app.AlarmManager;
import android.app.LoaderManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.d3ifcool.rememberactivities.Database.DBHelper;
import org.d3ifcool.rememberactivities.Database.RememberActivitiesContract;
import org.d3ifcool.rememberactivities.Alarm.AlarmRecivier;

import static org.d3ifcool.rememberactivities.TambahKegiatanActivity.RQS_1;

public class PopupActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks <Cursor> {

    private Uri mCurrentUri;
    DBHelper DBHelper;
    private TextView namaKegiatan, jamKegiatan;
    private Button jedaKegiatan,tutupKegiatan;
    private static final int EXISTING_LOADER=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        Intent intent=getIntent();
        mCurrentUri=intent.getData();
        DBHelper= new DBHelper(this);
        namaKegiatan=(TextView)findViewById(R.id.popUp_namaKegiatan);
        jamKegiatan=(TextView)findViewById(R.id.popUp_jamMulaikegiatan);
        jedaKegiatan=(Button)findViewById(R.id.popUp_jeda);
        tutupKegiatan=(Button)findViewById(R.id.popUp_tutup);
        if (mCurrentUri==null){
            setTitle("PopUp Alarm");
        }else {
            getLoaderManager().initLoader(EXISTING_LOADER,null,this);
        }
        jedaKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tundaWaktu();
                finish();
            }
        });
        tutupKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String [] projection={
               RememberActivitiesContract.myContractEntry.UID,
                RememberActivitiesContract.myContractEntry.NAME,
                RememberActivitiesContract.myContractEntry.jam_mulai,
        };
        return new CursorLoader(this,mCurrentUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor==null|| cursor.getCount()<1){
            return;
        }
        if (cursor.moveToFirst()){
            int nameColumnIndex=cursor.getColumnIndex(RememberActivitiesContract.myContractEntry.NAME);
            int jammulaiColumnIndex=cursor.getColumnIndex(RememberActivitiesContract.myContractEntry.jam_mulai);
            String nmkgtt=cursor.getString(nameColumnIndex);
            String jammulai=cursor.getString(jammulaiColumnIndex);

            namaKegiatan.setText("Nama Kegiatan :"+nmkgtt);
            jamKegiatan.setText("Jam mulai kegiatan anda :"+jammulai);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    private void tundaWaktu(){
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();
        //calSet.set(pickerdate.getYear(),pickerdate.getMonth(),pickerdate.getDayOfMonth());
        //calSet.set(Calendar.YEAR);
        final int hour=calNow.get(Calendar.HOUR_OF_DAY);
        final int minute=calNow.get(Calendar.MINUTE);
        calSet.set(Calendar.HOUR_OF_DAY, hour);
        calSet.set(Calendar.MINUTE, (minute+10));
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);
        Message.message(getApplicationContext(),"Kegiatan ditunda selama 10 menit");
        if (calSet.compareTo(calNow) <= 0) {
            // Today Set time passed, count to tomorrow
            Message.message(getApplicationContext(),"Tanggal Tidak valid");
        } else {

        }
        setAlarm(calSet);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setAlarm(Calendar target){
//        Cursor cursor;
//        SQLiteDatabase db= DBHelper.getReadableDatabase();
//        try{
//            String query="Select _id from "+RememberActivitiesContract.myContractEntry.Table_Name;
//            cursor=db.rawQuery(query,null);
//            if (cursor.getCount()==0) {
//                int id=1;
//                Uri CurrentUri= ContentUris.withAppendedId(RememberActivitiesContract.myContractEntry.CONTENT_URI,id);
//                Intent intent = new Intent(getBaseContext(), Alarmrecivier.class);
//                intent.setData(CurrentUri);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
//                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                alarmManager.set(AlarmManager.RTC_WAKEUP, target.getTimeInMillis(), pendingIntent);
//            }else if(cursor.moveToLast()){
//                int id=cursor.getCount()+2;
//                Uri CurrentUri= ContentUris.withAppendedId(RememberActivitiesContract.myContractEntry.CONTENT_URI,id);
//                Intent intent = new Intent(getBaseContext(), Alarmrecivier.class);
//                intent.setData(CurrentUri);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), cursor.getCount()+200, intent, 0);
//                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                alarmManager.set(AlarmManager.RTC_WAKEUP, target.getTimeInMillis(), pendingIntent);
//            }
//        }catch (Exception e){
//
//        }

    }
}

