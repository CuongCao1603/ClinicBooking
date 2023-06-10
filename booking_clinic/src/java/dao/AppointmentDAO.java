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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Base64;
import model.*;

public class AppointmentDAO {

    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext dbc = new DBContext();
    Connection connection = null;

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
            while (rs.next()) {
                list.add(new Appointment(rs.getDate(1), rs.getTime(2), rs.getString(3)));
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    public List<Appointment> getListByPage(List<Appointment> list,
            int start, int end) {
        ArrayList<Appointment> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }
}
