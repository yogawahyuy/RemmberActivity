package org.d3ifcool.rememberactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.d3ifcool.rememberactivities.Adapter.AdapterPencapaian;

import java.util.ArrayList;

public class LihatPencapaianActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_pencapaian);

        ListView listView = (ListView) findViewById(R.id.list_view);

//        AdapterPencapaian customAdapter = new AdapterPencapaian();
//        listView.setAdapter(customAdapter);

        ArrayList<String> list = new ArrayList<String>();
        list.add("Telah selesai melaksanakan tugas");
        list.add("Mencapai keberhasilah shalat Tahajud");
        list.add("Turun 2kg");
        list.add("Turun 5KG");
        list.add("Telah selesai melaksanakan tugas");
        list.add("Mencapai keberhasilah shalat Tahajud");
        list.add("Turun 2kg");
        list.add("Turun 5KG");
        list.add("Telah selesai melaksanakan tugas");
        list.add("Mencapai keberhasilah shalat Tahajud");
        list.add("Turun 2kg");
        list.add("Turun 5KG");list.add("Telah selesai melaksanakan tugas");
        list.add("Mencapai keberhasilah shalat Tahajud");
        list.add("Turun 2kg");
        list.add("Turun 5KG");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LihatPencapaianActivity.this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        

    }
}
