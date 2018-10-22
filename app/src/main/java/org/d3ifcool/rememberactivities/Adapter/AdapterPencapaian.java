package org.d3ifcool.rememberactivities.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.d3ifcool.rememberactivities.Model.Kegiatan;
import org.d3ifcool.rememberactivities.R;

import java.util.ArrayList;

/**
 * Created by Yoga Wahyu Yuwono on 19/09/2018.
 */

public class AdapterPencapaian extends RecyclerView.Adapter<AdapterPencapaian.ViewHolder> {
    private AdapterLihatKegiatan.ClickHandler clickHandler;
    private AdapterLihatKegiatan.DeleteHandler deleteHandler;
    private ArrayList<Kegiatan> daftarKegiatan;
    private Context context;
    private ArrayList<Integer> mSelectedId;

    public AdapterPencapaian(ArrayList<Kegiatan> kegiatans,Context ctx,AdapterLihatKegiatan.ClickHandler handler){
        daftarKegiatan=kegiatans;
        context=ctx;
        clickHandler=handler;
        mSelectedId=new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.customlist_lihatkegiatan,parent,false);
        AdapterPencapaian.ViewHolder vh=new ViewHolder(v);
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

    public interface ClickHandler{
        void onItemClick(int posisi);

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




}
