package configs;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUploader {
    public static void main(String[] args) {
        String apiURL = "https://drive.google.com/file/d/1GX1O0-yeSU0_QhdwsXUT0n3dYVtv0rr-/view?usp=sharing"; // Replace with the correct API URL
        String filePath = "C:/Users/doans/Downloads/Compressed/ClinicBooking/web/XML/Setting.xml"; // Update the file path

        try {
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);

            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");

            OutputStream outputStream;
            try {
                outputStream = connection.getOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("File uploaded successfully.");
            } else {
                System.out.println("File upload failed. Response code: " + responseCode);
            }

            fileInputStream.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
