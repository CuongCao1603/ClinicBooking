/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import context.DBContext;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Appointment;
import model.Doctor;
import model.Patient;
import model.Statistic;

/**
 *
 * @author doans
 */
public class AppointmentDAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext dbc = new DBContext();
    Connection connection = null;

    public List<Appointment> getAppointmentByDoctor(int doctor_id) throws SQLException, IOException {
        List<Appointment> list = new ArrayList<>();
        String sql = "Select appointments.appointment_id , doctor.doctor_name, "
                + "users.name , appointments.date ,appointments.time, "
                + "appointments.status from appointments inner join doctor "
                + "on appointments.doctor_id = doctor.doctor_id inner join patient "
                + "on appointments.patient_id = patient.patient_id inner join users "
                + "on patient.username = users.username where doctor.doctor_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, doctor_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Doctor doctor = new Doctor(rs.getString(2));
                Account account = new Account(rs.getString(3));
                Patient patient = new Patient(account);
                list.add(new Appointment(rs.getInt(1), patient, doctor, rs.getDate(4), rs.getTime(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            // Handle SQLException if needed
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;

    }

    public List<Appointment> getAppointmentListInDay() throws SQLException, IOException {
        List<Appointment> list = new ArrayList<>();
        String sql = "Select appointments.appointment_id , doctor.doctor_name, users.name , \n"
                + "appointments.date ,appointments.time, appointments.status from appointments \n"
                + "inner join doctor on appointments.doctor_id = doctor.doctor_id inner join patient on \n"
                + "appointments.patient_id = patient.patient_id inner join users on patient.username = users.username where appointments.date = cast(CURDATE() as Date);";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Doctor doctor = new Doctor(rs.getString(2));
                Account account = new Account(rs.getString(3));
                Patient patient = new Patient(account);
                list.add(new Appointment(rs.getInt(1), patient, doctor, rs.getDate(4), rs.getTime(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            //no code
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }
    
        public List<Statistic> getDataLast7Day(String type) {
        List<Statistic> list = new ArrayList<>();
        String day7 = " Select p.day , coalesce(count(u.appointment_id), 0) as count from (\n"
                + "    Select curdate() as day union\n"
                + "    Select date_sub(Curdate(),interval 1 day) union\n"
                + "    Select date_sub(Curdate(),interval 2 day) union\n"
                + "    Select date_sub(Curdate(),interval 3 day)\n"
                + "          union\n"
                + "    Select date_sub(Curdate(),interval 4 day)\n"
                + "          union\n"
                + "    Select date_sub(Curdate(),interval 5 day)\n"
                + "         union\n"
                + "    Select date_sub(Curdate(),interval 6 day))as p\n"
                + "left join appointments as u on p.day = u.date group by p.day order by p.day asc";

        String day14 = "Select p.day , coalesce(count(u.appointment_id), 0) as count from (\n"
                + "                    Select curdate() as day union\n"
                + "                    Select date_sub(Curdate(),interval 1 day) union\n"
                + "                    Select date_sub(Curdate(),interval 2 day) union\n"
                + "                    Select date_sub(Curdate(),interval 3 day) union\n"
                + "                    Select date_sub(Curdate(),interval 4 day) union\n"
                + "                    Select date_sub(Curdate(),interval 5 day) union\n"
                + "                    Select date_sub(Curdate(),interval 6 day) union\n"
                + "                    Select date_sub(Curdate(),interval 7 day) union\n"
                + "                    Select date_sub(Curdate(),interval 8 day) union\n"
                + "                    Select date_sub(Curdate(),interval 9 day) union\n"
                + "                    Select date_sub(Curdate(),interval 10 day)union\n"
                + "                    Select date_sub(Curdate(),interval 11 day)\n"
                + "                    union\n"
                + "                    Select date_sub(Curdate(),interval 12 day)\n"
                + "                    union\n"
                + "                    Select date_sub(Curdate(),interval 13 day)\n"
                + "                    )as p\n"
                + "                    \n"
                + "                left join appointments as u on p.day = u.date group by p.day order by p.day asc";

        String day3 = "Select p.day , coalesce(count(u.appointment_id), 0) as count from (\n"
                + "                    Select curdate() as day\n"
                + "                          union\n"
                + "                    Select date_sub(Curdate(),interval 1 day)\n"
                + "                          union\n"
                + "                    Select date_sub(Curdate(),interval 2 day)\n"
                + "                    )as p left join appointments as u on p.day = u.date group by p.day order by p.day asc";
        try {
            connection = dbc.getConnection();
            if (type.equals("7day")) {
                ps = connection.prepareStatement(day7);
            }
            if (type.equals("14day")) {
                ps = connection.prepareStatement(day14);
            }
            if (type.equals("3day")) {
                ps = connection.prepareStatement(day3);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Statistic(rs.getDate(1), rs.getInt(2)));
            }
        } catch (SQLException e) {
            //no code
        }
        return list;
    }

}
