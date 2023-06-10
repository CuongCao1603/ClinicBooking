/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connect.DBContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
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
    ResultSet rs = null;
    DBContext dbc = new DBContext();
    Connection connection = null;

    public List<Patient> getPatientByDoctor(int doctor_id) throws SQLException, IOException {
        List<Patient> list = new ArrayList<>();
        String sql1 = "select distinct users.img, users.name, users.phone, users.email,patient.DOB,patient.patient_id ,a.pdate as lastbooking from appointments \n"
                + "inner join patient on appointments.patient_id = patient.patient_id \n"
                + "inner join users on patient.username = users.username inner join (\n"
                + "select patient_id as pid , max(date) as pdate from appointments group by patient_id\n"
                + ") as a on a.pid = appointments.patient_id where appointments.doctor_id = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql1);
            ps.setInt(1, doctor_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob(1);
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    inputStream.close();
                    outputStream.close();
                } else {
                    base64Image = "default";
                }
//                Account a = new Account(base64Image, rs.getString(2), rs.getInt(3), false, rs.getString(4));
//                Appointment ap = new Appointment(rs.getDate(7), null, null);
//                list.add(new Patient(a, rs.getDate(5), rs.getInt(6), ap));
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
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
                Account a = new Account(null, rs.getString(1), rs.getInt(3), rs.getBoolean(4), rs.getString(2));
                return new Patient(a, rs.getDate(5));
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }
}
