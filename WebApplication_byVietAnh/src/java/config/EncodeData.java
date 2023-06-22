/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

/**
Base64 hoạt động bằng cách chia dữ liệu đầu vào thành các đoạn 6 bit và sau đó biểu thị từng đoạn dưới dạng một ký tự từ một bộ cụ thể gồm 64 ký tự ASCII có thể in được (do đó có tên "base64"). Dữ liệu được mã hóa kết quả thường lớn hơn dữ liệu nhị phân ban đầu nhưng có thể dễ dàng truyền và giải mã trở lại dạng ban đầu.
 */
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncodeData {

    private EncodeData() {}

    public static String enCode(String s) {
        byte[] data = s.getBytes(StandardCharsets.UTF_8);//chuyển đổi chuỗi s thành một mảng byte sử dụng mã hóa UTF-8.
        String s2 = Base64.getEncoder().encodeToString(data); // sử dụng lớp Base64.getEncoder() để mã hóa mảng byte thành một chuỗi Base64.
                                                              //Cuối cùng, phương thức trả về chuỗi s2 đã được mã hóa.
        return s2;
    }

    public static String deCode(String s) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.getDecoder().decode(s); // sử dụng lớp Base64.getDecoder() để giải mã chuỗi Base64 thành một mảng byte.
        String s2 = new String(decodedBytes);// chuyển đổi mảng byte đã giải mã thành chuỗi sử dụng mã hóa mặc định.
        return s2;
    }
}
