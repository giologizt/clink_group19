package com.mobdeve.group19.clink.model;

import com.google.gson.annotations.SerializedName;

public class Review {

    String _id;
    String userId;
    String body;
    public String recipeId;

    //Constructor
    public Review(String userId, String review, String recipeId) {
        this.userId = userId;
        this.body = review;
        this.recipeId = recipeId;
    }

    public Review(String reviewId, String review, String recipeId, String userId) {
        this._id = reviewId;
        this.body = review;
        this.recipeId = recipeId;
        this.userId = userId;
    }

    public Review(String review) {
        this.userId = "";
        this.body = review;
    }

    //Setters
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setReview(String review) {
        this.body = review;
    }

    //Getters
    public String getUserId() {
        return userId;
    }
    public String getReview() {
        return body;
    }
    public String getId() {
        return _id;
    }
    public String getRecipeId() {
        return recipeId;
    }
}
