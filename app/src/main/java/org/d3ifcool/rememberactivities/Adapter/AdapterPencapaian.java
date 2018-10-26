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
import org.d3ifcool.rememberactivities.Model.Pencapaian;
import org.d3ifcool.rememberactivities.R;

import java.util.ArrayList;

/**
 * Created by Yoga Wahyu Yuwono on 19/09/2018.
 */

public class AdapterPencapaian extends RecyclerView.Adapter<AdapterPencapaian.ViewHolder> {
    private AdapterPencapaian.ClickHandler clickHandler;
    //private AdapterPencapaian.DeleteHandler deleteHandler;
    private ArrayList<Pencapaian> daftarPencapaian;
    private Context context;
    private ArrayList<Integer> mSelectedId;
    private View emptyView;

    public AdapterPencapaian(ArrayList<Pencapaian> kegiatans, Context ctx, View empty,AdapterPencapaian.ClickHandler handler){
        daftarPencapaian=kegiatans;
        context=ctx;
        clickHandler=handler;
        mSelectedId=new ArrayList<>();
        emptyView=empty;
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
        final String jam=daftarPencapaian.get(position).getTgl_pencapain();
        final String nama=daftarPencapaian.get(position).getNamaPencapaian();
        //final String tgl=daftarPencapaian.get(position).getTglKegiatan();

       // holder.tgl.setText(tgl);
        holder.jam.setText(jam);
        holder.nama.setText(nama);
        holder.itemView.setSelected(mSelectedId.contains(position));
    }

    @Override
    public int getItemCount() {
        return daftarPencapaian.size();
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
