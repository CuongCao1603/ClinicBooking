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
    
    public ValidateTest() {
    }

    @Test
    public void testCheckPassword() {
    }

    @Test
    public void testCapitalizeFirstLetter() {
        Validate vd = new Validate();
        String input = "hoang cao Viet Anh";
        String out = "Hoang Cao Viet Anh";
        String actual = Validate.capitalizeFirstLetter(input);
        assertEquals(out, actual);
    }

    @Test
    public void testMain() {
    }
    
}
