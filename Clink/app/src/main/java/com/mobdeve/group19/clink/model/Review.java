package com.mobdeve.group19.clink.model;

import com.google.gson.annotations.SerializedName;

public class Review {

    String _id;
    String userId;
    String body;
    public String recipeId;

    public Review(String reviewId, String review, String recipeId) {
        this.userId = reviewId;
        this.body = review;
        this.recipeId = recipeId;
    }

    public Review(String review) {
        this.userId = "";
        this.body = review;

    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setReview(String review) {
        this.body = review;
    }

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
