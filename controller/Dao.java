/**
 * Interfaz que define el contrato para el acceso a datos de usuarios.
 * Implementaciones pueden usar diferentes fuentes como base de datos o ficheros.
 * 
 */

package controller;

import excepciones.LoginError;
import model.User;

public interface Dao {

    public User login(User usu) throws LoginError;
}
