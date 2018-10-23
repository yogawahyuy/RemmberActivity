package org.d3ifcool.rememberactivities;

import android.content.Intent;
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
                Snackbar.make(view, "Bagikan melalui email", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
