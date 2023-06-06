/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Account {
    private String username;
    Role role;
    private String password;
    private String name;
    private boolean gender;
    private int phone;
    private String email;
    private String img;
    private boolean status;
    private String captcha;

    public Account() {
    }

    public Account(String username, Role role, String password, String name,
            boolean gender, int phone, String email, String img, boolean status) {
        this.username = username;
        this.role = role;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.img = img;
        this.status = status;
    }

    public Account(String username, Role role, String name, boolean gender, int phone, String email, String img, boolean status) {
        this.username = username;
        this.role = role;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.img = img;
        this.status = status;
    }

    public Account(String username, String name, boolean gender, int phone, String email, String img) {
        this.username = username;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.img = img;
    }
    
    
    
    
    
    
    
    
    
}
