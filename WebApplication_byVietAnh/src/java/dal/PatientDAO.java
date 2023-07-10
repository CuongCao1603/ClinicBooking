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
    
        public List<Patient> search(int doctor_id, String keyword) throws SQLException, IOException {
        List<Patient> searchResults = new ArrayList<>();
        String sql = "SELECT DISTINCT users.name, users.phone, users.email, a.pdate, patient.DOB, patient.patient_id AS lastbooking FROM appointments "
                + "INNER JOIN patient ON appointments.patient_id = patient.patient_id "
                + "INNER JOIN users ON patient.username = users.username INNER JOIN ("
                + "SELECT patient_id AS pid , MAX(date) AS pdate FROM appointments GROUP BY patient_id"
                + ") AS a ON a.pid = appointments.patient_id "
                + "WHERE appointments.doctor_id = ? AND users.name LIKE ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, doctor_id);
            ps.setString(2, "%" + keyword + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Account a = new Account(rs.getString(1), rs.getInt(2), rs.getString(3));
                Appointment ap = new Appointment(rs.getDate(4));
                searchResults.add(new Patient(a, ap, rs.getDate(5), rs.getInt(6)));
            }
        } catch (SQLException e) {
            // Xử lý ngoại lệ
        } finally {
            // Đảm bảo đóng kết nối
            if (connection != null) {
                connection.close();
            }
        }
        return searchResults;
    }
//    1 là chỉ số của tham số trong truy vấn SQL. Chỉ số tham số bắt đầu từ 1.
//      text là giá trị chuỗi mà bạn muốn thiết lập cho tham số.
//      "%" + text + "%" là một chuỗi kết hợp. Nó kết hợp giá trị của biến text với các dấu % ở đầu và cuối chuỗi. 
//       Điều này tạo ra một chuỗi có dạng %text%, nơi % đại diện cho một ký tự bất kỳ 
//      hoặc một chuỗi ký tự bất kỳ.

    public List<Patient> getPatientbyid(int patient_id) throws SQLException, IOException {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT u.name, u.email, u.phone, u.gender, p.DOB FROM users u inner join patient p\n"
                + "on u.username = p.username\n"
                + "where p.patient_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, patient_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Account a = new Account(rs.getString(1), rs.getInt(2), rs.getBoolean(3), rs.getString(4));
                list.add(new Patient(a, rs.getDate(5))) ;
            }
        } catch (SQLException e) {
        }
        return list;
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

            while (rs.next()) {
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

    public List<Patient> searchPatientByName(String name) throws SQLException, IOException {
        List<Patient> list = new ArrayList<>();
        String sql = "select p.patient_id,u.username,u.name,u.gender,p.DOB,p.status from doctris_system.patient p\n"
                + " inner join doctris_system.users u\n"
                + " on p.username = u.username\n"
                + " WHERE u.name LIKE N'%" + name + "%'";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Patient(new Account(rs.getString(2), rs.getString(3), rs.getBoolean(4)), rs.getInt(1), rs.getDate(5), rs.getBoolean(6)));
            }
        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    public Patient getPatientByUsername(String username) throws SQLException, IOException {
        String sql = "select  u.img, u.username,u.name,u.email,u.gender,u.phone,p.patient_id,p.DOB,p.address,p.status from patient p inner join users u \n"
                + "on p.username = u.username\n"
                + "where u.username = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
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
                Account a = new Account(base64Image, rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getInt(6));
                return new Patient(a, rs.getInt(7), rs.getDate(8), rs.getString(9), rs.getBoolean(10));
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }

    public List<Patient> getListByPage(List<Patient> list, int start, int end) {
        ArrayList<Patient> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }
}
