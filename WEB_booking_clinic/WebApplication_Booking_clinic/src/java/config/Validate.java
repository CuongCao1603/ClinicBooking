
package config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validate {

    public static boolean checkPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@.$!%*?&])[A-Za-z\\d@$!.%*?&]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static String capitalizeFirstLetter(String s) {
        s = s.toLowerCase();
        String[] ss;
        String s1 = "";
        ss = s.split(" ");
        
        for (int i = 0; i < ss.length; i++) {
            
            if (ss[i].length() != 0) {
                s1 += Character.toUpperCase(ss[i].charAt(0));
                
                if (ss[i].length() > 1) {      
                    s1 += ss[i].substring(1);
                }
                
                if (i < ss.length) {
                    s1 += " ";
                }
                
            }
        }
        return s1;
        
    }
}
 