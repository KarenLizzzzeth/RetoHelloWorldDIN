/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import excepciones.LoginError;
import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

public class DaoImplementsFich implements Dao {

    public DaoImplementsFich() {
        File f = new File("usuarios.dat");
        if (!f.exists()) {
            precargarUsuarios();
        }
    }

    private void precargarUsuarios() {
        ArrayList<User> usuarios = new ArrayList<>();
        usuarios.add(new User(1, "Dani", "1234", "Calle Sol 12, Ciudad A", "dani.lopez@mail.com"));
        usuarios.add(new User(2, "Xavi", "1234", "Av. Luna 34, Ciudad B", "xavi.garcia@mail.com"));
        usuarios.add(new User(3, "Lorea", "1234", "Calle Estrella 56, Ciudad C", "lorea.martinez@mail.com"));
        usuarios.add(new User(4, "Leire", "1234", "Plaza Mayor 78, Ciudad D", "leire.fernandez@mail.com"));

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("usuarios.dat"));
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public User login(User usu) throws LoginError {
        ArrayList<User> usuarios = new ArrayList<>();
        usuarios = leerUsuarios();
        for (int i = 0; i < usuarios.size(); i++) {
            User u = usuarios.get(i);
            if (u.getNameUser().equalsIgnoreCase(usu.getNameUser()) && u.getPasswordUser().equalsIgnoreCase(usu.getPasswordUser())) {
                return u;
            }
        }
        throw new LoginError("Usuario o contraseña incorrectos");
    }

    private ArrayList<User> leerUsuarios() {
        File f = new File("usuarios.dat");
        if (!f.exists()) {
            return new ArrayList<>();
        }

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("usuarios.dat"));
            return (ArrayList<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
