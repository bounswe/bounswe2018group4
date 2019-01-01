package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class ApiResultFollower {

    @Expose
    private int follower;

    @Expose
    private String username;

    public ApiResultFollower(int follower, String username) {
        this.follower = follower;
        this.username = username;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
