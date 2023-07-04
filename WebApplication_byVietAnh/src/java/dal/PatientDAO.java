/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import context.DBContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import model.Account;
import model.Appointment;
import model.Patient;

/**
 *
 * @author DELL
 */
public class PatientDAO {

    PreparedStatement ps = null;
    Connection connection = null;
    DBContext dbc = new DBContext();
    ResultSet rs = null;

    public List<Patient> getPatientByDoctor(int doctor_id) throws SQLException {
        // Lấy ra id bệnh nhân từ id của bác si được truyền vào 
        List<Patient> list = new ArrayList<>();
        String sql = "select distinct users.name, users.phone, users.email, a.pdate, patient.DOB, patient.patient_id\n"
                + "as lastbooking from appointments \n"
                + "inner join patient on appointments.patient_id = patient.patient_id \n"
                + "inner join users on patient.username = users.username inner join \n"
                + "(\n"
                + "select patient_id as pid , max(date) as pdate from appointments group by patient_id\n"
                + ") \n"
                + "as a on a.pid = appointments.patient_id where appointments.doctor_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, doctor_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Account a = new Account(rs.getString(1), rs.getInt(2), rs.getString(3));
                Appointment ap = new Appointment(rs.getDate(4));
                list.add(new Patient(a, ap, rs.getDate(5), rs.getInt(6)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public Patient getPatientbyid(int patient_id) throws SQLException, IOException {
        String sql = "SELECT u.name,u.email,u.phone,u.gender,p.DOB FROM users u inner join patient p\n"
                + "on u.username = p.username\n"
                + "where p.patient_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, patient_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Account a = new Account(rs.getString(1), rs.getInt(2), rs.getBoolean(3), rs.getString(4));
                return new Patient(a, rs.getDate(5));
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public List<Patient> getAllPatient() throws SQLException, IOException {
        List<Patient> list = new ArrayList<>();
        String sql = "select p.patient_id,u.username,u.name,u.gender,p.DOB,p.status from doctris_system.patient p\n"
                + "inner join doctris_system.users u\n"
                + "on p.username = u.username";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()){
                list.add(new Patient(new Account(rs.getString(2), rs.getString(3), rs.getBoolean(4)), rs.getInt(1), rs.getDate(5), rs.getBoolean(6)));
            }
        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
            return list;
        }
    }
    
    public List<Patient> getListByPage(List<Patient> list, int start, int end){
        ArrayList<Patient> arr = new ArrayList<>();
        for(int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }
}
