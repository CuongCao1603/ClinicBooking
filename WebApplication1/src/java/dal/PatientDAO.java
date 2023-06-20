/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import context.DBContext;
import java.io.ByteArrayOutputStream;
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

        List<Patient> list = new ArrayList<>();
        String sql = "select distinct users.img, users.name, a.pdate, users.phone, users.email, patient.DOB, patient.patient_id\n"
                + "	as lastbooking from appointments \n"
                + "	inner join patient on appointments.patient_id = patient.patient_id \n"
                + "	inner join users on patient.username = users.username inner join \n"
                + "	(\n"
                + "	select patient_id as pid , max(date) as pdate from appointments group by patient_id\n"
                + "	) \n"
                + "	as a on a.pid = appointments.patient_id where appointments.doctor_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
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
                Account a = new Account(base64Image, rs.getString(4), rs.getInt(6), rs.getString(7));
                Appointment ap = new Appointment(rs.getDate(5));
                list.add(new Patient(a, rs.getDate(6), rs.getInt(1), ap));
            }
        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

}
