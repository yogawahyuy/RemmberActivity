package org.d3ifcool.rememberactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    com.google.android.gms.common.SignInButton klikGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ini adalah punya silfi, silfi enak dong ada beban biar kurus hahhaahaha
        klikGoogle = (com.google.android.gms.common.SignInButton)findViewById(R.id.sign_in_button);
        klikGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

    }
    //yoga comit eaa
}
