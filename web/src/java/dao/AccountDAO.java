/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.SQLException;
import connect.DBContext;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import model.Account;
import java.sql.Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Base64;
import model.Role;

/**
 *
 * @author doans
 */
public class AccountDAO extends DBContext {

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
            System.out.println(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }

    public void UpdateImage(String username, Part img) throws SQLException {
        String sql = "UPDATE `doctris_system`.`users` SET img = ? WHERE (username = ?)";
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

    public void Recover(String username, String password) throws SQLException {
        String sql = "UPDATE `doctris_system`.`users` SET password = ? WHERE (name = ?)";
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
}
