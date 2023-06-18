package com.techelevator.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.techelevator.model.Appointment;
import com.techelevator.model.Details;
import com.techelevator.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JdbcAppointmentDao implements AppointmentDao {

    private final JdbcTemplate jdbcTemplate;
    DetailsDao detailsDao;

    public JdbcAppointmentDao(JdbcTemplate jdbcTemplate, DetailsDao detailsDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.detailsDao = detailsDao;
    }


    @Override
    public List<Appointment> findAllAppointments() {
        List<Appointment> appointments= new ArrayList<>();
        String sql =
                "select \n" +
                        "apt.apt_id, \n" +
                        "apt.apt_name, \n" +
                        "apt.apt_status, \n" +
                        "apt.apt_agenda, \n" +
                        "apt.apt_date, \n" +
                        "apt.user_id as patient_user_id, \n" +
                        "apt.details_id as provider_details_id\n" +
                        "from appointment apt";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Appointment appointment = mapRowToAppointment(results);
            appointments.add(appointment);
        }
        return appointments;
    }

    @Override
    public Appointment getAppointmentByAptId(int aptId) {
        String sql =
                "select \n" +
                        "apt.apt_id, \n" +
                        "apt.apt_name, \n" +
                        "apt.apt_status, \n" +
                        "apt.apt_agenda, \n" +
                        "apt.apt_date, \n" +
                        "apt.user_id as patient_user_id, \n" +
                        "apt.details_id as provider_details_id\n" +
                        "from appointment apt\n" +
                        "where apt.apt_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, aptId);
        if (results.next()) {
            return mapRowToAppointment(results);
        } else {
            return null;
        }
    }


//    @Override
//    public List<Appointment> findAllAppointmentsByProviderDetailsId(int userId) {
//        List<Appointment> appointments = new ArrayList<>();
//        String sql =
//                "select \n" +
//                "apt.apt_id, \n" +
//                "apt.apt_name, \n" +
//                "apt.apt_status, \n" +
//                "apt.apt_agenda, \n" +
//                "apt.apt_date, \n" +
//                "apt.user_id as patient_user_id, \n" +
//                "apt.details_id as provider_details_id\n" +
//                "from appointment apt\n" +
//                "JOIN details pat on pat.details_id = apt.details_id\n" +
//                "WHERE is_provider = true\n" +
//                "AND apt.details_id = ?";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
//        while (results.next()) {
//            Appointment appointment = mapRowToAppointment(results);
//            appointments.add(appointment);
//        }
//        return appointments;
//    }

    @Override
    public List<Appointment> findAllAppointmentsByPatientUserId(int userId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql =
                "select \n" +
                        "apt.apt_id, \n" +
                        "apt.apt_name, \n" +
                        "apt.apt_status, \n" +
                        "apt.apt_agenda, \n" +
                        "apt.apt_date, \n" +
                        "apt.user_id, \n" +
                        "apt.details_id\n" +
                        "from appointment apt\n" +
                        "JOIN details pat on pat.details_id = apt.details_id\n" +
                        "WHERE apt.user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            Appointment appointment = mapRowToAppointment(results);
            appointments.add(appointment);
        }
        return appointments;
    }

    @Override
    public List<Appointment> findAllAppointmentsByMonthAndYear(int month, int year) {
        List<Appointment> appointments = new ArrayList<>();
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        String sql = "select * from appointment\n" +
                "WHERE appointment.apt_date  BETWEEN ? AND ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, startDate, endDate);
        while (results.next()) {
            Appointment appointment = mapRowToAppointment(results);
            appointments.add(appointment);
        }
        return appointments;
    }

    @Override
    public List<Appointment> findAllAppointmentsByProviderDetailsId(int providerId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql =
                "select \n" +
                        "apt.apt_id, \n" +
                        "apt.apt_name, \n" +
                        "apt.apt_status, \n" +
                        "apt.apt_agenda, \n" +
                        "apt.apt_date, \n" +
                        "apt.user_id, \n" +
                        "apt.details_id \n" +
                        "from appointment apt\n" +
                        "WHERE apt.details_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, providerId);
        while (results.next()) {
            Appointment appointment = mapRowToAppointment(results);
            appointments.add(appointment);
        }
        return appointments;
    }

    @Override
    public List<Appointment> findAllNewAppointmentsByProviderDetailsId(int providerId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql =
                "select \n" +
                        "apt.apt_id, \n" +
                        "apt.apt_name, \n" +
                        "apt.apt_status, \n" +
                        "apt.apt_agenda, \n" +
                        "apt.apt_date, \n" +
                        "apt.user_id, \n" +
                        "apt.details_id\n" +
                        "from appointment apt\n" +
                        "JOIN details pat on pat.details_id = apt.details_id\n" +
                        "WHERE apt.details_id = ? AND\n" +
                        "apt.apt_status != 'Ongoing';";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, providerId);
        while (results.next()) {
            Appointment appointment = mapRowToAppointment(results);
            appointments.add(appointment);
        }
        return appointments;
    }

    @Override
    public void setAptAsRead(int aptId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql =
                "update appointment \n" +
                        "set apt_status = 'Ongoing'\n" +
                        "WHERE apt_id = ?;";
        jdbcTemplate.update(sql, aptId);
    }

    @Override
    public List<Appointment> findAllAppointmentsBothIds(int userId,int providerId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql =
                "select \n" +
                        "apt.apt_id, \n" +
                        "apt.apt_name, \n" +
                        "apt.apt_status, \n" +
                        "apt.apt_agenda, \n" +
                        "apt.apt_date, \n" +
                        "apt.user_id as patient_user_id, \n" +
                        "apt.details_id as provider_details_id\n" +
                        "from appointment apt\n" +
                        "JOIN details pat on pat.details_id = apt.details_id\n" +
                        "WHERE apt.user_id = ?\n" +
                        "AND apt.details_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, providerId);
        while (results.next()) {
            Appointment appointment = mapRowToAppointment(results);
            appointments.add(appointment);
        }
        return appointments;
    }

    @Override
    public int findIdByAptName(String aptName) {
        if (aptName == null) throw new IllegalArgumentException("Appointment name cannot be null");

        int aptId;
        try {
            aptId = jdbcTemplate.queryForObject("select apt_id from appointment where apt_name = ?", int.class, aptName);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException(aptName + " was not found...");
        }
        return aptId;
    }

    @Override
    public void create(Appointment appointment) {
        try {
            String sql = "INSERT INTO public.appointment(\n" +
                    "\tapt_name, apt_status, apt_agenda, apt_date, user_id, details_id)\n" +
                    "\tVALUES ( ?, 'New', ?, ?, ?, ?);";
            jdbcTemplate.update(sql, appointment.getName(),
                    appointment.getAgenda(), appointment.getDate(),
                    appointment.getUserId(), appointment.getDetailsId());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void update(Appointment appointment) {

        try {
            String sql = "UPDATE public.appointment\n" +
                    "\tSET apt_id=?, apt_name=?, apt_status=?, apt_agenda=?, apt_date=?, user_id=?, provider_id=?\n" +
                    "\tWHERE appointment_id =?;";
            jdbcTemplate.update(sql, appointment.getId(), appointment.getName(),
                    appointment.getStatus(), appointment.getDate(),
                    appointment.getAgenda(), appointment.getDate(),
                    appointment.getUserId(), appointment.getDetailsId());


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void delete(int id) {
        try{
            String sql = "DELETE FROM public.appointment\n" +
                    "\tWHERE appointment_id =?;";
            jdbcTemplate.update(sql, id);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private Appointment mapRowToAppointment(SqlRowSet rs) {
        Appointment appointment = new Appointment();
        appointment.setId(rs.getInt("apt_id"));
        appointment.setName(rs.getString("apt_name"));
        appointment.setStatus(rs.getString("apt_status"));
        appointment.setAgenda(rs.getString("apt_agenda"));
        appointment.setDate(rs.getTimestamp("apt_date").toLocalDateTime());
        appointment.setUserId(rs.getInt("user_id"));
        appointment.setProviderId(rs.getInt("details_id"));
        Details patient = detailsDao.getDetailsByUserId(appointment.getUserId());
        appointment.setPatientFirstName(patient.getFirstName());
        appointment.setPatientLastName(patient.getLastName());
        Details provider = detailsDao.getDetailsById(appointment.getDetailsId());
        appointment.setProviderFirstName(provider.getFirstName());
        appointment.setProviderLastName(provider.getLastName());
        return appointment;
    }
}
