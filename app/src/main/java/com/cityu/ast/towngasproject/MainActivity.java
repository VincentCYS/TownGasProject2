package com.cityu.ast.towngasproject;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Main entry point for the sample, showing a backpack and "Purchase" button.
 */
public class MainActivity extends Activity {
    Button b;
    TextView tv;
    EditText et;
    ProgressBar pg;
    String editText;
    String displayText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//Name Text control
        et = (EditText) findViewById(R.id.editText1);
        //Display Text control
        tv = (TextView) findViewById(R.id.tv_result);
        //Button to trigger web service invocation
        b = (Button) findViewById(R.id.button1);
        //Display progress bar until web service invocation completes
        pg = (ProgressBar) findViewById(R.id.progressBar1);
        //Button Click Listener
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Check if Name text control is not empty
                if (et.getText().length() != 0 && et.getText().toString() != "") {
                    //Get the text control value
                    editText = et.getText().toString();
                    //Create instance for AsyncCallWS
                    AsyncCallWS task = new AsyncCallWS();
                    //Call execute
                    task.execute();
                    //If text control is empty
                } else {
                    tv.setText("Please enter name");
                }
            }
        });
    }


    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            //Invoke webservice
            displayText = WebService.invokeHelloWorldWS(editText,"sayHello");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //Set response
            tv.setText(displayText);
            //Make ProgressBar invisible
            pg.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPreExecute() {
            //Make ProgressBar invisible
            pg.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }

    }
}
