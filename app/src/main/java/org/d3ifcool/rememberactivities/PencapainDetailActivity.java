package org.d3ifcool.rememberactivities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.d3ifcool.rememberactivities.Model.Pencapaian;

public class PencapainDetailActivity extends AppCompatActivity {
    private TextView pencapaianKgt,namaKgt,tglKgt;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference database;
    String uid;
    Pencapaian pencapaian;
    String namaUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencapain_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pencapaianKgt=findViewById(R.id.pencapaian_catatKgt);
        namaKgt=findViewById(R.id.pencapaian_namaKgt);
        tglKgt=findViewById(R.id.pencapaian_tglKgt);

        //database
        mFirebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        database= FirebaseDatabase.getInstance().getReference("Pencapaian");
        uid=mFirebaseUser.getUid();
        namaUser=mFirebaseUser.getDisplayName();

        pencapaian=(Pencapaian) getIntent().getSerializableExtra("data");
        if (pencapaian!=null){
            namaKgt.setText(pencapaian.getNamaPencapaian());
            tglKgt.setText(pencapaian.getTgl_pencapain());
            pencapaianKgt.setText(pencapaian.getCatatanPencapaian());
        }else {
            Message.message(getApplicationContext(),"Kegiatan Kosong");
            startActivity(new Intent(PencapainDetailActivity.this,LihatKegiatanActivity.class));
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messeage=emailBody(pencapaian.getCatatanPencapaian(),pencapaian.getNamaPencapaian(),pencapaian.getTgl_pencapain());
                Intent intent=new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_SUBJECT,"Pencapaaian Kegiatan yang dilakukan oleh "+namaUser);
                intent.putExtra(Intent.EXTRA_TEXT,messeage);
                if (intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }
            }
        });
    }

    private String emailBody(String pencapaian,String namaKgt,String tgl){
        String isiEmail= "Pencapaaian yang sudah dilakukan oleh :"+namaUser;
        isiEmail +="\n Pencapaian       :"+pencapaian;
        isiEmail +="\n Nama Kegiatan    :"+namaKgt;
        isiEmail +="\n Tanggal Kegiatan :"+tgl;
        isiEmail +="\n";
        isiEmail +="\n";
        isiEmail +="\n Email Ini dibuat otomatis oleh aplikasi RememberActivities";

        return isiEmail;
    }
}
