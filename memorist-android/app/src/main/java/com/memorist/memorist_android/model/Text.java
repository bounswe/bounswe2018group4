package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Text implements Serializable {

    @Expose
    private int id;

    @Expose
    private String text;

    @Expose
    private int order;

    @Expose
    private int memory;

    public Text(int id, String text, int order, int memory) {
        this.id = id;
        this.text = text;
        this.order = order;
        this.memory = memory;
    }

    public int getId() {
        return id;
    }

    public int getMemory() {
        return memory;
    }

    public int getOrder() {
        return order;
    }

    public String getText() {
        return text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setText(String text) {
        this.text = text;
    }
}
