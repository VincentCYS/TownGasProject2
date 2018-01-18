package com.cityu.ast.towngasproject;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.cityu.ast.towngasproject.customAdapter.AddStaffListViewAdapter;

import java.util.ArrayList;

public class AddStaffActivity extends AppCompatActivity {
    View actionBarView;
    ArrayList<String> list;
    AddStaffListViewAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);
        ListView listView = (ListView) findViewById(R.id.addStaffListView);
        ImageButton imageBtnTakePhoto = (ImageButton) findViewById(R.id.imageBtnTakePhoto);

        createActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        list = new ArrayList<String>();

        list.add("item1");

        //instantiate custom adapter
        adapter = new AddStaffListViewAdapter(list, this);

        //handle listview and assign adapter
        listView.setAdapter(adapter);


        imageBtnTakePhoto.setOnClickListener(new View.OnClickListener() {
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
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(actionBarView.getContext());
                builder.setMessage("全部刪除?");
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "確定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                list.removeAll(list);
                                adapter.notifyDataSetChanged();
                                finish();
                                dialog.cancel();
                            }
                        });

                builder.setNegativeButton(
                        "取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                android.app.AlertDialog alert = builder.create();
                alert.show();



            }
        });
    }


    public void createActionBar(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.add_staff_custom_action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarView = getSupportActionBar().getCustomView();
    }
}