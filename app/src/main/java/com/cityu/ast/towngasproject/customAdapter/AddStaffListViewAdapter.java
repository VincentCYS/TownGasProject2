package com.cityu.ast.towngasproject.customAdapter;

/**
 * Created by vince on 17/1/2018.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.cityu.ast.towngasproject.R;

import java.util.ArrayList;

import static com.cityu.ast.towngasproject.CameraActivity.imageBitmap;

public class AddStaffListViewAdapter extends BaseAdapter implements ListAdapter {
    public static ArrayList<Bitmap> list = new ArrayList<Bitmap>();
    private Context context;
    private String[] listItem = {
            "16170 陳大文",
            "16171 陳二文",
            "16172 陳三文",
            "16173 陳田文",
            "16174 陳四文",
            "16175 陳叛文"
    };
    private boolean[] check = new boolean[listItem.length];
    private boolean[] copy_check = new boolean[listItem.length];

    private ArrayList<Integer> selectedItem = new ArrayList<>();
    String item = "";


    public AddStaffListViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.photo_list_item, null);
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.photo);
        final Button btnStaffInfo = (Button) view.findViewById(R.id.btnStaffInfo);
        final Button btnDelete = (Button) view.findViewById(R.id.btnDelete);

        // Display the image that taken by the camera
        imageView.setImageBitmap(list.get(position));


        btnStaffInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
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
                        item = listItem[selectedItem.get(0)];
                        for(int i = 1; i< selectedItem.size(); i++){

                            item += "\n" + listItem[selectedItem.get(i)];
                        }

                        for(int i = 0 ; i < check.length; i++)
                            copy_check[i] = check[i];

                        btnStaffInfo.setText(item);
                        btnStaffInfo.setTextSize(15.0f);
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if (item == "") {
                            btnStaffInfo.setText("輸入員工資料");
                            btnStaffInfo.setTextSize(24.0f);
                        } else {
                            // btnStafInfo.setText(item);
                            //  btnStafInfo.setTextSize(15.0f);
                        }

                        for(int i = 0 ; i < check.length; i++)
                            check[i] = copy_check[i];
                    }
                });
                builder.setNeutralButton(R.string.clear_all, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        for(int i = 0; i< check.length; i++){
                            check[i] = false;
                        }
                        selectedItem.clear();
                        btnStaffInfo.setText("輸入員工資料");
                        btnStaffInfo.setTextSize(24.0f);

                        for(int i = 0 ; i < check.length; i++){
                            check[i] = false;
                            copy_check[i] = check[i];
                        }
                    }
                });
                AlertDialog mDialog = builder.create();
                mDialog.show();
            }
        });


        // Delete a single row of record
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(view.getContext());
                builder1.setMessage("刪除?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "確定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Remove the record from the list
                                list.remove(position);
                                notifyDataSetChanged();     // Refresh the listview
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                android.app.AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        return view;
    }
}