package com.mobdeve.group19.clink.model;

import java.util.ArrayList;

public class Recipe {

    ArrayList<String> steps;
    ArrayList<Review> reviews;
    ArrayList<Ingredients> ingredients;

    Review review;

    String name;
    Integer prepTime;
    String author;
    String recipeId;

    String message;

    public Recipe(ArrayList<String> steps, ArrayList<Review> reviews, ArrayList<Ingredients> ingredients, String name, Integer prepTime, String author, String recipeId) {
        this.steps = steps;
        this.reviews = reviews;
        this.ingredients = ingredients;
        this.name = name;
        this.prepTime = prepTime;
        this.author = author;
        this.recipeId = recipeId;
    }

    public Recipe(ArrayList<String> steps, ArrayList<Ingredients> ingredients, String name, Integer prepTime, String author, String recipeId, String message) {
        this.steps = steps;
        this.reviews = new ArrayList<>();
        this.ingredients = ingredients;
        this.name = name;
        this.prepTime = prepTime;
        this.author = author;
        this.recipeId = recipeId;
        this.message = message;
    }

    public Recipe(Review review, String recipeId) {
        this.review.reviewId = review.getReviewId();
        this.review.review = review.getReview();
        this.recipeId = recipeId;
    }

    public Recipe(String recipeId) {
        this.recipeId = recipeId;
    }

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
        return recipeId;
    }

    public String getMessage() {
        return this.message;
    }

}
