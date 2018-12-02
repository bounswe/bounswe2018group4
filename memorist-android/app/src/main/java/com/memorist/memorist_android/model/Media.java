package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class Media {

    @Expose
    private String media;

    public Media(String media) {
        this.media = media;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }
}
