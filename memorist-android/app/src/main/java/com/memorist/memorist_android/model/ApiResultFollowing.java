package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class ApiResultFollowing {

    @Expose
    private int id;

    @Expose
    private String username;

    public ApiResultFollowing(int followed, String username) {
        this.id = followed;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int followed) {
        this.id = followed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
