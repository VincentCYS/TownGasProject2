package com.cityu.ast.towngasproject;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddStaffActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;
    Bitmap imageBitmap;
    View actionBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        try {
            createActionBar();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();}

        // Open Android system camera app
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
           startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");

            ListView listView = (ListView)findViewById(R.id.addStaffListView);
            CustomAdapter customAdapter = new CustomAdapter();
            listView.setAdapter(customAdapter);
        } else {
            finish();
        }
    }

    public void createActionBar(){
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);

        //Initializes the custom action bar layout
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.add_staff_custom_action_bar, null);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    class CustomAdapter extends BaseAdapter {

        private String[] listItem = {
                "16170 陳大文",
                "16171 陳二文",
                "16172 陳三文",
                "16173 陳田文",
                "16174 陳四文",
                "16175 陳叛文"
        };
        private boolean[] check = new boolean[listItem.length];
        private ArrayList<Integer> selectedItem = new ArrayList<>();
        String item = "";

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.photo_list_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.photo);
            final Button btnStafInfo = (Button) view.findViewById(R.id.btnStaffInfo);

            imageView.setImageBitmap(imageBitmap);

            btnStafInfo.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddStaffActivity.this);
                    builder.setTitle(R.string.workerList);
                    builder.setMultiChoiceItems(listItem, check, new DialogInterface.OnMultiChoiceClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position, boolean isChecked){
                            if(isChecked){
                                if(!selectedItem.contains(position)){
                                    selectedItem.add(position);
                                }
                            }else if(selectedItem.contains(position)){
                                selectedItem.remove(Integer.valueOf(position));
                            }

                        }
                    });
                    // Set the action buttons
                    builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            item = listItem[selectedItem.get(0)] + "\n";
                            for(int i = 1; i< selectedItem.size(); i++){
                                if(i != selectedItem.size()-1){
                                    item = item + "\n";
                                }
                                item += listItem[selectedItem.get(i)];
                            }
                            btnStafInfo.setText(item);
                            btnStafInfo.setTextSize(15.0f);
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            if (item == "") {
                                btnStafInfo.setText("輸入員工資料");
                                btnStafInfo.setTextSize(24.0f);
                            } else {
                               // btnStafInfo.setText(item);
                              //  btnStafInfo.setTextSize(15.0f);
                            }
                        }
                    });
                    builder.setNeutralButton(R.string.clear_all, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            for(int i = 0; i< check.length; i++){
                                check[i] = false;
                            }
                            selectedItem.clear();
                            btnStafInfo.setText("輸入員工資料");
                            btnStafInfo.setTextSize(24.0f);
                        }
                    });
                    AlertDialog mDialog = builder.create();
                    mDialog.show();
                }
            });



            return view;
        }
    }
}