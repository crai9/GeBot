package com.craig.gebot.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.sql.Timestamp;

public class VOS {

    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("timestamp_ms")
    @Expose
    private long timestampMs;

    @SerializedName("id")
    @Expose
    private String id;

    /**
     *
     * @return
     * The text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     * The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The timestampMs
     */
    public long getTimestampMs() {
        return timestampMs;
    }

    /**
     *
     * @param timestampMs
     * The timestamp_ms
     */
    public void setTimestampMs(long timestampMs) {
        this.timestampMs = timestampMs;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

}