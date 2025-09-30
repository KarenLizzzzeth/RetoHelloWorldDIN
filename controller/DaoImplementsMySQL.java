/**
 * Implementación del DAO que gestiona usuarios a través de una base de datos MySQL.
 * Utiliza una consulta preparada para validar credenciales y recuperar datos del usuario.
 * La configuración de conexión se obtiene desde un archivo de propiedades.
 *
 */
package controller;

import excepciones.LoginError;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.User;

public class DaoImplementsMySQL implements Dao {

    // Atributo para conexion
    private ResourceBundle configFile;
    private String urlBD, userBD, passwordBD;
    // Atributos
    private Connection con;
    private PreparedStatement stmt;
    // Sentencias SQL
    final String LOGIN = "SELECT * FROM USUARIO WHERE nameUser = ? AND PasswordUser = ?";

    /**
     * Constructor que inicializa los parámetros de conexión a partir del
     * archivo de configuración. El archivo debe estar ubicado en la ruta
     * "model.configclass.properties".
     */
    public DaoImplementsMySQL() {
        this.configFile = ResourceBundle.getBundle("model.configclass");
        this.urlBD = this.configFile.getString("Conn");
        this.userBD = this.configFile.getString("DBUser");
        this.passwordBD = this.configFile.getString("DBPass");
    }

    private void openConnection() {
        try {
            con = DriverManager.getConnection(urlBD, this.userBD, this.passwordBD);

        } catch (SQLException ex) {
            Logger.getLogger(DaoImplementsMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void closeConnection() throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
        if (con != null) {
            con.close();
        }
    }

    private void closeResult(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Realiza el proceso de autenticación de un usuario consultando la base de
     * datos.
     */
    @Override
    public User login(User usu) throws LoginError {
        ResultSet rs = null;
        openConnection();
        User usuarioAutenticado = null;

        try {
            stmt = con.prepareStatement(LOGIN);

            stmt.setString(1, usu.getNameUser());
            stmt.setString(2, usu.getPasswordUser());

            rs = stmt.executeQuery();

            if (!rs.next()) {
                throw new LoginError("Usuario o password incorrecto");
            } else {
                usuarioAutenticado = new User();
                usuarioAutenticado.setIdUser(rs.getInt("IdUser"));
                usuarioAutenticado.setNameUser(rs.getString("NameUser"));
                usuarioAutenticado.setPasswordUser(rs.getString("PasswordUser"));
                usuarioAutenticado.setEmail(rs.getString("Email"));
                usuarioAutenticado.setDirection(rs.getString("Direction"));
            }
        } catch (SQLException e) {
            throw new LoginError("Error con el SQL");
        } finally {
            closeResult(rs);
            try {
                closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(DaoImplementsMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return usuarioAutenticado;
    }
}
