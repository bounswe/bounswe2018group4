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
    private int[] liked_users;

    @Expose
    private Time[] mentioned_time;

    @Expose
    private Comments[] comments;

    @Expose
    private int numcomments;

    @Expose
    private LocationType[] location;

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

    public int[] getLiked_users() {
        return liked_users;
    }

    public void setLiked_users(int[] liked_users) {
        this.liked_users = liked_users;
    }

    public Time[] getMentioned_time() {
        return mentioned_time;
    }

    public void setMentioned_time(Time[] mentioned_time) {
        this.mentioned_time = mentioned_time;
    }

    public Comments[] getComments(){return this.comments; }

    public void setComments(Comments[] cm){this.comments=cm; }

    public int getNumcomments(){return  this.numcomments; }

    public void setNumcomments(int num){this.numcomments=num; }

    public LocationType[] getLocation() {
        return location;
    }

    public void setLocation(LocationType[] location) {
        this.location = location;
    }
}