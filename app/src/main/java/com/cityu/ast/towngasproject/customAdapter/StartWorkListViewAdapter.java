package com.cityu.ast.towngasproject.customAdapter;

/**
 * Created by vince on 17/1/2018.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cityu.ast.towngasproject.R;
import com.cityu.ast.towngasproject.StartWorkActivity;
import com.cityu.ast.towngasproject.WorkerListDialog;

import java.util.ArrayList;
import java.util.List;

public class StartWorkListViewAdapter extends BaseAdapter implements ListAdapter {
    public static ArrayList<Bitmap> list = new ArrayList<Bitmap>();
    public static ArrayList<Integer> backup = new ArrayList<>();
    public static ArrayList<String> nameList = new ArrayList<>();
    private Context context;

    public StartWorkListViewAdapter(Context context) {
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
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.start_work_list_item, null);
        }

        final TextView listText = (TextView) view.findViewById(R.id.list);
        final ImageView imageView = (ImageView) view.findViewById(R.id.startPhoto);
        final Button btnStaffInfo = (Button) view.findViewById(R.id.btnStartStaffInfo);
        final Button btnDelete = (Button) view.findViewById(R.id.btnStartDelete);
        final ImageView bigPic = (ImageView) parent.getRootView().findViewById(R.id.expanded_image);

        // Display the image that taken by the camera
        imageView.setImageBitmap(list.get(position));


        imageView.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        bigPic.setImageBitmap(list.get(position));
                        bigPic.setVisibility(View.VISIBLE);
                        return false;
                    }
                }
        );

        imageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bigPic.setVisibility(View.GONE);
                    }
                }
        );

        final WorkerListDialog workerListDialog = new WorkerListDialog(view.getContext(), listText);


        btnStaffInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                workerListDialog.show();
            }
        });


        // Delete a single add_staff_dialog_list_view of record
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

                                ((StartWorkActivity) context).finish();
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