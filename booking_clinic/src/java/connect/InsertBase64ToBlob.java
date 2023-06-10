/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connect;


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
        String url = "jdbc:mysql://localhost:3306/g3_cbs_db_final";
        String username = "sa";
        String password = "vanhleg2301";

        // Base64-encoded data
        File imageFile = new File("D:\\NetBeanProjects--------------------------\\DoctrisSystem-main\\ClinicBooking-codebyVietAnh\\Github_code\\booking_clinic\\web\\assets\\images\\doctors\\bs-huong.png");
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
