package com.cityu.ast.towngasproject;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Main2Test extends AppCompatActivity {
    private Button btn;
    private TextView tv;
    private boolean[] check;
    private String[] listItem ={
            "16170 陳大文",
            "16171 陳二文",
            "16172 陳三文",
            "16173 陳田文",
            "16174 陳四文",
            "16175 陳叛文"
    };
    private ArrayList<Integer> selectedItem = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_test);
        tv = (TextView)findViewById(R.id.tv1);
        check = new boolean[listItem.length];
        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(Main2Test.this);
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
                        String item = "";
                        for(int i = 0; i< selectedItem.size(); i++){
                            item = item + listItem[selectedItem.get(i)];
                            if(i != selectedItem.size()-1){
                                item = item + "";
                            }
                        }
                        tv.setText(item);
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });
                builder.setNeutralButton(R.string.clear_all, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        for(int i = 0; i< check.length; i++){
                            check[i] = false;
                        }
                        selectedItem.clear();
                        tv.setText("");
                    }
                });
                AlertDialog mDialog = builder.create();
                mDialog.show();
            }
        });
    }

}