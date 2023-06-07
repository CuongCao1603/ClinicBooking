/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Role {

    private int role_id;
    private String name;
    Setting setting;
    private String note;

    public Role() {
    }

    public Role(int role_id, String name, Setting setting, String note) {
        this.role_id = role_id;
        this.name = name;
        this.setting = setting;
        this.note = note;
    }

    public Role(int role_id, String name) {
        this.role_id = role_id;
        this.name = name;
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(int role_id) {
        this.role_id = role_id;
    }

}
