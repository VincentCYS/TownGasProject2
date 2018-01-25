package com.cityu.ast.towngasproject;


import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.cityu.ast.towngasproject.Postgre.DbContract;
import com.cityu.ast.towngasproject.Postgre.PostgresHelper;

import org.postgresql.util.PSQLException;


import java.sql.*;

/**
 * Main entry point for the sample, showing a backpack and "Purchase" button.
 */
public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String driver = "org.postgresql.Driver";
        final String url = "jdbc:postgresql://isd03.ddns.net:5432/FYP";
        final String login = "isd03";
        final String password = "fyp@isd03";
        String sql = "SELECT * FROM public.user";



        try {
            Class.forName(driver);
            Connection connection
                    = DriverManager.getConnection(url, login, password);


            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
          //  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //    StrictMode.setThreadPolicy(policy);

            while (result.next()) {
               Toast.makeText(this, result.getString("name") + " " +
                       result.getString("group"), Toast.LENGTH_LONG).show();
            }
            result.close();
            statement.close();

            connection.close();
        } catch (SQLException e) {
            Log.d("error1", e.toString());
        } catch (ClassNotFoundException e) {
            Log.i("error2", e.toString());
        }
    }
}
