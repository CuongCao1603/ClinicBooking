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

    public List<Appointment> getAppointmentByPatient(int doctor_id, int patient_id) throws SQLException, IOException {
        List<Appointment> list = new ArrayList<>();
        String sql = "select a.date,a.time,a.status from appointments a inner join patient p\n"
                + "on a.patient_id = p.patient_id \n"
                + "where a.doctor_id = ? and p.patient_id = ? ORDER BY a.date ASC";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, doctor_id);
            ps.setInt(2, patient_id);
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Appointment(rs.getDate(1), rs.getTime(2), rs.getString(3)));
            }
        } catch (SQLException e) {
        }
        return list;
    }
    
    public List<Appointment> getListByPage(List<Appointment> list, int start, int end){
        ArrayList<Appointment> arr = new ArrayList<>();
        for(int i = start; i < end; i++){
            arr.add(list.get(i));
        }
        return arr;
    }
}
