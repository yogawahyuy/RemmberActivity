package org.d3ifcool.rememberactivities.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.d3ifcool.rememberactivities.Database.RememberActivitiesContract;
import org.d3ifcool.rememberactivities.R;

/**
 * Created by Yoga Wahyu Yuwono on 19/09/2018.
 */

public class AdapterLihatKegiatan extends CursorAdapter {

    public AdapterLihatKegiatan(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.lihatkegiatan_card_view,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView namaKegiatan=view.findViewById(R.id.title_cardview);
        TextView tglKegiatan=view.findViewById(R.id.date);

        int namaColumnIndex=cursor.getColumnIndex(RememberActivitiesContract.myContractEntry.NAME);
        int tglColumnIndex=cursor.getColumnIndex(RememberActivitiesContract.myContractEntry.tgl_mulai);

        String namaKgt=cursor.getString(namaColumnIndex);
        String tglKgt=cursor.getString(tglColumnIndex);

        namaKegiatan.setText(namaKgt);
        tglKegiatan.setText(tglKgt);


    }
}
