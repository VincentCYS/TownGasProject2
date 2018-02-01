package com.cityu.ast.towngasproject;

/**
 * Created by vince on 31/1/2018.
 */

public class StaffList {
    private String [] name;
    private String [] userId;


    public StaffList(String[] name, String[] userId) {
        this.name = name;
        this.userId = userId;
    }

    public String[] getName() {
        return name;
    }

    public String[] getUserId() {
        return userId;
    }

}
