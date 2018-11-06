package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * User data contains the user's mail.
 */
public class User extends RealmObject {

    @PrimaryKey
    @Expose
    private long id;

    @Expose
    private String username;

    @Expose
    private String first_name;

    @Expose
    private String last_name;

    @Expose
    private String email;

    @Expose
    private boolean activeEmail_status;

    public User() {
        // Requires empty constructor...
    }

    public User(long id, String username, String first_name, String last_name, String email, boolean activeEmail_status) {
        this.id = id;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.activeEmail_status = activeEmail_status;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getActiveEmail_status() {
        return this.activeEmail_status;
    }

    public void setActiveEmail_status(boolean activeEmail_status) {
        this.activeEmail_status = activeEmail_status;
    }
}
