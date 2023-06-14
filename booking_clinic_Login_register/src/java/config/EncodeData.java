/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

/**
 *
 * @author doans
 */

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncodeData {

    // 
    // Tại sao cần mã hóa khi đưa vào database 
    // Tại sao cần chuyển chuỗi s về thành mảng byte sử dụng mã hóa UTF-8
    // Má hóa 
    public static String enCode(String s) {
        byte[] data = s.getBytes(StandardCharsets.UTF_8);//chuyển đổi chuỗi s thành một mảng byte sử dụng mã hóa UTF-8.
                                            //8-bit Unicode Transformation Format - Định dạng chuyển đổi Unicode 8-bit
        //UTF-8 là Định dạng Chuyển đổi Unicode 8-bit đại diện cho tất cả các ký tự có thể có trong tiêu chuẩn Unicode.
        
        String s2 = Base64.getEncoder().encodeToString(data);
        // sử dụng lớp Base64.getEncoder() để mã hóa mảng byte thành một chuỗi Base64.
        
        return s2;
        //Cuối cùng, phương thức trả về chuỗi s2 đã được mã hóa.
    }

    // Giải mã 
    public static String deCode(String s) throws UnsupportedEncodingException {
        
        byte[] decodedBytes = Base64.getDecoder().decode(s);
        // sử dụng lớp Base64.getDecoder() để giải mã chuỗi Base64 thành một mảng byte.
        
        String s2 = new String(decodedBytes);
        // chuyển đổi mảng byte đã giải mã thành chuỗi sử dụng mã hóa mặc định.
        
        return s2;
    }
}
