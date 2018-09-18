package org.d3ifcool.rememberactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TambahKegiatanActivity extends AppCompatActivity {
    /*  private static final int EXISTING_LOADER=0;
    private Uri mCurrentUri;
    private boolean hasChanged=false;
    private View.OnTouchListener mtouchlistener=new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            hasChanged=true;
            return false;
        }
    };
    EditText namakegiatan,date,time,datedone,timedone,catatan;
    DatePickerDialog datePickerDialog,datePickerDialogdone;
    final static int RQS_1 = 1;
    String namaAktivitas;
    int tahun,bulan,hari;
    int tahunselesai,bulanselesai,hariselesai;
    myDBHelper helper;
    newMydbHelper newMydbHelper;
    int mYear,mMonth,mDay;
    String status;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kegiatan);
        //dibawah ini adalah inisialisasi semua komponen
        /*  Intent intent=getIntent();
        //disini untuk database
        mCurrentUri=intent.getData();
        if (mCurrentUri==null){
            setTitle(R.string.tambah_kegiatan);
            invalidateOptionsMenu();
        }else {
            setTitle(R.string.edit_kegiatan);
            getLoaderManager().initLoader(EXISTING_LOADER,null,this);
        }
        newMydbHelper=new newMydbHelper(this);
        //inisialisasi tampilan
        namakegiatan = findViewById(R.id.activitiename);
        date =(EditText)findViewById(R.id.date);
        datedone =(EditText)findViewById(R.id.datedone);
        timedone=(EditText)findViewById(R.id.timedone);
        catatan = findViewById(R.id.note);
        time=(EditText)findViewById(R.id.timee);
        datebeginshow();
        timebeginshow(false);
        datedoneshow();
        timedoneshow();
        helper=new myDBHelper(this);
        namakegiatan.setOnTouchListener(mtouchlistener);
        date.setOnTouchListener(mtouchlistener);
        time.setOnTouchListener(mtouchlistener);
        datedone.setOnTouchListener(mtouchlistener);
        timedone.setOnTouchListener(mtouchlistener);
        catatan.setOnTouchListener(mtouchlistener);
        */
    }

    //dibawah ini adalah algoritma tambah kegiatan nama method "Add Data"
    /*
    private void addData(){
        namaAktivitas = namakegiatan.getText().toString();
        String note=catatan.getText().toString();
        String tglmulai=date.getText().toString();
        String jammulai=time.getText().toString();
        String tglberakhir=datedone.getText().toString();
        String jamberakhir=timedone.getText().toString();
        if (mCurrentUri==null&& TextUtils.isEmpty(namaAktivitas)&&TextUtils.isEmpty(tglmulai)&&TextUtils.isEmpty(jammulai)&&TextUtils.isEmpty(tglberakhir)&&TextUtils.isEmpty(jamberakhir)){
            return;
        }
        if (namaAktivitas.isEmpty()||tglmulai.isEmpty()||jammulai.isEmpty()||tglberakhir.isEmpty()||jamberakhir.isEmpty()){
            Message.message(getApplicationContext(),"Tolong isi semua field");
        }else {
                if (TextUtils.isEmpty(note)){
                    note="Anda Tidak Memasukan Catatan";
                    status="tercapai";
                }else if (!TextUtils.isEmpty(note)){
                    status="Belum Tercapai";
                }

                ContentValues values=new ContentValues();
                values.put(myContract.myContractEntry.NAME,namaAktivitas);
                values.put(myContract.myContractEntry.tgl_mulai,tglmulai);
                values.put(myContract.myContractEntry.jam_mulai,jammulai);
                values.put(myContract.myContractEntry.tgl_berakhir,tglberakhir);
                values.put(myContract.myContractEntry.jam_berakhir,jamberakhir);
                values.put(myContract.myContractEntry.catatan,note);
                if (mCurrentUri==null){
                    Uri newUri=getContentResolver().insert(myContract.myContractEntry.CONTENT_URI,values);
                    if (newUri==null){
                        Message.message(this,"Gagal Menyimpan Kegiatan");
                    }else {
                        Message.message(this,"Berhasil Menyimpan Kegiatan");
                        startActivity(new Intent(detailingActivity.this,seeActivity.class));
                    }
                }else{
                    int rowsAffected=getContentResolver().update(mCurrentUri,values,null,null);
                    if (rowsAffected==0){
                        Message.message(this,"Gagal Memperbaharui Kegiatan");
                    }else {
                        Message.message(this,"Berhasil Memperbaharui Kegiatan");
                        startActivity(new Intent(detailingActivity.this,seeActivity.class));
                    }
                }
        }
    }
    */

    //dibawah ini adalah algoritma untuk menampilkan notifikasi
    /*
    *  public void notifTemplate(String tittle, String message) {
        final Intent intent = new Intent(detailingActivity.this,seeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(detailingActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder)new NotificationCompat.Builder(detailingActivity.this).
                setSmallIcon(R.mipmap.ic_launcher_foregrou).setContentTitle(tittle).setContentText(message).setContentIntent(pendingIntent)
                .setAutoCancel(true).setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.FLAG_AUTO_CANCEL);
        NotificationManager notificationManager =
                (NotificationManager)detailingActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,mBuilder.build());
    }*/

    //dibawah ini adalah algoritma untuk menu ditoolbar
    /*
    *   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detailing,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.save_data:
                addData();
                notifTemplate("Remember Activities","Kegiatan Berhasil Ditambah");
            case android.R.id.home:
                if (hasChanged){
                    NavUtils.navigateUpFromSameTask(detailingActivity.this);
                    return true;
                }
                DialogInterface.OnClickListener discardButtonClickListene=new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        NavUtils.navigateUpFromSameTask(detailingActivity.this);
                    }
                };
                showUnsaveDialog(discardButtonClickListene);
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

    }*/

    //dibawah adalah algoritma untuk mencegah user keluar dari pengisian form jika form belum selesai terisi

    /*  private void showUnsaveDialog(DialogInterface.OnClickListener discard){
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

    } */

    //dibawah ini algoritma untuk menampilkan tanggal dan jam

    /*  private void datebeginshow(){

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c= Calendar.getInstance();
                 mYear=c.get(Calendar.YEAR);
                 mMonth=c.get(Calendar.MONTH);
                 mDay=c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog=new DatePickerDialog(detailingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        date.setText(year+"-"+(month+1)+"-"+day);
                        tahun=year;
                        bulan=month;
                        hari=day;
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });

    }
    private void datedoneshow(){

        datedone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c= Calendar.getInstance();
                int mYear=c.get(Calendar.YEAR);
                int mMont=c.get(Calendar.MONTH);
                int mDay=c.get(Calendar.DAY_OF_MONTH);
                datePickerDialogdone=new DatePickerDialog(detailingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        datedone.setText(year+"-"+(month+1)+"-"+day);
                        tahunselesai=year;
                        bulanselesai=month;
                        hariselesai=day;
                    }
                },mYear,mMont,mDay);
                datePickerDialogdone.show();
            }
        });
    }
    private void timebeginshow(boolean is24){

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mCurentTime=Calendar.getInstance();
                final int hour=mCurentTime.get(Calendar.HOUR_OF_DAY);
                final int minute=mCurentTime.get(Calendar.MINUTE);
                TimePickerDialog mtimepicker;
               mtimepicker=new TimePickerDialog(detailingActivity.this,onTimeSetListener,hour,minute,true);
                mtimepicker.setTitle("Pilih waktu Mulai");
                mtimepicker.show();
            }
        });

        }

    TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            time.setText(i+":"+i1);
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
            setAlarm(calSet);

        }
    };
     private void timedoneshow(){

        timedone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mCurentTime=Calendar.getInstance();
                int hour=mCurentTime.get(Calendar.HOUR_OF_DAY);
                int minute=mCurentTime.get(Calendar.MINUTE);
                TimePickerDialog mtimepickerdone;
                mtimepickerdone=new TimePickerDialog(detailingActivity.this,onTimeSetListenerone,hour,minute,true);
                mtimepickerdone.setTitle("Select Time");
                mtimepickerdone.show();
            }
        });
    }
    TimePickerDialog.OnTimeSetListener onTimeSetListenerone=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            timedone.setText(i+":"+i1);
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
            setAlarmselesai(calSet);

        }
    };

    */

    //disini terdapat algoritma untuk menambahkan alaram
    /*  private void setAlarm(Calendar target){
        Cursor cursor;
        SQLiteDatabase db=newMydbHelper.getReadableDatabase();
        try{
            String query="Select _id from "+myContract.myContractEntry.Table_Name;
            cursor=db.rawQuery(query,null);
            if (cursor.getCount()==0) {
                int id=1;
                Uri CurrentUri= ContentUris.withAppendedId(myContract.myContractEntry.CONTENT_URI,id);
                Intent intent = new Intent(getBaseContext(), Alarmrecivier.class);
                intent.setData(CurrentUri);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, target.getTimeInMillis(), pendingIntent);
            }else if(cursor.moveToLast()){
                int id=cursor.getCount()+1;
                Uri CurrentUri= ContentUris.withAppendedId(myContract.myContractEntry.CONTENT_URI,id);
                Intent intent = new Intent(getBaseContext(), Alarmrecivier.class);
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
            String query="Select _id from "+myContract.myContractEntry.Table_Name;
            cursor=db.rawQuery(query,null);
            if (cursor.getCount()==0) {
                int id=1;
                Uri CurrentUri= ContentUris.withAppendedId(myContract.myContractEntry.CONTENT_URI,id);
                Intent intent = new Intent(getBaseContext(), AlarmReciviersjamselesai.class);
                intent.setData(CurrentUri);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, target.getTimeInMillis(), pendingIntent);
            }else if(cursor.moveToLast()){
                int id=cursor.getCount()+1;
                Uri CurrentUri= ContentUris.withAppendedId(myContract.myContractEntry.CONTENT_URI,id);
                Intent intent = new Intent(getBaseContext(), AlarmReciviersjamselesai.class);
                intent.setData(CurrentUri);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), cursor.getCount()+100, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, target.getTimeInMillis(), pendingIntent);
            }
        }catch (Exception e){

        }

    }

    */

    //disini terdapat algoritma untuk menambah dan mengambil data (untuk edit) kegiatan
    /*
       @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String [] projection={
                myContract.myContractEntry.UID,
                myContract.myContractEntry.NAME,
                myContract.myContractEntry.tgl_mulai,
                myContract.myContractEntry.jam_mulai,
                myContract.myContractEntry.tgl_berakhir,
                myContract.myContractEntry.jam_berakhir,
                myContract.myContractEntry.catatan,
        };

        return new CursorLoader(this,mCurrentUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor==null||cursor.getCount()<1){
            return;
        }
        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(myContract.myContractEntry.NAME);
            int tglmulaiColumnIndex = cursor.getColumnIndex(myContract.myContractEntry.tgl_mulai);
            int jammulaiColumnIndex = cursor.getColumnIndex(myContract.myContractEntry.jam_mulai);
            int tglberakhirColumnIndex = cursor.getColumnIndex(myContract.myContractEntry.tgl_berakhir);
            int jamberakhirColumnIndex = cursor.getColumnIndex(myContract.myContractEntry.jam_berakhir);
            int catatanColumnIndex = cursor.getColumnIndex(myContract.myContractEntry.catatan);
            String nmkgt = cursor.getString(nameColumnIndex);
            String tglmulai = cursor.getString(tglmulaiColumnIndex);
            String jammulai = cursor.getString(jammulaiColumnIndex);
            String tglberakhir = cursor.getString(tglberakhirColumnIndex);
            String jamberakhir = cursor.getString(jamberakhirColumnIndex);

            String catatannya = cursor.getString(catatanColumnIndex);

            namakegiatan.setText(nmkgt);
            date.setText(tglmulai);
            time.setText(jammulai);
            datedone.setText(tglberakhir);
            timedone.setText(jamberakhir);
            catatan.setText(catatannya);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        namakegiatan.setText("");
        date.setText("");
        datedone.setText("");
        timedone.setText("");
        time.setText("");
        catatan.setText("");
    }


    */

}
