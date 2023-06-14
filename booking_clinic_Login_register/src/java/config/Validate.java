/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

/**
 *
 * @author DELL
 */
public class Validate {

    public static String capitalizeFirstLetter(String s) {
        
        // length() = độ dài của mảng 
        // length   = độ dài của chuỗi 
        
        // Tại sao lại phải toLowerCase => Chuyển về viết thường để xử lý lại 
        s = s.toLowerCase(); // hoang cao viet anh 
        
        String[] ss; // Lưu chuỗi ngắt 
//        String s2 = ""; // lưu chuỗi trả về 
        String s1 = "";                                                
        ss = s.split(" "); // ss = hoang cao viet anh 
//                                ss[0] ss[1]ss[2]ss[3]
//        Đoạn mã này tách một chuỗi s thành một danh sách các chuỗi con 
//        bằng cách sử dụng ký tự khoảng trắng (" ") làm dấu phân cách. 
//        Danh sách kết quả được lưu trữ trong biến ss
                          
        for (int i = 0; i < ss.length; i++) {
            
            // ss.length = 4 => Tính độ dài của cả chuỗi (mỗi mảng là 1 kí tự trong chuỗi)
            
            // ss[0].length() = 5
            if (ss[i].length() != 0) { // Nếu người dùng có nhập khoảng trắng ở đầu hay cuối thì đều bị loại bỏ 
                    
                s1 += Character.toUpperCase(ss[i].charAt(0));
                // s1 = s1 + "H" => s1 = "H"
                
                if (ss[i].length() > 1) {//ss[0].length() = 5 
                    
                    s1 += ss[i].substring(1); // substring dùng để nối chuỗi 
                    // 
                  // s1 = s1 + "oang" => s1 = "Hoang"
                }
                
//                s2 += s1;
//                   // s2 = s2 + "Hoang" => s2 = "Hoang"
                
                if (i < ss.length) {
                    s1 += " ";
                        // s2 = s2 + " " => s2 = "Hoang "
                    
                }
            }
        }        
        return s1 + ss[3].length();
    }
    
    public static void main(String[] args) {
        String[] s = {"hoang", "Cao", "viet", "Anh"};
        String s10 = "hoang Cao viet anh";
        String ss = capitalizeFirstLetter(s10);
        String s0 = s[0].substring(1);
        String s1 = s[1].substring(1);
        String s2 = s[2].substring(1);
        String s3 = s[3].substring(1);
        System.out.println(ss);
    }
}
