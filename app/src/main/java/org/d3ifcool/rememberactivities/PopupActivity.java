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
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.d3ifcool.rememberactivities.Database.DBHelper;
import org.d3ifcool.rememberactivities.Database.RememberActivitiesContract;
import org.d3ifcool.rememberactivities.Alarm.AlarmRecivier;

import static org.d3ifcool.rememberactivities.TambahKegiatanActivity.RQS_1;

public class PopupActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView namaKegiatan,jamMulai,judul;
    Button jeda,tutup;
    int posisi;
    String kegiatan,jamKegiatan,catatannya,jamselesai;
    double lat,lang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        namaKegiatan=findViewById(R.id.popUp_namaKegiatan);
        jamMulai=findViewById(R.id.popUp_jamMulaikegiatan);
        jeda=findViewById(R.id.popUp_jeda);
        tutup=findViewById(R.id.popUp_tutup);
        judul=findViewById(R.id.popUp_judul);

        //maps
        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.popUP_mapView);
        mapFragment.getMapAsync(this);

        //intent
        Intent intent=getIntent();
        posisi=intent.getIntExtra("posisi",0);
        kegiatan=intent.getStringExtra("namaKgt");
        jamKegiatan=intent.getStringExtra("jamkgt");
        lat=intent.getDoubleExtra("lat",0);
        lang=intent.getDoubleExtra("lang",0);
        catatannya=intent.getStringExtra("catatan");
        jamselesai=intent.getStringExtra("jamselesai");
        if (catatannya!=null&&catatannya.equalsIgnoreCase("Anda Tidak memasukan Catatan")){
            jeda.setVisibility(View.GONE);
            judul.setText("Kegiatan Selesai \n Anda tidak memasukan hal yang akan dicapai");
            namaKegiatan.setText(catatannya);
            jamMulai.setText(jamselesai);
            //ringtone
            try {
                Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                final Ringtone curent = RingtoneManager.getRingtone(getApplicationContext(), ringtone);
                curent.play();
                tutup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        curent.stop();
                        finish();
                    }
                });

            }catch (Exception e){
                Log.e("popup", "onCreate: "+e );
            }
        }else if (catatannya!=null&&!catatannya.equalsIgnoreCase("Anda Tidak memasukan Catatan")){
            jeda.setText("Tercapai");
            tutup.setText("Tidak Tercapai");
        }else if (catatannya==null){
            namaKegiatan.setText(kegiatan);
            jamMulai.setText(jamKegiatan);
            //ringtone
            try {
                Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                final Ringtone curent = RingtoneManager.getRingtone(getApplicationContext(), ringtone);
                curent.play();
                tutup.setText("Tidak Tercapai");
                tutup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        curent.stop();
                        finish();
                    }
                });

            }catch (Exception e){
                Log.e("popup", "onCreate: "+e );
            }
        }



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng tempatnya=new LatLng(lat,lang);
        googleMap.addMarker(new MarkerOptions().position(tempatnya)).setTitle(kegiatan);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(tempatnya));
    }

}

