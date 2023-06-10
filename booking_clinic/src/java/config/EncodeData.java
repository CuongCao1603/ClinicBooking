/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncodeData {

    public static String enCode(String s) {
        byte[] data = s.getBytes(StandardCharsets.UTF_8);
        String s2 = Base64.getEncoder().encodeToString(data);
        return s2;
    }


    public static String deCode(String s) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.getDecoder().decode(s);
        String s2 = new String(decodedBytes);
        return s2;
    }
}
