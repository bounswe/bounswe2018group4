package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class Tag {

    @Expose
    private int id;

    @Expose
    private String tag;

    @Expose
    private int memory;

    public Tag(int id, String tag, int memory) {
        this.id = id;
        this.tag = tag;
        this.memory = memory;
    }
}
