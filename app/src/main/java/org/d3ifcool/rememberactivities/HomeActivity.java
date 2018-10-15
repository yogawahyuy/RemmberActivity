package org.d3ifcool.rememberactivities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {


    CardView cardView,kegiatan,pencapaian,logout,tentangkami;

    private FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //untuk mengilangkan toolbar
//        if (Build.VERSION.SDK_INT < 16) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }else {
//            View decorView=getWindow().getDecorView();
//            int uiOption=View.SYSTEM_UI_FLAG_FULLSCREEN;
//            decorView.setSystemUiVisibility(uiOption);
//            android.app.ActionBar actionBar=getActionBar();
//            actionBar.hide();
//        }
        setContentView(R.layout.activity_home);
        cardView=findViewById(R.id.tambah);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,TambahKegiatanActivity.class);
                startActivity(intent);
            }
        });
        kegiatan=findViewById(R.id.lihatkegiatan_cardview);
        kegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,LihatKegiatanActivity.class));
            }
        });
        logout=findViewById(R.id.keluar);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        pencapaian=findViewById(R.id.lihatpencapaian);
        pencapaian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,LihatPencapaianActivity.class));
            }
        });

        tentangkami=findViewById(R.id.tentangkami);
        tentangkami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,TentangKamiActivity.class));
            }
        });
//        tambah = (FloatingActionButton)findViewById(R.id.floatingActionButton);
//        tambah.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HomeActivity.this,TambahKegiatanActivity.class);
//                startActivity(intent);
//            }
//        });
//        pencapaian = (FloatingActionButton)findViewById(R.id.floatingActionButton3);
//        pencapaian.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent2 = new Intent(HomeActivity.this,LihatPencapaianActivity.class);
//                startActivity(intent2);
//            }
//        });


        mFirebaseAuth=FirebaseAuth.getInstance();

        mAuthListener=new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(HomeActivity.this,MainActivity.class));
                    finish();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    //method logout
    private void signOut() {
        // Firebase sign out
        mFirebaseAuth.signOut();


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
            case R.id.profil:
                startActivity(new Intent(HomeActivity.this, TentangKamiActivity.class));
                break;
            case R.id.logout:
                signOut();
                break;


        }
        return super.onOptionsItemSelected(item);
    }
}
