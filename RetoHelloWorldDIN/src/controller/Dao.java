/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import excepciones.LoginError;
import model.User;

/**
 *
 * @author uazko
 */
public interface Dao {

    public User login(User usu) throws LoginError;
}
