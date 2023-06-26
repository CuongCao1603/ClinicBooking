/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
<<<<<<< HEAD:WebApplication1/src/java/model/Statistic.java
 * @author doans
=======
 * @author Khuong Hung
>>>>>>> b7aa3a68f581d7db9a7618459bff303e9b500346:src/java/model/Statistic.java
 */
public class Statistic {
    private Date date;
    private int count;

    public Statistic() {
    }

    public Statistic(Date date, int count) {
        this.date = date;
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    
}
