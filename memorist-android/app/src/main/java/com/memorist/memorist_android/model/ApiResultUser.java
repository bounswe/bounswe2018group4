package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

/**
 * This model will be used when api returns an object as result.
 * <T> is the data object model.
 */
public class ApiResultUser {

    @Expose
    private String token;

    @Expose
    private User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
