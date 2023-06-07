/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class RateStar {
    private int id;
    private String feedback;
    private int star;
    private int countfeedback;
    private Account user;

    public RateStar() {
    }

    public RateStar(String feedback, int star, int countfeedback, Account user) {
        this.feedback = feedback;
        this.star = star;
        this.countfeedback = countfeedback;
        this.user = user;
    }

    public RateStar(int star, int countfeedback) {
        this.star = star;
        this.countfeedback = countfeedback;
    }

    public RateStar(String feedback, int star, Account user) {
        this.feedback = feedback;
        this.star = star;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getCountfeedback() {
        return countfeedback;
    }

    public void setCountfeedback(int countfeedback) {
        this.countfeedback = countfeedback;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }
    
    
    
    
    
    
    
    
    
}
