package com.cityu.ast.towngasproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.cityu.ast.towngasproject.StartWorkActivity.adapter;
import static com.cityu.ast.towngasproject.customAdapter.StartWorkListViewAdapter.list;

public class CameraActivity extends AppCompatActivity {
    static final int REQUEST_TAKE_PHOTO = 1;
    public static Bitmap imageBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ProgressDialog pDialog = new ProgressDialog(CameraActivity.this);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            final Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            list.add(imageBitmap);

            pDialog.setMessage("載入中...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
        pDialog.dismiss();


        finish();
    }



    }
