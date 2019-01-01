package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class Creator {

    @Expose
    private String c_id;

    @Expose
    private String c_type;

    @Expose
    private String name;

    @Expose
    private String nickname;

    public String getC_id() {
        return c_id;
    }

    public String getC_type() {
        return c_type;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public void setC_type(String c_type) {
        this.c_type = c_type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
