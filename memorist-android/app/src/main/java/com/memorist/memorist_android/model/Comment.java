package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class Comment {

    @Expose
    private int id;

    @Expose
    private int owner;

    @Expose
    private String posting_time;

    @Expose
    private String owner_name;

    @Expose
    private String comment;


    public Comment(int id, int owner, String posting_time, String owner_name, String comment) {
        this.id = id;
        this.owner = owner;
        this.posting_time = posting_time;
        this.owner_name = owner_name;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int ownerid) {
        this.owner = ownerid;
    }

    public String getPosting_time() {
        return posting_time;
    }

    public void setPosting_time(String posting_time) {
        this.posting_time = posting_time;
    }

    public String getOwnerName() {
        return owner_name;
    }

    public void setOwnerName(String cOwnerName) {
        this.owner_name = cOwnerName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String commentText) {
        this.comment = commentText;
    }
}