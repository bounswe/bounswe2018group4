package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class ApiResultLike {

    @Expose
    private int like;

    public ApiResultLike(int like) {
        this.like = like;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
