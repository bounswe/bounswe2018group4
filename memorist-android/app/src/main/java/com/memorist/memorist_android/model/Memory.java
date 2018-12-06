package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class Memory {

    @Expose
    private int id;

    @Expose
    private String owner;

    @Expose
    private String posting_time;

    @Expose
    private String title;

    @Expose
    private Text[] texts;

    @Expose
    private Multimedia[] multimedia;

    @Expose
    private Tag[] tags;

    @Expose
    private int numlikes;

    public Memory(int id, String owner, String posting_time, String title, Text[] texts, Multimedia[] multimedia, Tag[] tags, int numlikes) {
        this.id = id;
        this.owner = owner;
        this.posting_time = posting_time;
        this.title = title;
        this.texts = texts;
        this.multimedia = multimedia;
        this.tags = tags;
        this.numlikes = numlikes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String ownerid) {
        this.owner = ownerid;
    }

    public String getPosting_time() {
        return posting_time;
    }

    public void setPosting_time(String posting_time) {
        this.posting_time = posting_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Multimedia[] getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(Multimedia[] multimedia) {
        this.multimedia = multimedia;
    }

    public Tag[] getTags() {
        return tags;
    }

    public void setTags(Tag[] tags) {
        this.tags = tags;
    }

    public int getNumlikes() {
        return numlikes;
    }

    public void setNumlikes(int numlikes) {
        this.numlikes = numlikes;
    }

    public Text[] getTexts() {
        return texts;
    }

    public void setTexts(Text[] texts) {
        this.texts = texts;
    }
}