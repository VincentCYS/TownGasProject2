package com.cityu.ast.towngasproject.Postgre;

/**
 * Created by vince on 24/1/2018.
 */

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.cityu.ast.towngasproject.Postgre.DbContract;

public class TestConnection {


    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager.getConnection(
                    DbContract.HOST+DbContract.DB_NAME,
                    DbContract.USERNAME,
                    DbContract.PASSWORD);

            Log.i("success", "DB connected");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

}