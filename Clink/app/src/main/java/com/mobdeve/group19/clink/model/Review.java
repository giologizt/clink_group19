package com.mobdeve.group19.clink.model;

public class Review {

    String userId;
    String body;

    public Review(String reviewId, String review) {
        this.userId = reviewId;
        this.body = review;
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
}
