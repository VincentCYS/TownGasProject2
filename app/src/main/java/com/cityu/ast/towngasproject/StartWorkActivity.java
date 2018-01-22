package com.cityu.ast.towngasproject;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cityu.ast.towngasproject.customAdapter.StartWorkListViewAdapter;

import static com.cityu.ast.towngasproject.CameraActivity.imageBitmap;
import static com.cityu.ast.towngasproject.WorkerListDialog.staffList;
import static com.cityu.ast.towngasproject.customAdapter.StartWorkListViewAdapter.list;


public class StartWorkActivity extends AppCompatActivity {
    View actionBarView;
    public static StartWorkListViewAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_work);
        ListView listView = (ListView) findViewById(R.id.startWorkListView);


        // Show the actionbar
        createActionBar();

        list.add(imageBitmap);

        //instantiate custom adapter
        adapter = new StartWorkListViewAdapter(this);

        //handle listview and assign adapter
        listView.setAdapter(adapter);

        // Start the android camera app
        Button btnTakePhoto = (Button) findViewById(R.id.btnStartTakePhoto);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CameraActivity.class));
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            btnTakePhoto.callOnClick();
        }


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
                showFinalStaffListDialog();
            }
        });
    }

    public void btnStartEvent () {
        list.removeAll(list);
        adapter.notifyDataSetChanged();
        finish();
    }

    public void showProcessSuccessDialog() {
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


    public void showFinalStaffListDialog() {
        if (staffList != null) {

            new AlertDialog.Builder(actionBarView.getContext(), R.style.Theme_AppCompat_DayNight_Dialog_Alert)
                    .setTitle("名單")
                    .setMessage(staffList)
                    .setCancelable(true)

                    // Positive button event
                    .setPositiveButton(
                            "確定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    showProcessSuccessDialog();
                                }

                                // Negative button event
                            }).setNegativeButton(
                    "取消",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }).create().show();
        } else {
            Toast.makeText(actionBarView.getContext(), "empty", Toast.LENGTH_SHORT).show();
        }
    }


    public void createActionBar(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.start_work_custom_action_bar);
        // Disable the back button on the actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        actionBarView = getSupportActionBar().getCustomView();
    }
}