package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class Selector {

    @Expose
    private String s_type;

    @Expose
    private String start;

    @Expose
    private String end;

    public String getEnd() {
        return end;
    }

    public String getS_type() {
        return s_type;
    }

    public String getStart() {
        return start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setS_type(String s_type) {
        this.s_type = s_type;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
