package org.d3ifcool.rememberactivities;

import android.app.LoaderManager;
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

public class LihatKegiatanActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final  int MY_LOADER=0;
    private AdapterLihatKegiatan mCursorAdapter;
    private FirebaseUser mFireBaseuser;

    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<Kegiatan> daftarKegiatan;

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


        //database
        mFireBaseuser= FirebaseAuth.getInstance().getCurrentUser();
        database= FirebaseDatabase.getInstance().getReference("Kegiatan");
        email=mFireBaseuser.getUid();
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
                adapter=new AdapterLihatKegiatan(daftarKegiatan, LihatKegiatanActivity.this, new AdapterLihatKegiatan.ClickHandler() {
                    @Override
                    public void onItemClick(int posisi) {
                        Log.d("lihat kegiatan", "onItemClick: click");
                      Intent intent=new Intent(LihatKegiatanActivity.this,RincianKegiatanActivity.class);
                      intent.putExtra("data",daftarKegiatan.get(posisi));
                      startActivity(intent);
                    }
                });
                rvView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Lihat kegiatan", "onCancelled: "+databaseError);
            }
        });

//        ListView lis=(ListView)findViewById(R.id.listView_kegiatan);
//        mCursorAdapter=new AdapterLihatKegiatan(this,null);
//        View emptyView=findViewById(R.id.emptyview);
//        lis.setEmptyView(emptyView);
//        mCursorAdapter=new AdapterLihatKegiatan(this,null);
//        lis.setAdapter(mCursorAdapter);
//        lis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent=new Intent(LihatKegiatanActivity.this,RincianKegiatanActivity.class);
//                Uri currentUri= ContentUris.withAppendedId(RememberActivitiesContract.myContractEntry.CONTENT_URI,id);
//                intent.setData(currentUri);
//                startActivity(intent);
//            }
//        });
//
//
//        getLoaderManager().initLoader(MY_LOADER,null,this);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection={
                RememberActivitiesContract.myContractEntry.UID,
                RememberActivitiesContract.myContractEntry.NAME,
                RememberActivitiesContract.myContractEntry.tgl_mulai,
        };
        return new CursorLoader(this,RememberActivitiesContract.myContractEntry.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //mCursorAdapter.swapCursor(null);
    }

    private class viewAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
