package org.d3ifcool.rememberactivities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    FloatingActionButton tambah;
    FloatingActionButton pencapaian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tambah = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,TambahKegiatanActivity.class);
                startActivity(intent);
            }
        });
        pencapaian = (FloatingActionButton)findViewById(R.id.floatingActionButton3);
        pencapaian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(HomeActivity.this,LihatPencapaianActivity.class);
                startActivity(intent2);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.home_menu,menu);
      return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.lihatKegiatan:
                startActivity(new Intent(HomeActivity.this,LihatKegiatanActivity.class));
                break;
            case R.id.tentangKami:
                startActivity(new Intent(HomeActivity.this,TentangKamiActivity.class));
                break;
            case R.id.umpanBalik:
                String url="https://bit.ly/2HtQbc9";
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;


        }
        return super.onOptionsItemSelected(item);
    }
}
