package org.d3ifcool.rememberactivities;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.MapView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.d3ifcool.rememberactivities.Adapter.AdapterLihatKegiatan;
import org.d3ifcool.rememberactivities.Database.RememberActivitiesContract;
import org.d3ifcool.rememberactivities.Model.Kegiatan;

import java.util.ArrayList;

public class LihatKegiatanActivity extends AppCompatActivity{
    private AdapterLihatKegiatan mAdapter;
    private FirebaseUser mFireBaseuser;

    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<Kegiatan> daftarKegiatan;
    View mEmptyView;
    ProgressDialog progressDialog;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_kegiatan);
        //inisialisasi recyle view dan komponen

        rvView=(RecyclerView)findViewById(R.id.recylce_kegiatan);
        rvView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(this,
                layoutManager.getOrientation());
        rvView.addItemDecoration(divider);
        mEmptyView=findViewById(R.id.emptyview_kegiatan);
        daftarKegiatan=new ArrayList<>();
        progressDialog=ProgressDialog.show(LihatKegiatanActivity.this,"Sedang Mengambil Kegiatan Anda","Harap Tunggu",false,false);


        //database
        mFireBaseuser= FirebaseAuth.getInstance().getCurrentUser();
        database= FirebaseDatabase.getInstance().getReference("Kegiatan");
        email=mFireBaseuser.getUid();
        //mengambil data
        database.child(email.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()){
                    Kegiatan kegiatan=noteDataSnapshot.getValue(Kegiatan.class);
                    kegiatan.setKey(noteDataSnapshot.getKey());
                    daftarKegiatan.add(kegiatan);
                }
                adapter=new AdapterLihatKegiatan(daftarKegiatan, LihatKegiatanActivity.this,mEmptyView,new AdapterLihatKegiatan.ClickHandler() {
                    @Override
                    public void onItemClick(int posisi) {
                        Log.d("lihat kegiatan", "onItemClick: click");
                      Intent intent=new Intent(LihatKegiatanActivity.this,RincianKegiatanActivity.class);
                      intent.putExtra("data",daftarKegiatan.get(posisi));
                      startActivity(intent);
                    }
                });
                rvView.setAdapter(adapter);
                updateEmptyView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Lihat kegiatan", "onCancelled: "+databaseError);
            }
        });




    }

    public void updateEmptyView(){
        if (daftarKegiatan.size()==0){
            mEmptyView.setVisibility(View.VISIBLE);
        }else {
            mEmptyView.setVisibility(View.GONE);

        }
    }
    }

