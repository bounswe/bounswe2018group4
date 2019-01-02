package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Comments implements Serializable {

    @Expose
    private int id;

    @Expose
    private String comment;

    @Expose
    private Owner owner;

    @Expose
    private String comment_time;

    public Comments(int id, String comment, Owner owner, String comment_time) {
        this.id = id;
        this.comment=comment;
        this.owner = owner;
        this.comment_time=comment_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String commentText) {
        this.comment = commentText;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner set_Owner) {
        this.owner = set_Owner;
    }

    public String getCommentTime() {
        return comment_time;
    }

    public void setCommentTime(String comment_time) {
        this.comment_time = comment_time;
    }

}