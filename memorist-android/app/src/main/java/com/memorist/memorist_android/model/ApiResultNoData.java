package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class ApiResultNoData {

    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
