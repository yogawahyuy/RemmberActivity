package org.d3ifcool.rememberactivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.d3ifcool.rememberactivities.Adapter.AdapterLihatKegiatan;
import org.d3ifcool.rememberactivities.Model.Kegiatan;

public class RincianKegiatanActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FirebaseUser mFirebaseUser;
    private DatabaseReference database;
    String uid;
    Kegiatan kegiatan;
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

        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);


        //database
        mFirebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        database= FirebaseDatabase.getInstance().getReference("Kegiatan");
        uid=mFirebaseUser.getUid();

        kegiatan=(Kegiatan) getIntent().getSerializableExtra("data");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_kegiatan,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.rincian_edit_kegiatan:
                Intent intent=new Intent(RincianKegiatanActivity.this,TambahKegiatanActivity.class);
                intent.putExtra("datakegiatan",getIntent().getSerializableExtra("data"));
                startActivity(intent);
                break;
            case R.id.rincian_hapus_kegiatan:
                dialoghapusKegiatan();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void dialoghapusKegiatan(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Hapus Kegiatan");
        builder.setMessage("Hapus kegiatan "+kegiatan.getNamaKegiatan()+" ?");
        builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteKegiatan();
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }

    private void deleteKegiatan(){
        if (database!=null){
            database.child(uid.toString()).child(kegiatan.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Message.message(getApplicationContext(),"Kegiatan Berhasil dihapus");
                    startActivity(new Intent(RincianKegiatanActivity.this,LihatKegiatanActivity.class));
                    finish();
                }
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng tempatnya=new LatLng(kegiatan.getLat(),kegiatan.getLang());
        googleMap.addMarker(new MarkerOptions().position(tempatnya)).setTitle(kegiatan.getTempatKegiatan());
        CameraPosition cameraPosition=new CameraPosition.Builder().target(tempatnya).zoom(17).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

//    @Override
//    public void onDeleteData(Kegiatan kegiatan, int posisi) {
//        if (database!=null){
//            database.child(uid.toString()).child(kegiatan.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//                    Message.message(getApplicationContext(),"Kegiatan Berhasil dihapus");
//                }
//            });
//        }
//    }
}
