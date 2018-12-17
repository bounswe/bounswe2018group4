package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class Comments {

    @Expose
    private int id;

    @Expose
    private String comment;

    @Expose
    private Owner owner;

    @Expose
    private String commentTime;

    public Comments(int id, String comment, Owner owner, String comment_Time) {
        this.id = id;
        this.comment=comment;
        this.owner = owner;
        this.commentTime=comment_Time;
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
        return commentTime;
    }

    public void setCommentTime(String comment_Time) {
        this.commentTime = comment_Time;
    }

}