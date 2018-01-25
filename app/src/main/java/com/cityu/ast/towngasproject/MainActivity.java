//package com.cityu.ast.towngasproject;
//
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.os.StrictMode;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.cityu.ast.towngasproject.Postgre.DbContract;
//import com.cityu.ast.towngasproject.Postgre.PostgresHelper;
//
//import org.postgresql.util.PSQLException;
//
//
//import java.sql.*;
//
///**
// * Main entry point for the sample, showing a backpack and "Purchase" button.
// */
//public class MainActivity extends Activity {
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {Bundle
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        PostgresHelper client = new PostgresHelper(
//                DbContract.HOST,
//                DbContract.DB_NAME,
//                DbContract.USERNAME,
//                DbContract.PASSWORD);
//
//        try {
//            if (client.connect()) {
//                Log.i("s", "DB connected");
//
//                ResultSet rs = client.execQuery("SELECT * FROM public.user");
//
//                while(rs.next()) {
//
//                    System.out.printf("%d\t%s\t%s\t%d\n",
//                            rs.getInt(1),
//                            rs.getString(2),
//                            rs.getString(3),
//                            rs.getInt(4));
//                }
//            }
//
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//
//
//
//    }
//}
