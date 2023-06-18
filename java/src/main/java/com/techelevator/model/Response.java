package com.techelevator.model;

public class Response {

    private int id;
    private int reviewId;
    private String responseTitle;
    private String responseDesc;
    private int userId;
    private int detailsId;

    public Response() { }

    public Response(int id, int reviewId, String responseTitle, String responseDesc, int userId, int detailsId) {
        this.id = id;
        this.reviewId = reviewId;
        this.responseTitle = responseTitle;
        this.responseDesc = responseDesc;
        this.userId = userId;
        this.detailsId = detailsId;
    }

    public int getResponseId(int id) {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getResponseTitle() {
        return responseTitle;
    }

    public void setResponseTitle(String responseTitle) {
        this.responseTitle = responseTitle;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(int detailsId) {
        this.detailsId = detailsId;
    }

}
