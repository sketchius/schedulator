package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import com.techelevator.model.Office;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JdbcOfficeDao implements OfficeDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcOfficeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByAddress(String address) {
        if (address == null) throw new IllegalArgumentException("Address cannot be null");

        int officeId;
        try {
            officeId = jdbcTemplate.queryForObject("select office_id from office where office_address = ?;", int.class, address);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("No office was found at " + address);
        }

        return officeId;
    }


    @Override
    public Office getOfficeById(int officeId) {
        String sql = "SELECT * FROM office WHERE office_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, officeId);
        if (results.next()) {
            return mapRowToOffice(results);
        } else {
            return null;
        }
    }
    @Override
    public List<Office> getOfficesByLocation(int officeId) {
        String sql = "SELECT * FROM office WHERE office_name = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, officeId);
        if (results.next()) {
            return (List<Office>) mapRowToOffice(results);
        } else {
            return null;
        }
    }

    @Override
    public List<Office> findAll() {
        List<Office> offices= new ArrayList<>();
        String sql = "select * from office";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Office office = mapRowToOffice(results);
            offices.add(office);
        }

        return offices;
    }

    @Override
    public Office findByAddress(String address) {
        if (address == null) throw new IllegalArgumentException("Address cannot be null");

        for (Office office : this.findAll()) {
            if (office.getAddress().equalsIgnoreCase(address)) {
                return office;
            }
        }
        throw new UsernameNotFoundException("No office was found at " + address);
    }

    @Override
    public void create(Office office) {
        try {
            String sql = "INSERT INTO public.office(\n" +
                    "\toffice_name, office_address, office_city_name, office_state_acronym, office_phone_number, office_open_time, office_close_time)\n" +
                    "\tVALUES ( ?, ?, ?, ?, ?, ?, ?);";
            jdbcTemplate.queryForObject(sql, Integer.class, office.getName(), office.getAddress(),
                    office.getCityName(), office.getStateAcronym(), office.getPhoneNumber(),
                    office.getOpenTime(), office.getCloseTime());
        } catch (Exception e) {
            System.out.println(e.getMessage());}

    }

    @Override
    public void update(Office office) {
        try {
            String sql = "UPDATE public.office\n" +
                    "\tSET office_name=?, office_address=?, office_city_name=?, office_state_acronym=?, office_phone_number=?, office_open_time=?, office_close_time=?\n" +
                    "\tWHERE office_id =?;";
            jdbcTemplate.update(sql, office.getName(), office.getAddress(), office.getCityName(),
                    office.getStateAcronym(), office.getPhoneNumber(), office.getOpenTime(), office.getCloseTime(), office.getId());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    @Override
    public void updateOffice(String principleUsername, Office office) {
        try {
            String sql = "UPDATE office\n" +
                    "SET office_address = ?, \n" +
                    "    office_city_name = ?, \n" +
                    "    office_state_acronym = ?, \n" +
                    "    office_phone_number = ?, \n" +
                    "    office_open_time = ?, \n" +
                    "    office_close_time = ? \n" +
                    "FROM details\n" +
                    "JOIN users ON details.user_id = users.user_id\n" +
                    "WHERE office.office_id = details.office_id AND users.username = ?;";
            jdbcTemplate.update(sql, office.getAddress(), office.getCityName(),
                    office.getStateAcronym(), office.getPhoneNumber(), office.getOpenTime(),
                    office.getCloseTime(),principleUsername);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try{
            String sql = "DELETE FROM public.office\n" +
                    "\tWHERE office_id =?;";
            jdbcTemplate.update(sql, id);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Office getOfficeByDetailsId(int detailsId) {
        String sql = "SELECT o.office_id, o.office_name, o.office_address, o.office_city_name, o.office_state_acronym, o.office_phone_number, o.office_open_time, o.office_close_time\n" +
                "FROM office o\n" +
                "JOIN office_users ou ON o.office_id = ou.office_id\n" +
                "JOIN details d ON ou.details_id = d.details_id\n" +
                "WHERE d.details_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, detailsId);
        if (results.next()) {
            return mapRowToOffice(results);
        } else {
            return null;
        }
    }

    @Override
    public Office getOfficeByUser(String principleUsername) {
        String sql = "SELECT * FROM office\n" +
                "             \n" +
                "                INNER JOIN details ON office.office_id = details.office_id\n" +
                "                INNER JOIN users ON details.user_id = users.user_id\n" +
                "                WHERE username = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, principleUsername);
        if (results.next()) {
            return mapRowToOffice(results);
        } else {
            return null;
        }
    }


    private Office mapRowToOffice(SqlRowSet rs) {
        Office office = new Office();
        office.setId(rs.getInt("office_id"));
        office.setName(rs.getString("office_name"));
        office.setAddress(rs.getString("office_address"));
        office.setCityName(rs.getString("office_city_name"));
        office.setStateAcronym(rs.getString("office_state_acronym"));
        office.setPhoneNumber(rs.getString("office_phone_number"));
        office.setOpenTime(rs.getString("office_open_time"));
        office.setCloseTime(rs.getString("office_close_time"));
        return office;
    }
}
