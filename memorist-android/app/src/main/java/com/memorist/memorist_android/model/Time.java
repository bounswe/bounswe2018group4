package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Time implements Serializable {

    @Expose
    private int id;

    @Expose
    private int data_type;

    @Expose
    private String date_format;

    @Expose
    private String date_string1;

    @Expose
    private String date_string2;

    public int getId() {
        return id;
    }

    public int getData_type() {
        return data_type;
    }

    public String getDate_format() {
        return date_format;
    }

    public String getDate_string1() {
        return date_string1;
    }

    public String getDate_string2() {
        return date_string2;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData_type(int data_type) {
        this.data_type = data_type;
    }

    public void setDate_format(String date_format) {
        this.date_format = date_format;
    }

    public void setDate_string1(String date_string1) {
        this.date_string1 = date_string1;
    }

    public void setDate_string2(String date_string2) {
        this.date_string2 = date_string2;
    }
}
