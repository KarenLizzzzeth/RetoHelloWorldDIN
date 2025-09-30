/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

public class User implements Serializable{

    private int IdUser;
    private String nameUser;
    private String PasswordUser;
    private String direction;
    private String email;

    public User(int IdUser, String nameUser, String PasswordUser, String direction, String email) {
        this.IdUser = IdUser;
        this.nameUser = nameUser;
        this.PasswordUser = PasswordUser;
        this.direction = direction;
        this.email = email;
    }

    public User() {
    }

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int IdUser) {
        this.IdUser = IdUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPasswordUser() {
        return PasswordUser;
    }

    public void setPasswordUser(String PasswordUser) {
        this.PasswordUser = PasswordUser;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  private static final long serialVersionUID = 1L;

}
