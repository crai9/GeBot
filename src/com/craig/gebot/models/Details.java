package com.craig.gebot.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details {

    @SerializedName("isSuffix")
    @Expose
    private String isSuffix;

    @SerializedName("recruiting")
    @Expose
    private String recruiting;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("clan")
    @Expose
    private String clan;

    @SerializedName("title")
    @Expose
    private String title;

    public String getIsSuffix() {
        return isSuffix;
    }

    public void setIsSuffix(String isSuffix) {
        this.isSuffix = isSuffix;
    }

    public String getRecruiting() {
        return recruiting;
    }

    public void setRecruiting(String recruiting) {
        this.recruiting = recruiting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClan() {
        return clan;
    }

    public void setClan(String clan) {
        this.clan = clan;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}