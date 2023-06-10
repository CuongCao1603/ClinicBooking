/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Doctor;
import connect.DBContext;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import model.Account;
import model.Appointment;
import model.Patient;
import model.Setting;

/**
 *
 * @author doans
 */
public class DoctorDAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext dbc = new DBContext();
    Connection connection = null;

    public List<Doctor> getRandomTop6Doctor() throws SQLException, IOException {
        List<Doctor> list = new ArrayList<>();
        String sql = "select concat_ws(cs.id,d.category_id)id,"
                + " cs.name, cs.setting_id ,cs.status,"
                + "d.doctor_id,d.role_id,d.doctor_name,d.username,"
                + "d.gender,d.DOB,d.phone,d.description,d.status,d.img,d.fee,d.position "
                + "from doctris_system.doctor d "
                + "inner join doctris_system.category_service cs "
                + "on d.category_id = cs.id ORDER BY RAND() LIMIT 8";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob(14);
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
//                    inputStream.close();
//                    outputStream.close();
                } else {
                    base64Image = "default";
                }
                Account a = new Account(rs.getString(8));
                Setting s = new Setting(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getBoolean(4));
                list.add(new Doctor(s, rs.getInt(5), rs.getInt(6), rs.getString(7), a, rs.getBoolean(9), rs.getDate(10), rs.getInt(11), rs.getString(12), rs.getBoolean(13), base64Image, rs.getDouble(15), rs.getString(16)));
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    public int getDoctorIDByUsername(String username) {
        int doctor_id = 0;
        String sql = "select doctor_id from doctor  where username = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                doctor_id = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return doctor_id;
    }

    public List<Appointment> getAllAppointment(int id) throws SQLException {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.appointment_id, p.patient_id,  u.name,a.date, a.time,a.status from appointments a\n"
                + "INNER JOIN patient p\n"
                + "ON a.patient_id = p.patient_id\n"
                + "INNER JOIN users u \n"
                + "ON p.username = u.username\n"
                + "WHERE a.doctor_id = ?\n"
                + "group by a.appointment_id, p.patient_id, u.name,a.date, a.time,a.status\n"
                + "order by CAST(a.date AS DATETIME) + CAST(a.time AS DATETIME) desc";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Appointment a = new Appointment(rs.getInt(1), new Patient(rs.getInt(2), rs.getString(3)), rs.getDate(4), rs.getTime(5), rs.getString(6));
                list.add(a);
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }
}
