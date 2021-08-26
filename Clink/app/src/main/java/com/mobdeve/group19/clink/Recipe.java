package com.mobdeve.group19.clink;

public class Recipe {
    private String name, time;
    private int imageId;

    public Recipe(int imageId, String name, String time) {
        this.imageId = imageId;
        this.name = name;
        this.time = time;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
