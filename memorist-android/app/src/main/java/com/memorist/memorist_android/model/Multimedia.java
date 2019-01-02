package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Multimedia implements Serializable {

    @Expose
    private int id;

    @Expose
    private int media_type;

    @Expose
    private int order;

    @Expose
    private int memory;

    @Expose
    private Media multimedia;

    public Multimedia(int id, int media_type, int order, int memory, Media multimedia) {
        this.id = id;
        this.media_type = media_type;
        this.order = order;
        this.memory = memory;
        this.multimedia = multimedia;
    }

    public int getId() {
        return id;
    }

    public int getMedia_type() {
        return media_type;
    }

    public int getOrder() {
        return order;
    }

    public int getMemory() {
        return memory;
    }

    public Media getMultimedia() {
        return multimedia;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMedia_type(int media_type) {
        this.media_type = media_type;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public void setMultimedia(Media multimedia) {
        this.multimedia = multimedia;
    }
}
