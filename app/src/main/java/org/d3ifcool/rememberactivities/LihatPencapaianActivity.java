package org.d3ifcool.rememberactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class LihatPencapaianActivity extends AppCompatActivity {

    String[] pencapaian= {"Telah melaksanakan rapat", "Telah menjadi Manager", "Programmer", "Guru", " "};
    int[]image ={R.drawable.ceklis,R.drawable.silang};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_pencapaian);

        ListView listView = (ListView)findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
    }

    class CustomAdapter extends BaseAdapter{



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
           convertView  = getLayoutInflater().inflate(R.layout.list_item,null);

            ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
            TextView textView = (TextView)convertView.findViewById(R.id.textView);

            imageView.setImageResource(image[position]);
            textView.setText(pencapaian[position]);
            return convertView ;
        }
    }


}
