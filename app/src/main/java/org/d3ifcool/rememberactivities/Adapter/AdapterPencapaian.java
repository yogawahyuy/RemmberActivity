package org.d3ifcool.rememberactivities.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.d3ifcool.rememberactivities.R;

/**
 * Created by Yoga Wahyu Yuwono on 19/09/2018.
 */

public class AdapterPencapaian extends BaseAdapter {

    String[] pencapaian = {"Telah melaksanakan rapat", "Telah menjadi Manager"};
    int[] image = {R.drawable.ceklis, R.drawable.silang};
    private LayoutInflater layoutInflater;

    @Override
    public int getCount() {
        return image.length;
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
        convertView = layoutInflater.inflate(R.layout.list_item, parent);


        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        TextView textView = (TextView) convertView.findViewById(R.id.textView);

        imageView.setImageResource(image[position]);
        textView.setText(pencapaian[position]);
        return convertView;
    }

}
