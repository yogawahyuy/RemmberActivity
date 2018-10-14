package org.d3ifcool.rememberactivities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    FloatingActionButton tambah;
    FloatingActionButton kegiatan;
    FloatingActionButton pencapaian;
    FloatingActionButton tentangkami;
    FloatingActionButton umpanbalik;
    FloatingActionButton keluar;

    private FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleSignInClient mGoogleSignInClient;

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
