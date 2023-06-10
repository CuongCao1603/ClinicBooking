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
                } 
                
                else {
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
        String sql = "INSERT INTO `g3_cbs_db_final`.`users` (`username`, `role_id`, `password`, `name`, `gender`, `phone`, `email`,`status`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
