package com.us.craig.gebot.models;

/**
 * Created by craig on 08/10/2015.
 */

public class Item {

    private String name;
    private String icon;
    private String icon_large;
    private int id;
    private String type;
    private String typeIcon;
    private String description;
    private String members;
    private Current current;
    private Today today;
    private Day180 day180;
    private Day90 day90;
    private Day30 day30;

    public Day180 getDay180() {
        return day180;
    }

    public void setDay180(Day180 day180) {
        this.day180 = day180;
    }

    public Day90 getDay90() {
        return day90;
    }

    public void setDay90(Day90 day90) {
        this.day90 = day90;
    }

    public Day30 getDay30() {
        return day30;
    }

    public void setDay30(Day30 day30) {
        this.day30 = day30;
    }

    public Today getToday() {
        return today;
    }

    public void setToday(Today today) {
        this.today = today;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon_large() {
        return icon_large;
    }

    public void setIcon_large(String icon_large) {
        this.icon_large = icon_large;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeIcon() {
        return typeIcon;
    }

    public void setTypeIcon(String typeIcon) {
        this.typeIcon = typeIcon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
