package org.d3ifcool.rememberactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import org.d3ifcool.rememberactivities.Model.Kegiatan;

public class RincianKegiatanActivity extends AppCompatActivity {
    private FirebaseUser mFirebaseUser;
    private DatabaseReference database;
    private TextView namaKgt,tglKgt,jamMulai,jamBerakhir,catatan,tempat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_kegiatan);
        namaKgt=findViewById(R.id.rincian_namaKgt);
        tglKgt=findViewById(R.id.rincian_tglMulai);
        jamMulai=findViewById(R.id.rincian_jamMulai);
        jamBerakhir=findViewById(R.id.rincian_jamBerakhir);
        catatan=findViewById(R.id.rincian_catatan);

        final Kegiatan kegiatan=(Kegiatan) getIntent().getSerializableExtra("data");

        if (kegiatan!=null){
            namaKgt.setText(kegiatan.getNamaKegiatan());
            tglKgt.setText(kegiatan.getTglKegiatan());
            jamMulai.setText(kegiatan.getJamKegiatan());
            jamBerakhir.setText(kegiatan.getBerakhirKegiatan());
            catatan.setText(kegiatan.getCatatanKegiatan());
        }else {
            Message.message(getApplicationContext(),"Kegiatan Kosong");
            startActivity(new Intent(RincianKegiatanActivity.this,LihatKegiatanActivity.class));
        }

    }
}
