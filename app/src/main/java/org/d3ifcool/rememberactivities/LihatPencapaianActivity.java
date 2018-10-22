package org.d3ifcool.rememberactivities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.d3ifcool.rememberactivities.Adapter.AdapterPencapaian;
import org.d3ifcool.rememberactivities.Model.Kegiatan;

import java.util.ArrayList;

public class LihatPencapaianActivity extends AppCompatActivity {
    private FirebaseUser mFireBaseuser;
    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<Kegiatan> daftarKegiatan;
    String Uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_pencapaian);
        //inisialisasi recyle view dan komponen

        rvView=(RecyclerView)findViewById(R.id.recylce_kegiatan);
        rvView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(this,
                layoutManager.getOrientation());
        rvView.addItemDecoration(divider);

        mFireBaseuser= FirebaseAuth.getInstance().getCurrentUser();
        database= FirebaseDatabase.getInstance().getReference("Kegiatan");
        Uid=mFireBaseuser.getUid();


    }
}
