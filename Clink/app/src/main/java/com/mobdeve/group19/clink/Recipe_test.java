package com.mobdeve.group19.clink;

public class Recipe_test {
    private String name, time, ingredients, steps;
    private int imageId;

    public Recipe_test(int imageId, String name, String time, String ingredients, String steps) {
        this.imageId = imageId;
        this.name = name;
        this.time = time;
        this.ingredients = ingredients;
        this.steps = steps;
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

    public String getIngredients() {
        return ingredients;
    }

    public String getSteps() {
        return steps;
    }
}
