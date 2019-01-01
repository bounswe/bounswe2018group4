package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class Annotation {

    @Expose
    private String context;

    @Expose
    private String id;

    @Expose
    private String a_type;

    @Expose
    private String motivation;

    @Expose
    private Creator[] creator;

    @Expose
    private String created;

    @Expose
    private Body[] body;

    @Expose
    private Target[] target;

    public Body[] getBody() {
        return body;
    }

    public Creator[] getCreator() {
        return creator;
    }

    public String getA_type() {
        return a_type;
    }

    public String getContext() {
        return context;
    }

    public String getCreated() {
        return created;
    }

    public String getId() {
        return id;
    }

    public String getMotivation() {
        return motivation;
    }

    public Target[] getTarget() {
        return target;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setA_type(String a_type) {
        this.a_type = a_type;
    }

    public void setBody(Body[] body) {
        this.body = body;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setCreator(Creator[] creator) {
        this.creator = creator;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public void setTarget(Target[] target) {
        this.target = target;
    }
}
