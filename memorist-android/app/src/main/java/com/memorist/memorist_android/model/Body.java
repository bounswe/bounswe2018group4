package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class Body {

    @Expose
    private String b_type;

    @Expose
    private String value;

    public String getB_type() {
        return b_type;
    }

    public String getValue() {
        return value;
    }

    public void setB_type(String b_type) {
        this.b_type = b_type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Body(String b_type, String value) {
        this.b_type = b_type;
        this.value = value;
    }
}
