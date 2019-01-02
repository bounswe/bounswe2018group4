package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class ApiResultNoData {

    @Expose
    private String status;

    @Expose
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
