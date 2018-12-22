package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class ApiResultFollowing {

    @Expose
    private int followed;

    public ApiResultFollowing(int followed) {
        this.followed = followed;
    }

    public int getFollowed() {
        return followed;
    }

    public void setFollowed(int followed) {
        this.followed = followed;
    }
}
