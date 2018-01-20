package com.cityu.ast.towngasproject;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.cityu.ast.towngasproject.customAdapter.AddStaffListViewAdapter;

import java.util.ArrayList;

import static com.cityu.ast.towngasproject.CameraActivity.imageBitmap;
import static com.cityu.ast.towngasproject.customAdapter.AddStaffListViewAdapter.list;


public class AddStaffActivity extends AppCompatActivity {
    View actionBarView;
    AddStaffListViewAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);
        ListView listView = (ListView) findViewById(R.id.addStaffListView);

        // Show the actionbar
        createActionBar();

        list.add(imageBitmap);

        //instantiate custom adapter
        adapter = new AddStaffListViewAdapter(this);

        //handle listview and assign adapter
        listView.setAdapter(adapter);


        // Start the android camera app
        Button btnTakePhoto = (Button) findViewById(R.id.btnTakePhoto);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), CameraActivity.class));
            }
        });


        Button btnDeleteAll = (Button) actionBarView.findViewById(R.id.btnDeleteAll);
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(actionBarView.getContext(), R.style.Theme_AppCompat_DayNight_Dialog_Alert)
                        .setTitle("全部刪除?")
                        .setCancelable(true)

                        // Positive button event
                        .setPositiveButton(
                        "確定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                list.removeAll(list);
                                adapter.notifyDataSetChanged();
                                finish();
                                dialog.cancel();
                            }

                            // Negative button event
                        }).setNegativeButton(
                        "取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).create().show();



            }
        });

        Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(actionBarView.getContext(), R.style.Theme_AppCompat_DayNight_Dialog_Alert)
                .setTitle("打卡成功!")
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        btnStartEvent();
                    }
                })
                .setCancelable(true)

                        // Positive button event
                        .setPositiveButton(
                        "確定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                btnStartEvent();
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });
    }

    public void btnStartEvent () {
        list.removeAll(list);
        adapter.notifyDataSetChanged();
        finish();
    }


    public void createActionBar(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.add_staff_custom_action_bar);
        // Disable the back button on the actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        actionBarView = getSupportActionBar().getCustomView();
    }
}