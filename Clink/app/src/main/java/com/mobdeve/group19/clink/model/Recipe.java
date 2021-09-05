package com.mobdeve.group19.clink.model;

import java.util.ArrayList;

public class Recipe {

    ArrayList<String> steps;
    ArrayList<String> reviews;
    ArrayList<ArrayList<String>> ingredients;

    String name;
    Integer prepTime;
    String author;

    public Recipe(ArrayList<String> steps, ArrayList<String> reviews, ArrayList<ArrayList<String>> ingredients, String name, Integer prepTime, String author) {
        this.steps = steps;
        this.reviews = reviews;
        this.ingredients = ingredients;
        this.name = name;
        this.prepTime = prepTime;
        this.author = author;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public ArrayList<ArrayList<String>> getIngredients() {
        return ingredients;
    }

    public String getName() {
        return name;
    }

    public Integer getPrepTime() {
        return prepTime;
    }

    public String getAuthor() {
        return author;
    }
}
