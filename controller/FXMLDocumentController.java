/**
 * Controlador principal de la interfaz FXML para el login de usuarios.
 * Gestiona la autenticación mediante DAO, ya sea desde base de datos MySQL o desde fichero.
 * Muestra la información del usuario autenticado en una nueva vista.
 *
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

public class FXMLDocumentController implements Initializable {

    private static final Logger logger = Logger.getLogger(FXMLDocumentController.class.getName());

    /**
     * Botón para iniciar sesión usando MySQL.
     */
    @FXML
    private Button buttonSQL;

    /**
     * Botón para iniciar sesión usando fichero.
     */
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

    /**
     * DAO utilizado para autenticar usuarios.
     */
    private Dao dao;

    /**
     * Usuario autenticado.
     */
    private User usuario;

    /**
     * Evento del botón que configura el DAO para usar MySQL y realiza el login.
     */
    @FXML
    private void loginSQL(ActionEvent event) throws LoginError {
        dao = new DaoImplementsMySQL();
        login();
    }

    /**
     * Evento del botón que configura el DAO para usar fichero y realiza el
     * login.
     *
     */
    @FXML
    private void loginFichero(ActionEvent event) throws LoginError {
        dao = new DaoImplementsFich();
        login();
    }

    /**
     * Método que realiza el proceso de login usando el DAO configurado. Si el
     * login es exitoso, se muestra la vista del usuario.
     *
     * @throws LoginError Si el login falla por credenciales incorrectas o error
     * de acceso.
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
     * Inicializa el controlador y configura el DAO por defecto a MySQL.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new DaoImplementsMySQL();
        logger.info("Aplicación inicializada, DAO configurado a MySQL.");

    }

    /**
     * Asigna los datos del usuario autenticado a las etiquetas de la vista.
     *
     * @param usuario El usuario autenticado.
     */
    public void setUsuario(User usuario) {
        this.usuario = usuario;
        labelUsuario.setText(usuario.getNameUser());
        labelEmail.setText(usuario.getEmail());
        labelDireccion.setText(usuario.getDirection());
    }

    /**
     * Carga y muestra la vista del usuario autenticado.
     *
     * @param logeado El usuario que ha iniciado sesión correctamente.
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
