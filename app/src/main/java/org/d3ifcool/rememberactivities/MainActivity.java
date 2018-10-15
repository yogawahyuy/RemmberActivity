package org.d3ifcool.rememberactivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    com.google.android.gms.common.SignInButton klikGoogle;
    private static final int RC_SIGN=9001;
    private static final String TAG="Google Sign";
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth mFirebaseAuth;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=(ProgressBar)findViewById(R.id.pbar);

        //Configure Google Sign
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);

        mFirebaseAuth=FirebaseAuth.getInstance();


        //ini adalah punya silfi, silfi enak dong ada beban biar kurus hahhaahaha
        klikGoogle = (com.google.android.gms.common.SignInButton)findViewById(R.id.sign_in_button);
        klikGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
               SignIn();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void SignIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN){
            Task<GoogleSignInAccount>task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                progressBar.setVisibility(View.GONE);
                GoogleSignInAccount account=task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            }catch (ApiException e){
                Log.w(TAG, "Google Sign Failed because: ", e);
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
        Log.d(TAG, "firebaseAuthWithGoogle: "+acct.getId());

        AuthCredential credential= GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //jika sign succes update ui
                            Log.d(TAG, "onComplete: success");
                            FirebaseUser user=mFirebaseAuth.getCurrentUser();
                            updateUI(user);
                            progressBar.setVisibility(View.GONE);
                        }else {
                            //jika gagal memunculkan pesan ke user

                            Log.w(TAG, "onFailure: ", task.getException() );
                            Snackbar.make(findViewById(R.id.mainlayout),"Login Gagal",Snackbar.LENGTH_LONG).show();
                            updateUI(null);
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser user) {

        if (user != null) {
            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
