/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import excepciones.LoginError;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

/**
 *
 * @author 2dam
 */
public class FXMLDocumentController implements Initializable {

    private static final Logger logger = Logger.getLogger(FXMLDocumentController.class.getName());

    @FXML
    private Button buttonSQL;

    @FXML
    private Button btnFichero;

    @FXML
    private Label lblUsuario;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private Label labelUsuario;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelDireccion;

    private Dao dao;

    private User usuario;

    /**
     * Evento botón loginSQL
     *
     * @param event acción del botón
     * @throws LoginError si las credenciales son inválidas o ocurre un error en
     * la BD
     */
    @FXML
    private void loginSQL(ActionEvent event) throws LoginError {
        dao = new DaoImplementsMySQL();
        login();
    }

    /**
     * Evento botón loginFichero
     *
     * @param event acción del botón
     * @throws LoginError si las credenciales son inválidas o ocurre un error en
     * el fichero
     */
    @FXML
    private void loginFichero(ActionEvent event) throws LoginError {
        dao = new DaoImplementsFich();
        login();
    }

    /**
     * Método de login
     *
     * @throws LoginError si el login falla
     */
    private void login() throws LoginError {
        String nombre = txtUsuario.getText();
        String password = txtContrasenia.getText();

        User usu = new User();
        usu.setNameUser(nombre);
        usu.setPasswordUser(password);

        try {
            User logeado = dao.login(usu);
            mostrarVentanaUsuario(logeado);

            logger.info("Usuario autenticado: " + logeado.getNameUser());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Acceso Concedido");
            alert.setHeaderText("Inicio de sesión exitoso");
            alert.setContentText("¡Bienvenido, " + logeado.getNameUser() + "!");
            alert.showAndWait();

        } catch (LoginError e) {
            logger.warning("Error de login: " + e.getMessage());

            e.visualizarMen();
        }
    }

    /**
     * Inicializar dao
     *
     * @param url ubicación del archivo FXML
     * @param rb recursos de internacionalización
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new DaoImplementsMySQL();
        logger.info("Aplicación inicializada, DAO configurado a MySQL.");

    }

    /**
     * Método para agregar datos al objeto usuario
     *
     * @param usuario el usuario autenticado
     */
    public void setUsuario(User usuario) {
        this.usuario = usuario;
        labelUsuario.setText(usuario.getNameUser());
        labelEmail.setText(usuario.getEmail());
        labelDireccion.setText(usuario.getDirection());
    }

    /**
     * Método para mostrar la ventana del usuario logeado
     *
     * @param logeado el usuario que ha iniciado sesión correctamente
     */
    private void mostrarVentanaUsuario(User logeado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewUser.fxml"));
            loader.setController(this);
            Parent root = loader.load();

            setUsuario(logeado);

            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error al cargar vista de usuario: {0}", ex.getMessage());
        }
    }
}
