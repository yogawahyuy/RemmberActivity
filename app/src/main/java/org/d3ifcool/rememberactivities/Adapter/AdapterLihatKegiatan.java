package org.d3ifcool.rememberactivities.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.d3ifcool.rememberactivities.Database.RememberActivitiesContract;
import org.d3ifcool.rememberactivities.Model.Kegiatan;
import org.d3ifcool.rememberactivities.R;

import java.util.ArrayList;

/**
 * Created by Yoga Wahyu Yuwono on 19/09/2018.
 */

public class AdapterLihatKegiatan extends RecyclerView.Adapter<AdapterLihatKegiatan.ViewHolder> {

    private ArrayList<Kegiatan> daftarKegiatan;
    private Context context;

    public AdapterLihatKegiatan(ArrayList<Kegiatan> kegiatans,Context ctx){
        daftarKegiatan=kegiatans;
        context=ctx;
    }



    class ViewHolder extends RecyclerView.ViewHolder{
        TextView jam,nama,tgl;

        public ViewHolder(View itemView) {
            super(itemView);
            jam=itemView.findViewById(R.id.waktu_mulai);
            nama=itemView.findViewById(R.id.custom_namaKgt);
            tgl=itemView.findViewById(R.id.custom_tglKgt);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.customlist_lihatkegiatan,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String jam=daftarKegiatan.get(position).getJamKegiatan();
        final String nama=daftarKegiatan.get(position).getNamaKegiatan();
        final String tgl=daftarKegiatan.get(position).getTglKegiatan();

        holder.tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.jam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.nama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.nama.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        holder.jam.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        holder.tgl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        holder.tgl.setText(tgl);
        holder.jam.setText(jam);
        holder.nama.setText(nama);
    }

    @Override
    public int getItemCount() {
        return daftarKegiatan.size();
    }

//    public AdapterLihatKegiatan(Context context, Cursor c) {
//        super(context, c, 0);
//    }
//
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        return LayoutInflater.from(context).inflate(R.layout.lihatkegiatan_card_view,parent,false);
//    }
//
//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//        TextView namaKegiatan=view.findViewById(R.id.title_cardview);
//        TextView tglKegiatan=view.findViewById(R.id.date);
//
//        int namaColumnIndex=cursor.getColumnIndex(RememberActivitiesContract.myContractEntry.NAME);
//        int tglColumnIndex=cursor.getColumnIndex(RememberActivitiesContract.myContractEntry.tgl_mulai);
//
//        String namaKgt=cursor.getString(namaColumnIndex);
//        String tglKgt=cursor.getString(tglColumnIndex);
//
//        namaKegiatan.setText(namaKgt);
//        tglKegiatan.setText(tglKgt);
//
//
//    }
}
