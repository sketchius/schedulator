package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.techelevator.model.Office;
import com.techelevator.model.Review;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.techelevator.model.User;


@Component
public class JdbcReviewDao implements ReviewDao {


    private final JdbcTemplate jdbcTemplate;

    public JdbcReviewDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Review> findAll() {
        List<Review> reviews = new ArrayList<>();
        String sql = "select * from review";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Review review = mapRowToReview(results);
            reviews.add(review);
        }

        return reviews;
    }

    @Override
    public List<Review> findAllByUserId(int userId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "select * from review where user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            Review review = mapRowToReview(results);
            reviews.add(review);
        }

        return reviews;
    }

    @Override
    public List<Review> findAllByDetailsId(int providerId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "select * from review where details_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, providerId);
        while (results.next()) {
            Review review = mapRowToReview(results);
            reviews.add(review);
        }

        return reviews;
    }

    @Override
    public Review getReviewById(int reviewId) {
        String sql = "SELECT * FROM review WHERE review_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reviewId);
        if (results.next()) {
            return mapRowToReview(results);
        } else {
            return null;
        }

    }


    @Override
    public Review getReviewByUserId(int detailsId) {
        String sql = "SELECT * FROM review WHERE details_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, detailsId);
        if (results.next()) {
            return mapRowToReview(results);
        } else {
            return null;
        }
    }

    @Override
    public Review findByReviewName(String reviewName) {
        if (reviewName == null) throw new IllegalArgumentException("Review Title cannot be null");

        for (Review review : this.findAll()) {
            if (review.getReviewTitle().equalsIgnoreCase(reviewName)) {
                return review;
            }
        }
        throw new UsernameNotFoundException(reviewName + " was not found...");
    }

    @Override
    public int findIdByReviewName(String reviewName) {
        if (reviewName == null) throw new IllegalArgumentException("Review Title cannot be null");

        int reviewId;
        try {
            reviewId = jdbcTemplate.queryForObject("select review_id from review where review_title = ?", int.class, reviewName);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException(reviewName + " was not found...");
        }

        return reviewId;
    }

    @Override
    public boolean create(String reviewTitle, String reviewDesc,  int rating, int userId, int detailsId) {
        /* How to create a review!
        You might notice it'll prompt you for four different properties in your create, what do I need you might ask!
        (1). You'll need to parse the subject and contents of the review into SEPARATE strings
        (2). Then you need to get a list of providers using 'findAllProviders'
        (3). use Principal to get user ID (Not implemented yet)
        (4). You need to select a provider, store the last name and use getProviderIdByLastName
        (5). Now that you've got your properties, input them into your create and BAM!!! You've got a brand-new review!!!!!
        v--------------------------------------------v
        Remove once principal is setup and working*/

     String sql = "INSERT INTO review (review_title, review_desc, rating, user_id, details_id) VALUES (?, ?, ?, ?, ?);";
     return jdbcTemplate.update(sql, reviewTitle, reviewDesc, rating, userId, detailsId ) == 1;
    }




    private Review mapRowToReview(SqlRowSet rs) {
        Review review = new Review();
        review.setReviewId(rs.getInt("review_id"));
        review.setReviewTitle(rs.getString("review_title"));
        review.setRating(rs.getInt("rating"));
        review.setReviewDesc(rs.getString("review_desc"));
        review.setUserId(rs.getInt("user_id"));
        review.setDetailsId(rs.getInt("details_id"));
        return review;
    }

}
