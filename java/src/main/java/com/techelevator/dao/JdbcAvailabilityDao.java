package com.techelevator.dao;

import com.techelevator.model.Availability;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class JdbcAvailabilityDao implements AvailabilityDao{


    private final JdbcTemplate jdbcTemplate;

    public JdbcAvailabilityDao(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate;}


    @Override
    public Availability getAvailabilityByDetailsId(int detailsId) {
        String sql = "SELECT * FROM user_availability WHERE details_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, detailsId);
        if (results.next()) {
            return mapRowToAvailability(results);
        } else {
            return null;
        }
    }

    @Override
    public Availability getAvailabilityById(int id) {
        String sql = "SELECT * FROM user_availability WHERE availability_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            return mapRowToAvailability(results);
        } else {
            return null;
        }
    }

    @Override
    public boolean create(int detailsId, LocalDate availableFrom, LocalDate availableTo){
        String insertCreateSql = "INSERT INTO user_availability (details_id, available_from, available_to) VALUES (?, ?, ?);";
        return jdbcTemplate.update(insertCreateSql, detailsId, availableFrom, availableTo) == 1;
    }

    private Availability mapRowToAvailability(SqlRowSet rs) {
        Availability availability = new Availability();
        availability.setId(rs.getInt("availability_id"));
        availability.setDetailsId(rs.getInt("details_id"));
        availability.setAvailableFrom(rs.getDate("available_from").toLocalDate());
        availability.setAvailableTo(rs.getDate("available_to").toLocalDate());
        return availability;
    }
}
