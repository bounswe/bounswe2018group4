package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class ApiResultFollowing {

    @Expose
    private int followed;

    @Expose
    private String username;

    public ApiResultFollowing(int followed, String username) {
        this.followed = followed;
        this.username = username;
    }

    public int getFollowed() {
        return followed;
    }

    public void setFollowed(int followed) {
        this.followed = followed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
