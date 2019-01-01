package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

/**
 * This model will be used when api returns an object as result.
 * <T> is the data object model.
 */
public class ApiResultMediaUpload {

    @Expose
    private int id;

    @Expose
    private String media;

    @Expose
    private int media_type;

    public int getId() {
        return id;
    }

    public String getMedia() {
        return media;
    }

    public int getMedia_type() {
        return media_type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public void setMedia_type(int media_type) {
        this.media_type = media_type;
    }
}