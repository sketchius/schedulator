package com.techelevator.dao;

import com.techelevator.model.Response;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcResponsesDao implements ResponsesDao {


    private final JdbcTemplate jdbcTemplate;

    public JdbcResponsesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Response> findAll() {
        List<Response> responses = new ArrayList<>();
        String sql = "select * from response";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Response review = mapRowToReview(results);
            responses.add(review);
        }

        return responses;
    }

    @Override
    public List<Response> findAllByUserId(int userId) {
        List<Response> responses = new ArrayList<>();
        String sql = "select * from response where user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            Response response = mapRowToReview(results);
            responses.add(response);
        }

        return responses;
    }

    @Override
    public List<Response> findAllByDetailsId(int providerId) {
        List<Response> responses = new ArrayList<>();
        String sql = "select * from response where details_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, providerId);
        while (results.next()) {
            Response response = mapRowToReview(results);
            responses.add(response);
        }

        return responses;
    }

    @Override
    public Response getResponseByReviewId(int reviewId) {
        String sql = "SELECT * FROM response WHERE review_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reviewId);
        if (results.next()) {
            return mapRowToReview(results);
        } else {
            return null;
        }

    }

    @Override
    public Response getResponseById(int responseId) {
        String sql = "SELECT * FROM response WHERE response_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, responseId);
        if (results.next()) {
            return mapRowToReview(results);
        } else {
            return null;
        }

    }


    @Override
    public Response getResponseByUserId(int userId) {
        String sql = "SELECT * FROM response WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()) {
            return mapRowToReview(results);
        } else {
            return null;
        }
    }

    @Override
    public Response findByResponseName(String responseName) {
        if (responseName == null) throw new IllegalArgumentException("Response Title cannot be null");

        for (Response response : this.findAll()) {
            if (response.getResponseTitle().equalsIgnoreCase(responseName)) {
                return response;
            }
        }
        throw new UsernameNotFoundException(responseName + " was not found...");
    }

    @Override
    public int findIdByResponseName(String responseName) {
        if (responseName == null) throw new IllegalArgumentException("Review Title cannot be null");

        int responseId;
        try {
            responseId = jdbcTemplate.queryForObject("select response_id from response where response_title = ?", int.class, responseName);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException(responseName + " was not found...");
        }

        return responseId;
    }

    @Override
    public boolean create(Response response) {

        String sql = "insert into response (review_id, response_desc, response_title, user_id, details_id) values (?, ?, '', ?, ?)";
        return jdbcTemplate.update(sql, response.getReviewId(), response.getResponseDesc(), response.getUserId(), response.getDetailsId()) == 1;
    }




    private Response mapRowToReview(SqlRowSet rs) {
        Response review = new Response();
        review.getResponseId(rs.getInt("response_id"));
        review.setReviewId(rs.getInt("review_id"));
        review.setResponseTitle(rs.getString("response_title"));
        review.setResponseDesc(rs.getString("response_desc"));
        review.setUserId(rs.getInt("user_id"));
        review.setDetailsId(rs.getInt("details_id"));
        return review;
    }

}
