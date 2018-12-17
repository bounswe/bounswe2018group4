package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class Memory {

    @Expose
    private int id;

    @Expose
    private Owner owner;

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

    @Expose
    private Comments[] comments;

    @Expose
    private int numcomments;

    public Memory(int id, Owner owner, String posting_time, String title, Text[] texts, Multimedia[] multimedia,
                  Tag[] tags, int numlikes, Comments[] comments, int numcomments) {
        this.id = id;
        this.owner = owner;
        this.posting_time = posting_time;
        this.title = title;
        this.texts = texts;
        this.multimedia = multimedia;
        this.tags = tags;
        this.numlikes = numlikes;
        this.comments=comments;
        this.numcomments=numcomments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner ownerid) {
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

    public Comments[] getComments(){return this.comments; }

    public void setComments(Comments[] cm){this.comments=cm; }

    public int getNumcomments(){return  this.numcomments; }

    public void setNumcomments(int num){this.numcomments=num; }
    
}