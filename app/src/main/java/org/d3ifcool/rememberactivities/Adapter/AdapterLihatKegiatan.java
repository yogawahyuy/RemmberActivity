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
    public ClickHandler clickHandler;
    private ArrayList<Kegiatan> daftarKegiatan;
    private Context context;
    private ArrayList<Integer> mSelectedId;

    public interface ClickHandler{
        void onItemClick(int posisi);
    }

    public AdapterLihatKegiatan(ArrayList<Kegiatan> kegiatans,Context ctx,ClickHandler handler){
        daftarKegiatan=kegiatans;
        context=ctx;
        clickHandler=handler;
        mSelectedId=new ArrayList<>();
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView jam,nama,tgl;

        public ViewHolder(View itemView) {
            super(itemView);
            jam=itemView.findViewById(R.id.waktu_mulai);
            nama=itemView.findViewById(R.id.custom_namaKgt);
            tgl=itemView.findViewById(R.id.custom_tglKgt);

            itemView.setFocusable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickHandler.onItemClick(getAdapterPosition());
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

        holder.tgl.setText(tgl);
        holder.jam.setText(jam);
        holder.nama.setText(nama);
        holder.itemView.setSelected(mSelectedId.contains(position));
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
