/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connect;

/**
 *
 * @author doans
 */
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.Base64;
//
//public class InsertBase64ToBlob {
//
//    public static void main(String[] args) throws IOException {
//        // MySQL database connection details
//        String url = "jdbc:mysql://localhost:3306/doctris_system";
//        String username = "root";
//        String password = "123456";
//
//        // Base64-encoded data
//        File imageFile = new File("C:\\Users\\doans\\Downloads\\WebApplication1\\web\\assets\\images\\doctors\\bs-an.png");
//        InputStream inputStream = new FileInputStream(imageFile);
//        byte[] imageBytes = new byte[inputStream.available()];
//        inputStream.read(imageBytes);
//
//        try (Connection conn = DriverManager.getConnection(url, username, password)) {
//            // Prepare the SQL statement
//            String sql = "UPDATE doctor (img, role_id, username, Category_id) VALUES (?, ?, ?, ?)";
//            PreparedStatement statement = conn.prepareStatement(sql);
//            statement.setBytes(1, imageBytes);
//            statement.setInt(2, 3);
//            statement.setString(3, "anhong");
//            statement.setInt(4, 2);
//
//            // Execute the SQL statement
//            int rowsInserted = statement.executeUpdate();
//            if (rowsInserted > 0) {
//                System.out.println("Image inserted successfully.");
//            } else {
//                System.out.println("Failed to insert image.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertBase64ToBlob {

    public static void main(String[] args) throws IOException {
        // MySQL database connection details
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "thanh298";

        // Base64-encoded data
        File imageFile = new File("C:\\Users\\Downloads\\WebApplication1\\008.jpg");
        InputStream inputStream = new FileInputStream(imageFile);
        byte[] imageBytes = new byte[inputStream.available()];
        inputStream.read(imageBytes);

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // Prepare the SQL statement
            String sql = "UPDATE users SET img = ?, phone = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setBytes(1, imageBytes);
            statement.setInt(2, 383298183);

            // Execute the SQL statement
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Image updated successfully.");
            } else {
                System.out.println("Failed to update image.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
