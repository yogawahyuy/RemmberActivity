package org.d3ifcool.rememberactivities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import org.d3ifcool.rememberactivities.Alarm.AlarmRecivier;
import org.d3ifcool.rememberactivities.Database.DBHelper;
import org.d3ifcool.rememberactivities.Database.RememberActivitiesContract;

import java.util.Calendar;


public class TambahKegiatanActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int EXISTING_LOADER = 0;
    private Uri mCurrentUri;
    private boolean hasChanged = false;
    private View.OnTouchListener mtouchlistener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            hasChanged = true;
            return false;
        }
    };

    EditText namaKgt, tglKgt, jamMulai, jamBrakhir, tempat, catatan;
    DatePickerDialog datePickerDialog, datePickerDialogdone;
    final static int RQS_1 = 1;
    String namakegiatan, tanggalkegiatan, jamMulaiKgt, jamBerakhirKgt, tempatKgt, catatanKgt;
    int tahun, bulan, hari;
    int tahunselesai, bulanselesai, hariselesai;
    DBHelper newMydbHelper;
    int mYear, mMonth, mDay;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kegiatan);
        //dibawah ini adalah inisialisasi semua komponen
        Intent intent = getIntent();
        //disini untuk database
        mCurrentUri = intent.getData();
        if (mCurrentUri == null) {
            setTitle(R.string.tambah_kegiatan);
            invalidateOptionsMenu();
        } else {
            setTitle(R.string.edit_kegiatan);
            getLoaderManager().initLoader(EXISTING_LOADER, null, this);
        }
        newMydbHelper = new DBHelper(this);
        //inisialisasi tampilan
        namaKgt = findViewById(R.id.namaKegiatan);
        tglKgt = findViewById(R.id.tanggalKegiatan);
        jamMulai = findViewById(R.id.jamMulai);
        jamBrakhir = findViewById(R.id.jamBerakhir);
        tempat = findViewById(R.id.tempat);
        catatan = findViewById(R.id.catatan);

        //untuk on click listener
        tglKgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tanggalKegiatan();
            }
        });
        jamMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setjamMulai(false);
            }
        });
        jamBrakhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setjamBerakhir();
            }
        });

    }



    //dibawah ini adalah algoritma tambah kegiatan nama method "Add Data"

    private void addData() {
        namakegiatan = namaKgt.getText().toString();
        tanggalkegiatan = tglKgt.getText().toString();
        jamMulaiKgt = jamMulai.getText().toString();
        jamBerakhirKgt = jamBrakhir.getText().toString();
        tempatKgt = tempat.getText().toString();
        catatanKgt = catatan.getText().toString();
        if (mCurrentUri == null && TextUtils.isEmpty(namakegiatan) && TextUtils.isEmpty(tanggalkegiatan) && TextUtils.isEmpty(jamMulaiKgt) && TextUtils.isEmpty(jamBerakhirKgt)) {
            return;
        }
        if (namakegiatan.isEmpty()||tanggalkegiatan.isEmpty()||jamMulaiKgt.isEmpty()||jamBerakhirKgt.isEmpty()){
            Message.message(getApplicationContext(),"Tolong isi semua field");
        }else {
                if (TextUtils.isEmpty(catatanKgt)){
                    catatanKgt="Anda Tidak Memasukan Catatan";
                    status="tercapai";
                }else if (!TextUtils.isEmpty(catatanKgt)){
                    status="Belum Tercapai";
                }

                ContentValues values=new ContentValues();
                values.put(RememberActivitiesContract.myContractEntry.NAME,namakegiatan);
                values.put(RememberActivitiesContract.myContractEntry.tgl_mulai,tanggalkegiatan);
                values.put(RememberActivitiesContract.myContractEntry.jam_mulai,jamMulaiKgt);
                values.put(RememberActivitiesContract.myContractEntry.jam_berakhir,jamBerakhirKgt);
                values.put(RememberActivitiesContract.myContractEntry.tempat,tempatKgt);
                values.put(RememberActivitiesContract.myContractEntry.catatan,catatanKgt);
                if (mCurrentUri==null){
                    Uri newUri=getContentResolver().insert(RememberActivitiesContract.myContractEntry.CONTENT_URI,values);
                    if (newUri==null){
                        Message.message(this,"Gagal Menyimpan Kegiatan");
                    }else {
                        Message.message(this,"Berhasil Menyimpan Kegiatan");
                        //startActivity(new Intent(TambahKegiatanActivity.this,LihatKegiatanActivity.class));
                    }
                }else{
                    int rowsAffected=getContentResolver().update(mCurrentUri,values,null,null);
                    if (rowsAffected==0){
                        Message.message(this,"Gagal Memperbaharui Kegiatan");
                    }else {
                        Message.message(this,"Berhasil Memperbaharui Kegiatan");
                        startActivity(new Intent(TambahKegiatanActivity.this,LihatKegiatanActivity.class));
                    }
                }
        }
    }
//    */

        //dibawah ini adalah algoritma untuk menampilkan notifikasi

      public void notifTemplate(String tittle, String message) {
        final Intent intent = new Intent(TambahKegiatanActivity.this,LihatKegiatanActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(TambahKegiatanActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder)new NotificationCompat.Builder(TambahKegiatanActivity.this).
                setSmallIcon(R.mipmap.ic_launcher).setContentTitle(tittle).setContentText(message).setContentIntent(pendingIntent)
                .setAutoCancel(true).setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.FLAG_AUTO_CANCEL);
        NotificationManager notificationManager =
                (NotificationManager)TambahKegiatanActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,mBuilder.build());
    }

        //dibawah ini adalah algoritma untuk menu ditoolbar
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_tambah_kegiatan, menu);
            return true;
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.save:
                addData();
                notifTemplate("Remember Activities","Kegiatan Berhasil Ditambah");
                return true;

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (hasChanged){
            super.onBackPressed();
            return;
        }
        DialogInterface.OnClickListener discardButtonClickListene=new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        };
        showUnsaveDialog(discardButtonClickListene);

    }

        //dibawah adalah algoritma untuk mencegah user keluar dari pengisian form jika form belum selesai terisi

      private void showUnsaveDialog(DialogInterface.OnClickListener discard){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Buang perubahan Anda dan keluar dari pengeditan?");
        builder.setPositiveButton("Buang",discard);
        builder.setNegativeButton("Tetap Mengedit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface!=null){
                    dialogInterface.dismiss();
                }
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }

        //dibawah ini algoritma untuk menampilkan tanggal dan jam

      private void tanggalKegiatan(){

        tglKgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c= Calendar.getInstance();
                 mYear=c.get(Calendar.YEAR);
                 mMonth=c.get(Calendar.MONTH);
                 mDay=c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog=new DatePickerDialog(TambahKegiatanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        tglKgt.setText(year+"-"+(month+1)+"-"+day);
                        tahun=year;
                        bulan=month;
                        hari=day;
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });

    }
    private void setjamMulai(boolean is24){

        jamMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mCurentTime=Calendar.getInstance();
                final int hour=mCurentTime.get(Calendar.HOUR_OF_DAY);
                final int minute=mCurentTime.get(Calendar.MINUTE);
                TimePickerDialog mtimepicker;
               mtimepicker=new TimePickerDialog(TambahKegiatanActivity.this,onTimeSetListener,hour,minute,true);
                mtimepicker.setTitle("Pilih waktu Mulai");
                mtimepicker.show();
            }
        });

        }

    TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            jamMulai.setText(i+":"+i1);
            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();
            calSet.set(tahun,bulan,hari);
            calSet.set(Calendar.HOUR_OF_DAY, i);
            calSet.set(Calendar.MINUTE, i1);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);
            if (calSet.compareTo(calNow) <= 0) {
                // Today Set time passed, count to tomorrow
              Message.message(getApplicationContext(),"Tanggal atau jam Tidak valid");
            } else {

            }
          //  setAlarm(calSet);

        }
    };
     private void setjamBerakhir(){

        jamBrakhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mCurentTime=Calendar.getInstance();
                int hour=mCurentTime.get(Calendar.HOUR_OF_DAY);
                int minute=mCurentTime.get(Calendar.MINUTE);
                TimePickerDialog mtimepickerdone;
                mtimepickerdone=new TimePickerDialog(TambahKegiatanActivity.this,onTimeSetListenerone,hour,minute,true);
                mtimepickerdone.setTitle("Select Time");
                mtimepickerdone.show();
            }
        });
    }
    TimePickerDialog.OnTimeSetListener onTimeSetListenerone=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            jamBrakhir.setText(i+":"+i1);
            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();
            calSet.set(tahunselesai,bulanselesai,hariselesai);
            calSet.set(Calendar.HOUR_OF_DAY, i);
            calSet.set(Calendar.MINUTE, i1);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);
            if (calSet.compareTo(calNow) <= 0) {
                // Today Set time passed, count to tomorrow
                Message.message(getApplicationContext(),"Tanggal Tidak valid");
            } else {

            }
           // setAlarmselesai(calSet);

        }
    };



        //disini terdapat algoritma untuk menambahkan alaram
      private void setAlarm(Calendar target){
        Cursor cursor;
        SQLiteDatabase db=newMydbHelper.getReadableDatabase();
        try{
            String query="Select _id from "+RememberActivitiesContract.myContractEntry.Table_Name;
            cursor=db.rawQuery(query,null);
            if (cursor.getCount()==0) {
                int id=1;
                Uri CurrentUri= ContentUris.withAppendedId(RememberActivitiesContract.myContractEntry.CONTENT_URI,id);
                Intent intent = new Intent(getBaseContext(), AlarmRecivier.class);
                intent.setData(CurrentUri);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, target.getTimeInMillis(), pendingIntent);
            }else if(cursor.moveToLast()){
                int id=cursor.getCount()+1;
                Uri CurrentUri= ContentUris.withAppendedId(RememberActivitiesContract.myContractEntry.CONTENT_URI,id);
                Intent intent = new Intent(getBaseContext(), AlarmRecivier.class);
                intent.setData(CurrentUri);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), cursor.getCount()+1, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, target.getTimeInMillis(), pendingIntent);
            }
        }catch (Exception e){

        }

    }

    private void setAlarmselesai(Calendar target){
        Cursor cursor;
        SQLiteDatabase db=newMydbHelper.getReadableDatabase();
        try{
            String query="Select _id from "+RememberActivitiesContract.myContractEntry.Table_Name;
            cursor=db.rawQuery(query,null);
            if (cursor.getCount()==0) {
                int id=1;
                Uri CurrentUri= ContentUris.withAppendedId(RememberActivitiesContract.myContractEntry.CONTENT_URI,id);
                Intent intent = new Intent(getBaseContext(), AlarmRecivier.class);
                intent.setData(CurrentUri);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, target.getTimeInMillis(), pendingIntent);
            }else if(cursor.moveToLast()){
                int id=cursor.getCount()+1;
                Uri CurrentUri= ContentUris.withAppendedId(RememberActivitiesContract.myContractEntry.CONTENT_URI,id);
                Intent intent = new Intent(getBaseContext(), AlarmRecivier.class);
                intent.setData(CurrentUri);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), cursor.getCount()+100, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, target.getTimeInMillis(), pendingIntent);
            }
        }catch (Exception e){

        }

    }



        //disini terdapat algoritma untuk menambah dan mengambil data (untuk edit) kegiatan

       @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String [] projection={
                RememberActivitiesContract.myContractEntry.UID,
                RememberActivitiesContract.myContractEntry.NAME,
                RememberActivitiesContract.myContractEntry.tgl_mulai,
                RememberActivitiesContract.myContractEntry.jam_mulai,
                RememberActivitiesContract.myContractEntry.jam_berakhir,
                RememberActivitiesContract.myContractEntry.catatan,
        };

        return new CursorLoader(this,mCurrentUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor==null||cursor.getCount()<1){
            return;
        }
        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(RememberActivitiesContract.myContractEntry.NAME);
            int tglmulaiColumnIndex = cursor.getColumnIndex(RememberActivitiesContract.myContractEntry.tgl_mulai);
            int jammulaiColumnIndex = cursor.getColumnIndex(RememberActivitiesContract.myContractEntry.jam_mulai);
            int jamberakhirColumnIndex = cursor.getColumnIndex(RememberActivitiesContract.myContractEntry.jam_berakhir);
            int catatanColumnIndex = cursor.getColumnIndex(RememberActivitiesContract.myContractEntry.catatan);
            String nmkgt = cursor.getString(nameColumnIndex);
            String tglmulai = cursor.getString(tglmulaiColumnIndex);
            String jammulai = cursor.getString(jammulaiColumnIndex);
            String jamberakhir = cursor.getString(jamberakhirColumnIndex);

            String catatannya = cursor.getString(catatanColumnIndex);

            namaKgt.setText(nmkgt);
            tglKgt.setText(tglmulai);
            jamMulai.setText(jammulai);
            jamBrakhir.setText(jamberakhir);
            catatan.setText(catatannya);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        namaKgt.setText("");
        tglKgt.setText("");
        jamMulai.setText("");
        jamBrakhir.setText("");
        catatan.setText("");
    }


    }

