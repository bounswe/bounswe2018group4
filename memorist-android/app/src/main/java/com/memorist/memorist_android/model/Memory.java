package com.memorist.memorist_android.model;

import android.net.Uri;

import java.util.ArrayList;

public class Memory {

    private long memoryId;
    private User memoryOwner;
    private String postedTime;
    private String mentionedTime;
    private String location;
    private String title;

    private boolean isLiked;

    private ArrayList<String> memoryFormat;
    private ArrayList<String> memoryText;
    private ArrayList<Uri> memoryImage;
    private ArrayList<Uri> memoryVideo;
    private ArrayList<Uri> memoryAudio;
    private ArrayList<String> memoryTags;

    public Memory(long memoryId, User memoryOwner, String postedTime, String mentionedTime, String location, String title,
                  ArrayList<String> memoryFormat, ArrayList<String> memoryText, ArrayList<Uri> memoryImage,
                  ArrayList<Uri> memoryVideo, ArrayList<Uri> memoryAudio, ArrayList<String> memoryTags) {
        this.memoryId = memoryId;
        this.memoryOwner = memoryOwner;
        this.postedTime = postedTime;
        this.mentionedTime = mentionedTime;
        this.location = location;
        this.title = title;
        this.memoryFormat = memoryFormat;
        this.memoryText = memoryText;
        this.memoryImage = memoryImage;
        this.memoryVideo = memoryVideo;
        this.memoryAudio = memoryAudio;
        this.memoryTags = memoryTags;
        this.isLiked = false;
    }

    public long getMemoryId() {
        return memoryId;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public String getLocation() {
        return location;
    }

    public String getMentionedTime() {
        return mentionedTime;
    }

    public String getPostedTime() {
        return postedTime;
    }

    public User getMemoryOwner() {
        return memoryOwner;
    }

    public ArrayList<String> getMemoryFormat() {
        return memoryFormat;
    }

    public ArrayList<String> getMemoryText() {
        return memoryText;
    }

    public ArrayList<Uri> getMemoryImage() {
        return memoryImage;
    }

    public ArrayList<Uri> getMemoryVideo() {
        return memoryVideo;
    }

    public ArrayList<Uri> getMemoryAudio() {
        return memoryAudio;
    }

    public ArrayList<String> getMemoryTags() {
        return memoryTags;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMemoryId(long memoryId) {
        this.memoryId = memoryId;
    }

    public void setMemoryOwner(User memoryOwner) {
        this.memoryOwner = memoryOwner;
    }

    public void setMentionedTime(String mentionedTime) {
        this.mentionedTime = mentionedTime;
    }

    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }

    public void setMemoryFormat(ArrayList<String> memoryFormat) {
        this.memoryFormat = memoryFormat;
    }

    public void setMemoryImage(ArrayList<Uri> memoryImage) {
        this.memoryImage = memoryImage;
    }

    public void setMemoryText(ArrayList<String> memoryText) {
        this.memoryText = memoryText;
    }

    public void setMemoryVideo(ArrayList<Uri> memoryVideo) {
        this.memoryVideo = memoryVideo;
    }

    public void setMemoryAudio(ArrayList<Uri> memoryAudio) {
        this.memoryAudio = memoryAudio;
    }

    public void setMemoryTags(ArrayList<String> memoryTags) {
        this.memoryTags = memoryTags;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}