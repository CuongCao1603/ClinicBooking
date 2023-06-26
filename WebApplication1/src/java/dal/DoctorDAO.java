/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Doctor;
import context.DBContext;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import model.Account;
import model.Appointment;
import model.Patient;
import model.RateStar;
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
                + "from doctris_system.doctor d inner join doctris_system.category_service cs "
                + "on d.category_id = cs.id ORDER BY RAND() LIMIT 8";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob(14); //trích xuất đối tượng Blob từ kết quả truy vấn ResultSet tại vị trí cột thứ 14.
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();//tạo một đối tượng InputStream từ blob, sử dụng getBinaryStream() để lấy luồng nhị phân của blob.
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();//tạo một đối tượng ByteArrayOutputStream, được sử dụng để ghi dữ liệu từ luồng đầu vào.
                    byte[] buffer = new byte[4096]; //tạo một bộ đệm có kích thước 4096 byte.
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);//ghi bytesRead byte đọc được từ buffer vào outputStream.
                    }//bytesRead = inputStream.read(buffer) đọc một phần dữ liệu từ inputStream vào buffer và trả về số byte đã đọc. Nếu không còn dữ liệu, giá trị trả về là -1 và vòng lặp sẽ dừng lại.
                    //Sau khi vòng lặp kết thúc, toàn bộ dữ liệu đã được đọc từ blob và ghi vào outputStream. Bây giờ, dữ liệu trong outputStream cần được chuyển đổi thành một mảng byte.
                    byte[] imageBytes = outputStream.toByteArray();//chuyển đổi nội dung của outputStream thành một mảng byte bằng cách sử dụng phương thức toByteArray().
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);// mã hóa mảng byte thành một chuỗi Base64 sử dụng Base64.getEncoder().encodeToString() và gán kết quả vào biến base64Image.
                } else {
                    base64Image = "default";
                }
                Account a = new Account(rs.getString(8));
                Setting s = new Setting(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getBoolean(4));
                list.add(new Doctor(s, rs.getInt(5), rs.getInt(6), rs.getString(7), a, rs.getBoolean(9), rs.getDate(10), rs.getInt(11), rs.getString(12), rs.getBoolean(13), base64Image, rs.getDouble(15), rs.getString(16)));
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

    public List<Doctor> getAllDoctor() throws SQLException, IOException {
        List<Doctor> list = new ArrayList<>();
        String sql = "select cs.name, d.doctor_id,d.doctor_name,d.gender,d.status "
                + "from doctris_system.doctor d "
                + "inner join doctris_system.category_service cs "
                + "on d.category_id = cs.id";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Setting s = new Setting(rs.getString(1));
                list.add(new Doctor(s, rs.getInt(2), rs.getString(3), rs.getBoolean(4), rs.getBoolean(5)));
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

    public List<Doctor> Search(String text) throws SQLException, IOException {
        List<Doctor> list = new ArrayList<>();
        String sql = "select cs.name, d.doctor_id,d.doctor_name,d.gender,d.status "
                + "from doctris_system.doctor d "
                + "inner join doctris_system.category_service cs "
                + "on d.category_id = cs.id where d.doctor_name LIKE ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + text + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Setting s = new Setting(rs.getString(1));
                list.add(new Doctor(s, rs.getInt(2), rs.getString(3), rs.getBoolean(4), rs.getBoolean(5)));
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

    public List<Doctor> getAllDoctorBySpeciality(String speciality) throws SQLException, IOException {
        List<Doctor> list = new ArrayList<>();
        String sql = "select cs.name, d.doctor_id,d.doctor_name,d.gender,d.status "
                + "from doctris_system.doctor d "
                + "inner join doctris_system.category_service cs "
                + "on d.category_id = cs.id where d.category_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, speciality);
            rs = ps.executeQuery();
            while (rs.next()) {
                Setting s = new Setting(rs.getString(1));
                list.add(new Doctor(s, rs.getInt(2), rs.getString(3), rs.getBoolean(4), rs.getBoolean(5)));
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    public List<Doctor> getAllDoctorByGender(String gender) throws SQLException, IOException {
        List<Doctor> list = new ArrayList<>();
        String sql = "select cs.name, d.doctor_id,d.doctor_name,d.gender,d.status "
                + "from doctris_system.doctor d "
                + "inner join doctris_system.category_service cs "
                + "on d.category_id = cs.id where d.gender = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setBoolean(1, Boolean.parseBoolean(gender));
            rs = ps.executeQuery();
            while (rs.next()) {
                Setting s = new Setting(rs.getString(1));
                list.add(new Doctor(s, rs.getInt(2), rs.getString(3), rs.getBoolean(4), rs.getBoolean(5)));
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    public List<Doctor> getAllDoctorByFilter(String gender, String speciality) throws SQLException, IOException {
        List<Doctor> list = new ArrayList<>();
        String sql = "select cs.name, d.doctor_id,d.doctor_name,d.gender,d.status "
                + "from doctris_system.doctor d "
                + "inner join doctris_system.category_service cs "
                + "on d.category_id = cs.id where d.gender = ? and d.category_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, gender);
            ps.setString(2, speciality);
            rs = ps.executeQuery();
            while (rs.next()) {
                Setting s = new Setting(rs.getString(1));
                list.add(new Doctor(s, rs.getInt(2), rs.getString(3), rs.getBoolean(4), rs.getBoolean(5)));
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    public Doctor getDoctorByID(int doctor_id) throws SQLException, IOException {
        String sql = "select cs.name,d.doctor_id,d.role_id,d.doctor_name,d.username,"
                + "d.gender,d.DOB,d.phone,d.description,d.status,d.img,u.email,d.fee,d.position "
                + "from doctris_system.doctor d "
                + "inner join doctris_system.category_service cs on d.category_id = cs.id "
                + "inner join doctris_system.users u on d.username = u.username "
                + "where d.doctor_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, doctor_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob(11);
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
                Account a = new Account(rs.getString(5), rs.getString(12), null, null);
                Setting s = new Setting(rs.getString(1));
                return new Doctor(s, rs.getInt(2), rs.getInt(3), rs.getString(4), a, rs.getBoolean(6), rs.getDate(7), rs.getInt(8), rs.getString(9), rs.getBoolean(10), base64Image, rs.getDouble(13), rs.getString(14));
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }

    public List<RateStar> getRateDoctor(int id) throws SQLException, IOException {
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

    public void UpdateImage(int doctor_id, Part img) throws SQLException {
        String sql = "UPDATE `doctris_system`.`doctor` SET `img` = ? WHERE (`doctor_id` = ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            InputStream image = img.getInputStream();
            ps.setBlob(1, image);
            ps.setInt(2, doctor_id);
            ps.executeUpdate();
        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void DoctorUpdate(int id, String name, boolean gender, int phone, Date DOB, String description, int speciality, boolean status) throws SQLException {
        String sql = "UPDATE `doctris_system`.`doctor` SET `Category_id` = ?, `doctor_name` = ?, `gender` = ?, `DOB` = ?, `phone` = ?, `description` = ?, `status` = ? WHERE (`doctor_id` = ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, speciality);
            ps.setString(2, name);
            ps.setBoolean(3, gender);
            ps.setDate(4, DOB);
            ps.setInt(5, phone);
            ps.setString(6, description);
            ps.setBoolean(7, status);
            ps.setInt(8, id);
            ps.executeUpdate();
        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public int CountDoctor() {
        int count = 0;
        String sql = "select count(*) from doctor";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return count;
    }

    public List<Doctor> getListByPage(List<Doctor> list,
            int start, int end) {
        ArrayList<Doctor> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }
}
