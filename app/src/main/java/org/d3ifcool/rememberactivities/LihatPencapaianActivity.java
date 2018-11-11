package org.d3ifcool.rememberactivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.d3ifcool.rememberactivities.Adapter.AdapterLihatKegiatan;
import org.d3ifcool.rememberactivities.Adapter.AdapterPencapaian;
import org.d3ifcool.rememberactivities.Model.Kegiatan;
import org.d3ifcool.rememberactivities.Model.Pencapaian;

import java.util.ArrayList;

public class LihatPencapaianActivity extends AppCompatActivity {
    private FirebaseUser mFireBaseuser;
    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<Pencapaian> daftarPencapaian;
    private TextView mEmptyView;
    ConnectivityManager connectivityManager;
    ProgressDialog progressDialog;
    String Uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_pencapaian);
        //inisialisasi recyle view dan komponen

        rvView=(RecyclerView)findViewById(R.id.recylce_pencapaian);
        rvView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(this,
                layoutManager.getOrientation());
        rvView.addItemDecoration(divider);
        mEmptyView=findViewById(R.id.emptyview_pencapaian);
        daftarPencapaian=new ArrayList<>();
        connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (connectivityManager.getActiveNetwork() != null && connectivityManager.getActiveNetworkInfo().isAvailable() && connectivityManager.getActiveNetworkInfo().isConnected()) {
                progressDialog = ProgressDialog.show(LihatPencapaianActivity.this, "Sedang Mengambil Pencapaian Anda", "Harap Tunggu", false, false);
                mFireBaseuser= FirebaseAuth.getInstance().getCurrentUser();
                database= FirebaseDatabase.getInstance().getReference("Pencapaian");
                Uid=mFireBaseuser.getUid();
//mengambil data
                database.child(Uid.toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()){
                            Pencapaian pencapaian=noteDataSnapshot.getValue(Pencapaian.class);
                            pencapaian.setKeyPencapaian(noteDataSnapshot.getKey());
                            daftarPencapaian.add(pencapaian);
                        }
                        adapter=new AdapterPencapaian(daftarPencapaian, LihatPencapaianActivity.this,mEmptyView, new AdapterPencapaian.ClickHandler() {
                            @Override
                            public void onItemClick(int posisi) {
                                Log.d("lihat kegiatan", "onItemClick: click");
                                Intent intent=new Intent(LihatPencapaianActivity.this,PencapainDetailActivity.class);
                                intent.putExtra("data",daftarPencapaian.get(posisi));
                                startActivity(intent);
                            }
                        });
                        rvView.setAdapter(adapter);
                        updateEmptyView();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("Lihat Pencapaian", "onCancelled: "+databaseError);
                    }
                });
            }else{
                mEmptyView.setText("Pencapaian Tidak bisa ditampilkan \n Mohon Cek Koneksi Anda");
            }
        }




    }
    public void updateEmptyView(){
        if (daftarPencapaian.size()==0){
            mEmptyView.setVisibility(View.VISIBLE);
        }else {
            mEmptyView.setVisibility(View.GONE);

        }
    }
}

