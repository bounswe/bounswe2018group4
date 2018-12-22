package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class ApiResultFollower {

    @Expose
    private int follower;

    public ApiResultFollower(int follower) {
        this.follower = follower;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }
}
