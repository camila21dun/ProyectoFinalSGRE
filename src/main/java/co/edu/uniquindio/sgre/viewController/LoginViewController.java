package co.edu.uniquindio.sgre.viewController;

import co.edu.uniquindio.sgre.exceptions.EmpleadoException;
import co.edu.uniquindio.sgre.model.Admin;
import co.edu.uniquindio.sgre.model.Empleado;
import co.edu.uniquindio.sgre.model.SGRE;
import co.edu.uniquindio.sgre.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginViewController {

    @FXML
    private Button btnInicio;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private TextField txtUser;

    @FXML
    private AnchorPane ventana;

    private final SGRE sgre = SGRE.getInstance();

    public LoginViewController() throws EmpleadoException {}

    @FXML
    void inicioSesionEvent(ActionEvent event) {
        String usuario = txtUser.getText();
        String contrasenia = txtContrasenia.getText();

        if (usuario.isEmpty() || contrasenia.isEmpty()) {
            mostrarAlerta("Error", "Por favor ingresa usuario y contraseña");
            return;
        }

        try {
            if (sgre.verificarAdmin(usuario, contrasenia)) {
                Admin admin = sgre.obtenerAdmin(usuario);
                SessionManager.getInstance().setAdmin(admin);
                SessionManager.getInstance().setUserType("admin");
                mostrarAlerta("Inicio de sesión exitoso", "Bienvenido Administrador " + usuario);
                new ViewController(ventana, "/co/edu/uniquindio/sgre/sgre.fxml");
            } else if (sgre.verificarEmpleado(usuario, contrasenia)) {
                Empleado empleado = sgre.obtenerEmpleado(usuario);
                SessionManager.getInstance().setEmpleado(empleado);
                SessionManager.getInstance().setUserType("empleado");
                mostrarAlerta("Inicio de sesión exitoso", "Bienvenido Empleado " + usuario);
                new ViewController(ventana, "/co/edu/uniquindio/sgre/sgre.fxml");
            } else if (sgre.verificarUser(usuario, contrasenia)) {
                Usuario user = sgre.obtenerUsuario(usuario);
                SessionManager.getInstance().setUsuario(user);
                SessionManager.getInstance().setUserType("usuario");
                mostrarAlerta("Inicio de sesión exitoso", "Bienvenido Usuario " + usuario);
                new ViewController(ventana, "/co/edu/uniquindio/sgre/sgre.fxml");
            } else {
                mostrarAlerta("Error", "Credenciales incorrectas");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void registrarEvent(ActionEvent event) throws IOException {
        registrarAction();
    }

    private void registrarAction() throws IOException {
        SessionManager.getInstance().setUserType("usuario2");
        new ViewController(ventana, "/co/edu/uniquindio/sgre/sgre.fxml");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    void initialize() {

    }
}
