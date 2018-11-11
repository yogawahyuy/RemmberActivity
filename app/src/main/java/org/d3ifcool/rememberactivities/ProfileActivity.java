package org.d3ifcool.rememberactivities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    String nama, email,nomorTelp;
    private FirebaseAuth mFirebaseAuth;
    GoogleSignInApi mGoogleSignClient;
    FirebaseAuth.AuthStateListener mAuthListener;
    GoogleApiClient msignclient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nama = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String personPhotoUrl = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
        nomorTelp = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

        mFirebaseAuth=FirebaseAuth.getInstance();
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignClient=Auth.GoogleSignInApi;
        msignclient=new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        msignclient.connect();
        TextView namaText = findViewById(R.id.namaAtas);
        TextView namaTextBawah = findViewById(R.id.namaBawah);
        TextView emailBawah = findViewById(R.id.emailProfile);
        CircleImageView image = findViewById(R.id.photosEmail);
        Button keluar = findViewById(R.id.keluar);


        namaText.setText(nama);
        namaTextBawah.setText(nama);
        emailBawah.setText(email);
        Glide.with(getApplicationContext()).load(personPhotoUrl).thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                finish();
            }
        });




    }
    private void signOut() {
        // Firebase sign out

        mGoogleSignClient.signOut(msignclient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                mFirebaseAuth.signOut();
            }
        });


    }
}
