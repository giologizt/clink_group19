package com.mobdeve.group19.clink.model;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class Recipe {

    ArrayList<String> steps;
    ArrayList<Review> reviews;
    ArrayList<Ingredients> ingredients;

    Review review;

    File file;
    URI imageFile;

    String name;
    Integer prepTime;
    String author;
    String _id;

    String image;

    String message;

    //Constructors
    public Recipe(ArrayList<String> steps, ArrayList<Review> reviews, ArrayList<Ingredients> ingredients, String name, Integer prepTime, String author, String id, String image) {
        this.steps = steps;
        this.reviews = reviews;
        this.ingredients = ingredients;
        this.name = name;
        this.prepTime = prepTime;
        this.author = author;
        this._id = id;
        this.image = image;

        this.file = new File(image);
        this.imageFile = file.toURI();
    }

    public Recipe(ArrayList<String> steps, ArrayList<Ingredients> ingredients, String name, Integer prepTime, String author, String recipeId, String message, String image) {
        this.steps = steps;
        this.reviews = new ArrayList<>();
        this.ingredients = ingredients;
        this.name = name;
        this.prepTime = prepTime;
        this.author = author;
        this._id = recipeId;
        this.message = message;

        this.image = image;

        this.file = new File(image);
        this.imageFile = file.toURI();
    }

    public Recipe(ArrayList<String> steps, ArrayList<Ingredients> ingredients, String name, Integer prepTime, String _id) {
        this.steps = steps;
        this.ingredients = ingredients;
        this.name = name;
        this.prepTime = prepTime;
        this._id = _id;
    }

    public Recipe(Review review, String recipeId) {
        this.reviews.add(review);
        this._id = recipeId;
    }

    public Recipe(String recipeId) {
        this._id = recipeId;
    }

    //Getters
    public ArrayList<String> getSteps() {
        return steps;
    }
    public ArrayList<Review> getReviews() {
        return reviews;
    }
    public ArrayList<Ingredients> getIngredients() {
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
    public String getRecipeId() {
        return _id;
    }
    public String getMessage() {
        return this.message;
    }
    public String getImage() {
        return  this.image;
    }
    public URI getImageFile() {
        return imageFile;
    }
}
