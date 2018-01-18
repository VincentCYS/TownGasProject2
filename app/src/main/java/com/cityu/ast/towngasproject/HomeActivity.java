package com.cityu.ast.towngasproject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.io.File;

public class HomeActivity extends AppCompatActivity {

    Button btnStartHome, btnEnd;
    static final int CAM_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Hide action bar
        getSupportActionBar().hide();

        btnStartHome = (Button) findViewById(R.id.btnStartHome);
        btnEnd = (Button) findViewById(R.id.btnEnd);

        btnStartHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CameraActivity.class);
                startActivity(intent);
            }

        });
    }

}
