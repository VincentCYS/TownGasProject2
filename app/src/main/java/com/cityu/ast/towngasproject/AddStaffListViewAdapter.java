package com.cityu.ast.towngasproject;

/**
 * Created by vince on 17/1/2018.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddStaffListViewAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final Bitmap values;

    public AddStaffListViewAdapter(Context context, Bitmap values) {
        super(context, -1);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.photo_list_item, parent, false);

        // EditText edittxtComment =(EditText) rowView.findViewById(R.id.editTxtComment);
        Button btnStaffInfo = (Button) rowView.findViewById(R.id.btnStaffInfo);
        Button btnDelete = (Button) rowView.findViewById(R.id.btnDelete);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.photo);

        imageView.setImageBitmap(values);

        return rowView;
    }
}