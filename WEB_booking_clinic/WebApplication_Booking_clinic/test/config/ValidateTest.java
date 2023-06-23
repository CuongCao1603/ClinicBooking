/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package config;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DELL
 */
public class ValidateTest {
    
    Validate vd = new Validate();;
    
    public ValidateTest() { 
    }

    @Test
    public void testCheckPassword() {
        
    }

    @Test
    public void testCapitalizeFirstLetter() {
        String input = "hoang cao Viet anh";
        String output = "Hoang Cao Viet Anh";
       String actual = Validate.capitalizeFirstLetter(input);
        assertEquals(output, actual);
    }
    
}
