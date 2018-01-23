package com.cityu.ast.towngasproject;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cityu.ast.towngasproject.customAdapter.StartWorkListViewAdapter;

import java.util.ArrayList;

import static com.cityu.ast.towngasproject.customAdapter.StartWorkListViewAdapter.nameList;

/**
 * Created by tsang on 19/1/2018.
 */

public class WorkerListDialog extends Dialog{
    private ListView lv;
    private EditText et = null;
    private TextView list;
    private Button clearAll, confirm, cancel;
    private ArrayList<Integer> selectedItem = new ArrayList<>();
    private ArrayAdapter<String> adapter = null;
    private final static String[] listItem ={
            "16170 陳大文",
            "16171 陳二文",
            "16172 陳三文",
            "16173 陳田文",
            "16174 陳四文",
            "16175 陳叛文",
            "23570 cheung 大文",
            "32171 chan 二文",
            "36172 tsang三文",
            "53173 zhu 田文",
            "56174 lee 四文",
            "56175 au 叛文"
    };

    public static String staffList = "";


    public WorkerListDialog(final Context context,final TextView list, final int position) {
        super(context);
        /** Design the dialog in main.xml file */
        //final Dialog dialog = new Dialog(context);
        setContentView(R.layout.dialog_worker_select);
       // list = (TextView) view.findViewById(R.id.list);
        clearAll = (Button) findViewById(R.id.clear_all);
        confirm = (Button) findViewById(R.id.confirm);
        cancel = (Button) findViewById(R.id.cancel);
        et = (EditText) findViewById(R.id.et1);
        et.addTextChangedListener(filterTextWatcher);
        lv = (ListView) findViewById(R.id.lv1);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        adapter = new ArrayAdapter<String>(context, R.layout.add_staff_dialog_list_view, R.id.workerItem, listItem);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView ctv = (CheckedTextView) view.findViewById(R.id.workerItem);
                if(ctv.isChecked()){
                    if(!selectedItem.contains(position)){
                        selectedItem.add(position);
                        Toast.makeText(context, position+" added", Toast.LENGTH_SHORT).show();
                    }
                }else if(selectedItem.contains(position)){
                    selectedItem.remove(Integer.valueOf(position));
                    Toast.makeText(context, position+" removed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        clearAll.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                for(int i = 0; i< lv.getCount(); i++){
                    lv.setItemChecked(i, false);
                }
                selectedItem.clear();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                nameList.add(getShowItem());
                list.setText(nameList.get(position));

                StartWorkListViewAdapter.backup.clear();
                for(int i = 0;i<selectedItem.size();i++){
                    StartWorkListViewAdapter.backup.add(selectedItem.get(i));
                }
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                selectedItem.clear();
                for(int i = 0;i< lv.getChildCount();i++){
                    if(!StartWorkListViewAdapter.backup.contains(i)){
                        lv.setItemChecked(i, false);
                    }else if(StartWorkListViewAdapter.backup.contains(i)){
                        lv.setItemChecked(i, true);
                    }
                }
                for(int i = 0;i<StartWorkListViewAdapter.backup.size();i++){
                    selectedItem.add(StartWorkListViewAdapter.backup.get(i));
                }
                dismiss();
            }
        });

    }

    private TextWatcher filterTextWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            adapter.getFilter().filter(s);
        }
    };

    @Override
    public void onStop(){
        et.removeTextChangedListener(filterTextWatcher);
    }

    public String getShowItem(){
        String item = "";
        for(int i = 0; i< selectedItem.size(); i++){
            item = item + listItem[selectedItem.get(i)];
            staffList += "\n" + listItem[selectedItem.get(i)];
            if(i != selectedItem.size()-1){
                item = item + "\n";
            }
        }
        return "名單: \n" + item;
    }
}