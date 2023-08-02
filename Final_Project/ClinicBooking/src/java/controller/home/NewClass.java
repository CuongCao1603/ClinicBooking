/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.home;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class NewClass {

    public class Calculation {

        public ArrayList<Integer> vicDiff(int[] ar1, int[] ar2) {
            
            ArrayList<Integer> output = null;
            
            boolean found;
            
            if (ar1 != null & ar2 != null) {
                output = new ArrayList<Integer>();
                
                for (int item : ar1) {
                    int i = 0;
                    found = false;
                    
                    while (i < ar2.length && !found) {
                        if (item == ar2[i]) {
                            found = true;
                            i++;
                        }
                    }
                    
                    System.out.println(found);
                    if (found) {
                        output.add(item);
                    }
                }
                
            }
            
            return output;
        }
    }

}
