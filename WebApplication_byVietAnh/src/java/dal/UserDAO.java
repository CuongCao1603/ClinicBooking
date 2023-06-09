/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.SQLException;
import context.DBContext;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import model.Account;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Role;

/**
 *
 * @author doans
 */
public class UserDAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext dbc = new DBContext();
    Connection connection = null;

    public Account login(String email, String password) throws SQLException, IOException {
        String sql = " select * from users where email=? and password=?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob(8);
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
                Role r = new Role(rs.getInt(2));
                return new Account(rs.getString(1), r, rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getInt(6), rs.getString(7), base64Image, rs.getBoolean(9));
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }

    //CheckAcc
    public Account checkAcc(String email, String username) throws SQLException {
        String sql = "select * from users where email=? or username=?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob(8);
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
                Role r = new Role(rs.getInt(2));
                return new Account(rs.getString(1), r, rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getInt(6), rs.getString(7), base64Image, rs.getBoolean(9));
            }
        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }

    // Register
    public void Register(String email, String password, String username, int role_id, String name, int phone, boolean gender, boolean status) throws SQLException {
        String sql = "INSERT INTO `doctris_system`.`users` (`username`, `role_id`, `password`, `name`, `gender`, `phone`, `email`,`status`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setInt(2, role_id);
            ps.setString(3, password);
            ps.setString(4, name);
            ps.setBoolean(5, gender);
            ps.setInt(6, phone);
            ps.setString(7, email);
            ps.setBoolean(8, status);
            ps.executeUpdate();
//            execUpdate() là một phương thức trong API kết nối cơ sở dữ liệu Java (JDBC) được sử dụng để 
//              thực thi các câu lệnh SQL không trả về bất kỳ tập kết quả nào, 
//              chẳng hạn như các câu lệnh INSERT, UPDATE hoặc DELETE.
//              Phương thức execUpdate() trả về một giá trị số nguyên biểu thị số hàng bị ảnh hưởng bởi câu lệnh SQL. Ví dụ: nếu bạn thực thi câu lệnh CẬP NHẬT bằng hàm execUpdate(), thì giá trị được trả về sẽ là số hàng đã được cập nhật trong cơ sở dữ liệu.
        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void UpdateProfile(String username, String name, int phone, boolean gender) throws SQLException {
        String sql = "UPDATE `doctris_system`.`users` SET `name` = ?, `phone` = ?, `gender` = ? WHERE (`username` = ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, phone);
            ps.setBoolean(3, gender);
            ps.setString(4, username);
            ps.executeUpdate();
        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void Recover(String username, String password) throws SQLException {
        String sql = "UPDATE `doctris_system`.`users` SET password = ? WHERE (username = ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(2, username);
            ps.setString(1, password);
            ps.executeUpdate();
        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public Account getAccountByUsername(String username) throws SQLException, IOException {
        String sql = "SELECT u.username,u.name,u.gender,u.email,u.phone,r.name,u.status,u.img "
                + "FROM doctris_system.users u "
                + "inner join doctris_system.role r "
                + "on u.role_id = r.id where u.username = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob(8);
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
                Role r = new Role(rs.getString(6));
                return new Account(rs.getString(1), r, rs.getString(2), rs.getBoolean(3), rs.getInt(5), rs.getString(4), base64Image, rs.getBoolean(7));
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }

    public void UpdateImage(String username, Part img) throws SQLException {
        String sql = "UPDATE `doctris_system`.`users` SET `img` = ? WHERE (`username` = ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            InputStream image = img.getInputStream();
            ps.setBlob(1, image);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

}
