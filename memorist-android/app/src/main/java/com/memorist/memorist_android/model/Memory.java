package com.memorist.memorist_android.model;

public class Memory {

    private long memoryId;
    private User memoryOwner;
    private String postedTime;
    private String mentionedTime;
    private String location;
    private String story;

    public Memory(long memoryId, User memoryOwner, String postedTime, String mentionedTime, String location, String story) {
        this.memoryId = memoryId;
        this.memoryOwner = memoryOwner;
        this.postedTime = postedTime;
        this.mentionedTime = mentionedTime;
        this.location = location;
        this.story = story;
    }

    public long getMemoryId() {
        return memoryId;
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

    public String getStory() {
        return story;
    }

    public User getMemoryOwner() {
        return memoryOwner;
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

    public void setStory(String story) {
        this.story = story;
    }
}