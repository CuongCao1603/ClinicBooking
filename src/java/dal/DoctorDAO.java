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
import model.Doctor;
import model.RateStar;
import model.Setting;

/**
 *
 * @author Admin
 */
public class DoctorDAO {
      PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext dbc = new DBContext();
    Connection connection = null;



    public List<Setting> getSpeciality() throws SQLException {
            List<Setting> list = new ArrayList<>();
        String sql = "select * from doctris_system.category_service";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Setting(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getBoolean(4)));
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;


    }

    public List<Doctor> getSort(String type) throws IOException, SQLException {
          List<Doctor> list = new ArrayList<>();
        String star = "select concat_ws(cs.id,d.category_id)id, cs.name, cs.setting_id ,cs.status,\n"
                + "d.doctor_id,d.role_id,d.doctor_name,d.username,\n"
                + "d.gender,d.DOB,d.phone,d.description,d.status,d.img,d.fee,d.position, sum(ratestar.star)/count(ratestar.star) star\n"
                + "from doctris_system.doctor d \n"
                + "inner join doctris_system.category_service cs \n"
                + "on d.category_id = cs.id left join ratestar on d.doctor_id = ratestar.doctor_id group by id, cs.name, cs.setting_id ,cs.status,\n"
                + "d.doctor_id,d.role_id,d.doctor_name,d.username, d.gender,d.DOB,d.phone,d.description,d.status,d.img,d.fee,d.position order by star DESC";
        String latest = "select concat_ws(cs.id,d.category_id)id,\n"
                + "cs.name, cs.setting_id ,cs.status,\n"
                + "d.doctor_id,d.role_id,d.doctor_name,d.username,\n"
                + "d.gender,d.DOB,d.phone,d.description,d.status,d.img,d.fee,d.position\n"
                + "from doctris_system.doctor d \n"
                + "inner join doctris_system.category_service cs \n"
                + "on d.category_id = cs.id ORDER BY d.doctor_id desc";
        String popular = "select concat_ws(cs.id,d.category_id)id, cs.name, cs.setting_id ,cs.status,\n"
                + "d.doctor_id,d.role_id,d.doctor_name,d.username,\n"
                + "d.gender,d.DOB,d.phone,d.description,d.status,d.img,d.fee,d.position, count(appointments.appointment_id) appointment\n"
                + "from doctris_system.doctor d\n"
                + "inner join doctris_system.category_service cs\n"
                + "on d.category_id = cs.id left join  appointments on d.doctor_id = appointments.doctor_id group by id, cs.name, cs.setting_id ,cs.status,\n"
                + "d.doctor_id,d.role_id,d.doctor_name,d.username, d.gender,d.DOB,d.phone,d.description,d.status,d.img,d.fee,d.position order by appointment DESC";
        String fee = "select concat_ws(cs.id,d.category_id)id,"
                + " cs.name, cs.setting_id ,cs.status,"
                + "d.doctor_id,d.role_id,d.doctor_name,d.username,"
                + "d.gender,d.DOB,d.phone,d.description,d.status,d.img,d.fee,d.position "
                + "from doctris_system.doctor d "
                + "inner join doctris_system.category_service cs "
                + "on d.category_id = cs.id ORDER BY d.doctor_id desc";
        String ascending = "select concat_ws(cs.id,d.category_id)id,"
                + " cs.name, cs.setting_id ,cs.status,"
                + "d.doctor_id,d.role_id,d.doctor_name,d.username,"
                + "d.gender,d.DOB,d.phone,d.description,d.status,d.img,d.fee,d.position "
                + "from doctris_system.doctor d "
                + "inner join doctris_system.category_service cs "
                + "on d.category_id = cs.id ORDER BY d.doctor_id desc";
        String decrease = "select concat_ws(cs.id,d.category_id)id,"
                + " cs.name, cs.setting_id ,cs.status,"
                + "d.doctor_id,d.role_id,d.doctor_name,d.username,"
                + "d.gender,d.DOB,d.phone,d.description,d.status,d.img,d.fee,d.position "
                + "from doctris_system.doctor d "
                + "inner join doctris_system.category_service cs "
                + "on d.category_id = cs.id ORDER BY d.doctor_id asc";
        try {
            connection = dbc.getConnection();
            if (type.equals("star")) {
                ps = connection.prepareStatement(star);
            }
            if (type.equals("latest")) {
                ps = connection.prepareStatement(latest);
            }

            if (type.equals("popular")) {
                ps = connection.prepareStatement(popular);
            }

            if (type.equals("fee-")) {
                ps = connection.prepareStatement(ascending);
            }

            if (type.equals("fee")) {
                ps = connection.prepareStatement(decrease);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob(14);
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

    public List<Doctor> getAllDoctorHome() throws IOException, SQLException {
       
          List<Doctor> list = new ArrayList<>();
        String sql = "select concat_ws(cs.id,d.category_id)id,"
                + " cs.name, cs.setting_id ,cs.status,"
                + "d.doctor_id,d.role_id,d.doctor_name,d.username,"
                + "d.gender,d.DOB,d.phone,d.description,d.status,d.img,d.fee,d.position "
                + "from doctris_system.doctor d "
                + "inner join doctris_system.category_service cs "
                + "on d.category_id = cs.id";
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

    public List<Doctor> getFilter(String speciality, String gender) throws IOException, SQLException {
         List<Doctor> list = new ArrayList<>();
        String spec = "select concat_ws(cs.id,d.category_id)id,"
                + " cs.name, cs.setting_id ,cs.status,"
                + "d.doctor_id,d.role_id,d.doctor_name,d.username,"
                + "d.gender,d.DOB,d.phone,d.description,d.status,d.img,d.fee,d.position "
                + "from doctris_system.doctor d "
                + "inner join doctris_system.category_service cs "
                + "on d.category_id = cs.id where d.category_id = ?";
        String gen = "select concat_ws(cs.id,d.category_id)id,"
                + " cs.name, cs.setting_id ,cs.status,"
                + "d.doctor_id,d.role_id,d.doctor_name,d.username,"
                + "d.gender,d.DOB,d.phone,d.description,d.status,d.img,d.fee,d.position "
                + "from doctris_system.doctor d "
                + "inner join doctris_system.category_service cs "
                + "on d.category_id = cs.id where d.gender = ?";
        String filter = "select concat_ws(cs.id,d.category_id)id,\n"
                + "cs.name, cs.setting_id ,cs.status,\n"
                + "d.doctor_id,d.role_id,d.doctor_name,d.username,\n"
                + "d.gender,d.DOB,d.phone,d.description,d.status,d.img,d.fee,d.position\n"
                + "from doctris_system.doctor d \n"
                + "inner join doctris_system.category_service cs \n"
                + "on d.category_id = cs.id where d.gender = ? AND d.category_id = ?";
        try {
            connection = dbc.getConnection();
            if (speciality.equals("all")) {
                ps = connection.prepareStatement(gen);
                ps.setBoolean(1, Boolean.parseBoolean(gender));
            } else if (gender.equals("all")) {
                ps = connection.prepareStatement(spec);
                ps.setString(1, speciality);
            } else {
                ps = connection.prepareStatement(filter);
                ps.setBoolean(1, Boolean.parseBoolean(gender));
                ps.setString(2, speciality);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob(14);
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

    public Doctor getDetail(int id) throws IOException, SQLException {
      String getdoctor = "select concat_ws(cs.id,d.category_id)id,"
                + " cs.name, cs.setting_id ,cs.status,"
                + "d.doctor_id,d.role_id,d.doctor_name,d.username,"
                + "d.gender,d.DOB,d.phone,d.description,d.status,d.img,d.fee,d.position "
                + "from doctris_system.doctor d "
                + "inner join doctris_system.category_service cs "
                + "on d.category_id = cs.id where d.doctor_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(getdoctor);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob(14);
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
                Account a = new Account(rs.getString(8));
                Setting s = new Setting(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getBoolean(4));
                return new Doctor(s, rs.getInt(5), rs.getInt(6), rs.getString(7), a, rs.getBoolean(9), rs.getDate(10), rs.getInt(11), rs.getString(12), rs.getBoolean(13), base64Image, rs.getDouble(15), rs.getString(16));
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }

    public int getStars(int id) throws SQLException {
          int star = 0;
        String sql = "SELECT (SUM(star) / COUNT(*)) as star from ratestar where doctor_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                star = rs.getInt(1);
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return star;
    }

    public int CountFeedback(int id) throws SQLException {
        int star = 0;
        String sql = "SELECT COUNT(*) from ratestar where doctor_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                star = rs.getInt(1);
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return star;
    }

    public List<RateStar> getRateDoctor(int id) throws IOException, SQLException {
          List<RateStar> list = new ArrayList<>();
        String sql = "SELECT users.name, users.img, ratestar.star, ratestar.feedback, "
                + "ratestar.datetime FROM ratestar inner join users "
                + "on ratestar.username = users.username where ratestar.doctor_id = ? order by ratestar.id desc";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob(2);
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
                Account a = new Account(rs.getString(1), null, null, base64Image);
                list.add(new RateStar(a, rs.getInt(3), rs.getString(4), rs.getTimestamp(5)));
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<RateStar> getListByPageRate(List<RateStar> rate, int start, int end) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Doctor> getListByPage(ArrayList<Doctor> doctorall, int start, int end) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Appointment> getAllAppointment(int doctorIDByUsername) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Appointment getAppointmentDetail(int parseInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void UpdateAppointmentStatus(int parseInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
