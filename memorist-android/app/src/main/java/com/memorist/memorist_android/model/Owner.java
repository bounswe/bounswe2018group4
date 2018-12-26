package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class Owner {

    @Expose
    private int id;

    @Expose
    private String username;

    @Expose
    private String photo;

    public Owner(int id, String username, String photo) {
        this.id = id;
        this.username = username;
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
