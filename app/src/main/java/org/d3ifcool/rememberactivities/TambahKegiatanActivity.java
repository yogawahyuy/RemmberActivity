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
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import org.d3ifcool.rememberactivities.Adapter.AdapterLihatKegiatan;
import org.d3ifcool.rememberactivities.Alarm.AlarmRecivier;
import org.d3ifcool.rememberactivities.Alarm.SecondAlarmRecivier;
import org.d3ifcool.rememberactivities.Database.DBHelper;
import org.d3ifcool.rememberactivities.Database.RememberActivitiesContract;
import org.d3ifcool.rememberactivities.Model.Kegiatan;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


public class TambahKegiatanActivity extends AppCompatActivity{
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
    private DatabaseReference database;
    private FirebaseUser mfirebaseUser;
    String email;
    int idKegiatan;
    String cekIntent;
    double lang,lat;
    Kegiatan kegiatan;
    int PLACE_PICKER_REQUEST=1;
    Calendar calNow;
    Calendar calSet,calSetSelesai;
    private FirebaseUser mFireBaseuser;
    private ArrayList<Kegiatan> daftarKegiatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kegiatan);
        //inisialisasi tampilan
        namaKgt = findViewById(R.id.namaKegiatan);
        tglKgt = findViewById(R.id.tanggalKegiatan);
        jamMulai = findViewById(R.id.jamMulai);
        jamBrakhir = findViewById(R.id.jamBerakhir);
        tempat = findViewById(R.id.tempat);
        catatan = findViewById(R.id.catatan);
        calNow= Calendar.getInstance();
        calSet= (Calendar) calNow.clone();
        calSetSelesai=(Calendar) calNow.clone();
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

        //dibawah ini adalah inisialisasi semua komponen
        Intent intent = getIntent();
        //disini untuk database
        database=FirebaseDatabase.getInstance().getReference("Kegiatan");
        mfirebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        email=FirebaseAuth.getInstance().getCurrentUser().getUid();
        mCurrentUri = intent.getData();

        //Inisialisasi untuk place picker
        final PlacePicker.IntentBuilder builder=new PlacePicker.IntentBuilder();

        tempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    startActivityForResult(builder.build(TambahKegiatanActivity.this),PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                }
            }
        });

        idKegiatan=getIntent().getIntExtra("id",0);
        Log.e("cek id", "onCreate: "+idKegiatan );
        if (getIntent().getSerializableExtra("datakegiatan") == null) {
            setTitle("Tambah Kegiatan");
            invalidateOptionsMenu();
        } else {
            setTitle(R.string.edit_kegiatan);
           kegiatan =(Kegiatan)getIntent().getSerializableExtra("datakegiatan");
            //getLoaderManager().initLoader(EXISTING_LOADER, null, this);
            namaKgt.setText(kegiatan.getNamaKegiatan());
            tglKgt.setText(kegiatan.getTglKegiatan());
            jamMulai.setText(kegiatan.getJamKegiatan());
            jamBrakhir.setText(kegiatan.getBerakhirKegiatan());
            tempat.setText(kegiatan.getTempatKegiatan());
            catatan.setText(kegiatan.getCatatanKegiatan());

        }
        newMydbHelper = new DBHelper(this);


        //mengambil data
        database.child(email.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                daftarKegiatan=new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()){
                    Kegiatan kegiatan=noteDataSnapshot.getValue(Kegiatan.class);
                    kegiatan.setKey(noteDataSnapshot.getKey());
                    daftarKegiatan.add(kegiatan);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Lihat kegiatan", "onCancelled: "+databaseError);
            }
        });

    }

    //cek jika ada field yang kosong
    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    //Untuk kembalian place picker


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==PLACE_PICKER_REQUEST){
            if (resultCode==RESULT_OK){
                Place place=PlacePicker.getPlace(data,this);
                lang=place.getLatLng().longitude;
                lat=place.getLatLng().latitude;
                tempat.setText(place.getAddress().toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void tambahData(){
        //get email

        if (!isEmpty(namaKgt.getText().toString())&& !isEmpty(tglKgt.getText().toString()) && !isEmpty(jamMulai.getText().toString()) && !isEmpty(jamBrakhir.getText().toString())) {
            if (isEmpty(catatan.getText().toString())) {
                String tempCatat = "Anda Tidak memasukan Catatan";
                submitKegiatan(new Kegiatan(namaKgt.getText().toString(), tglKgt.getText().toString(), jamMulai.getText().toString(), jamBrakhir.getText().toString(), tempat.getText().toString(),lang,lat, tempCatat, email));

            } else {
                submitKegiatan(new Kegiatan(namaKgt.getText().toString(), tglKgt.getText().toString(), jamMulai.getText().toString(), jamBrakhir.getText().toString(), tempat.getText().toString(),lang,lat,catatan.getText().toString(),email));
            }
        }else{
            Message.message(this,"Terdapat Field yang Kosong");
        }
    }

    private void submitKegiatan(Kegiatan kegiatan){
        database.child(email.toString()).push().setValue(kegiatan).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Message.message(getApplicationContext(),"Berhasil Menyimpan Kegiatan");
                startActivity(new Intent(TambahKegiatanActivity.this,LihatKegiatanActivity.class));
                setAlarm(calSet);
                setAlarmselesai(calSetSelesai);
                finish();
            }
        });

    }

    private void updateKegiatan(Kegiatan kegiatan){
        database.child(email.toString()).child(kegiatan.getKey()).setValue(kegiatan).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Message.message(getApplicationContext(),"Kegiatan Berhasil dirubah");
                startActivity(new Intent(TambahKegiatanActivity.this,LihatKegiatanActivity.class));
                finish();
            }
        });
    }
    private void updateData(){
        if (!isEmpty(namaKgt.getText().toString())&& !isEmpty(tglKgt.getText().toString()) && !isEmpty(jamMulai.getText().toString()) && !isEmpty(jamBrakhir.getText().toString())) {
            if (isEmpty(catatan.getText().toString())) {
                String tempCatat = "Anda Tidak memasukan Catatan";
                kegiatan.setNamaKegiatan(namaKgt.getText().toString());
                kegiatan.setTglKegiatan(tglKgt.getText().toString());
                kegiatan.setJamKegiatan(jamMulai.getText().toString());
                kegiatan.setBerakhirKegiatan(jamBrakhir.getText().toString());
                kegiatan.setTempatKegiatan(tempat.getText().toString());
                kegiatan.setCatatanKegiatan(tempCatat);
            } else {
                kegiatan.setNamaKegiatan(namaKgt.getText().toString());
                kegiatan.setTglKegiatan(tglKgt.getText().toString());
                kegiatan.setJamKegiatan(jamMulai.getText().toString());
                kegiatan.setBerakhirKegiatan(jamBrakhir.getText().toString());
                kegiatan.setTempatKegiatan(tempat.getText().toString());
                kegiatan.setCatatanKegiatan(catatan.getText().toString());
                updateKegiatan(kegiatan);
            }
        }else{
            Message.message(this,"Terdapat Field yang Kosong");
        }
    }





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
                if (kegiatan==null) {
                    tambahData();
                    notifTemplate("Remember Activities", "Kegiatan Berhasil Ditambah");
                }else
                {
                    updateData();
                }
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
            calSet.set(tahun,bulan,hari);
            calSet.set(Calendar.HOUR_OF_DAY, i);
            calSet.set(Calendar.MINUTE, i1);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);
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
            calSetSelesai.set(tahun,bulan,hari);
            calSetSelesai.set(Calendar.HOUR_OF_DAY, i);
            calSetSelesai.set(Calendar.MINUTE, i1);
            calSetSelesai.set(Calendar.SECOND, 0);
            calSetSelesai.set(Calendar.MILLISECOND, 0);
           // setAlarmselesai(calSet);

        }
    };



        //disini terdapat algoritma untuk menambahkan alaram
      private void setAlarm(Calendar target){
          //untuk tambah kegiatan
          if (getIntent().getSerializableExtra("datakegiatan") == null) {
              Intent intent = new Intent(TambahKegiatanActivity.this, AlarmRecivier.class);
              intent.putExtra("id", daftarKegiatan.size() - 1);
              intent.putExtra("kegiatan", daftarKegiatan.get(daftarKegiatan.size() - 1).getNamaKegiatan());
              intent.putExtra("jam", daftarKegiatan.get(daftarKegiatan.size() - 1).getJamKegiatan());
              intent.putExtra("lat", daftarKegiatan.get(daftarKegiatan.size() - 1).getLat());
              intent.putExtra("lang", daftarKegiatan.get(daftarKegiatan.size() - 1).getLang());
              PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), daftarKegiatan.size() - 1, intent, 0);
              AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
              alarmManager.set(AlarmManager.RTC_WAKEUP, target.getTimeInMillis(), pendingIntent);
          }else{
              // untuk edit kegiatan
              Intent intent = new Intent(TambahKegiatanActivity.this, AlarmRecivier.class);
              intent.putExtra("id", daftarKegiatan.size() - 1);
              intent.putExtra("kegiatan", daftarKegiatan.get(daftarKegiatan.size() - 1).getNamaKegiatan());
              intent.putExtra("jam", daftarKegiatan.get(daftarKegiatan.size() - 1).getJamKegiatan());
              intent.putExtra("lat", daftarKegiatan.get(daftarKegiatan.size() - 1).getLat());
              intent.putExtra("lang", daftarKegiatan.get(daftarKegiatan.size() - 1).getLang());
              PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), idKegiatan, intent, 0);
              AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
              alarmManager.set(AlarmManager.RTC_WAKEUP, target.getTimeInMillis(), pendingIntent);
          }
    }

    private void setAlarmselesai(Calendar target){
          //untuk tambah kegiatan
        if (getIntent().getSerializableExtra("datakegiatan") == null) {
            Intent intent = new Intent(TambahKegiatanActivity.this, SecondAlarmRecivier.class);
            intent.putExtra("id", daftarKegiatan.size() - 1);
            intent.putExtra("id", daftarKegiatan.size() - 1);
            intent.putExtra("kegiatan", daftarKegiatan.get(daftarKegiatan.size() - 1).getNamaKegiatan());
            intent.putExtra("jam", daftarKegiatan.get(daftarKegiatan.size() - 1).getJamKegiatan());
            intent.putExtra("lat", daftarKegiatan.get(daftarKegiatan.size() - 1).getLat());
            intent.putExtra("lang", daftarKegiatan.get(daftarKegiatan.size() - 1).getLang());
            intent.putExtra("catatan", daftarKegiatan.get(daftarKegiatan.size() - 1).getCatatanKegiatan());
            intent.putExtra("jamselesai", daftarKegiatan.get(daftarKegiatan.size() - 1).getBerakhirKegiatan());
            intent.putExtra("tgl", daftarKegiatan.get(daftarKegiatan.size() - 1).getTglKegiatan());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), daftarKegiatan.size() - 1, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, target.getTimeInMillis(), pendingIntent);
        }else {
            //untuk edit kegiatan
            Intent intent = new Intent(TambahKegiatanActivity.this, SecondAlarmRecivier.class);
            intent.putExtra("id", daftarKegiatan.size() - 1);
            intent.putExtra("id", daftarKegiatan.size() - 1);
            intent.putExtra("kegiatan", daftarKegiatan.get(daftarKegiatan.size() - 1).getNamaKegiatan());
            intent.putExtra("jam", daftarKegiatan.get(daftarKegiatan.size() - 1).getJamKegiatan());
            intent.putExtra("lat", daftarKegiatan.get(daftarKegiatan.size() - 1).getLat());
            intent.putExtra("lang", daftarKegiatan.get(daftarKegiatan.size() - 1).getLang());
            intent.putExtra("catatan", daftarKegiatan.get(daftarKegiatan.size() - 1).getCatatanKegiatan());
            intent.putExtra("jamselesai", daftarKegiatan.get(daftarKegiatan.size() - 1).getBerakhirKegiatan());
            intent.putExtra("tgl", daftarKegiatan.get(daftarKegiatan.size() - 1).getTglKegiatan());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), idKegiatan, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, target.getTimeInMillis(), pendingIntent);
        }
    }
    }

