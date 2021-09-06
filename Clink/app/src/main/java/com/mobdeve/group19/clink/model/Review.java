package com.mobdeve.group19.clink.model;

public class Review {

    String reviewId;
    String review;

    public Review(String reviewId, String review) {
        this.reviewId = reviewId;
        this.review = review;
    }

    public Review(String review) {
        this.reviewId = "";
        this.review = review;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReviewId() {
        return reviewId;
    }

    public String getReview() {
        return review;
    }
}
