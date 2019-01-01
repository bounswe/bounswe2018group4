package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class Target {

    @Expose
    private String t_type;

    @Expose
    private String source;

    @Expose
    private Selector[] selector;

    public Selector[] getSelector() {
        return selector;
    }

    public String getSource() {
        return source;
    }

    public String getT_type() {
        return t_type;
    }

    public void setSelector(Selector[] selector) {
        this.selector = selector;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setT_type(String t_type) {
        this.t_type = t_type;
    }
}
