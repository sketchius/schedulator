package com.techelevator.dao;

import com.techelevator.model.Response;

import java.util.List;

public interface ResponsesDao {

    List<Response> findAll();

    List<Response> findAllByUserId(int userId);

    List<Response> findAllByDetailsId(int detailsID);

    Response getResponseByReviewId(int reviewId);

    Response getResponseById(int responseId);

    Response getResponseByUserId(int userId);

    Response findByResponseName(String reviewName);

    int findIdByResponseName(String reviewName);

    boolean create(Response response);
}
