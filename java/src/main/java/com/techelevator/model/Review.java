package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Review {

    private int reviewId;
    private int detailsId;
    private int userId;
    private int rating;
    private String reviewTitle;
    private String reviewDesc;

    public Review(int reviewId, int detailsId, int userId, int rating, String reviewTitle, String reviewDesc) {
        this.reviewId = reviewId;
        this.detailsId = detailsId;
        this.userId = userId;
        this.rating = rating;
        this.reviewTitle = reviewTitle;
        this.reviewDesc = reviewDesc;
    }

    public Review() {

    }


    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(int detailsId) {
        this.detailsId = detailsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewDesc() {
        return reviewDesc;
    }

    public void setReviewDesc(String reviewDesc) {
        this.reviewDesc = reviewDesc;
    }
}

