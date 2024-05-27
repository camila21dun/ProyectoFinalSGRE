package co.edu.uniquindio.sgre.viewController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.sgre.controller.UsuarioController;
import co.edu.uniquindio.sgre.exceptions.EmpleadoException;
import co.edu.uniquindio.sgre.exceptions.UsuarioException;
import co.edu.uniquindio.sgre.mapping.dto.UsuarioDto;
import co.edu.uniquindio.sgre.model.SGRE;
import co.edu.uniquindio.sgre.utils.Persistencia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import co.edu.uniquindio.sgre.model.Usuario;

public class PerfilViewController {

    UsuarioController usuarioControllerService;
    ObservableList<UsuarioDto> listaUsuariosDto = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizar;

    @FXML
    private TableColumn<?, ?> tabCapacidad;

    @FXML
    private TableColumn<?, ?> tabFecha;

    @FXML
    private TableColumn<?, ?> tabId;

    @FXML
    private TableView<?> tablaHistorialR;

    @FXML
    private TextField txtCedula;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtUser;

    @FXML
    private AnchorPane ventana;

    private final SGRE sgre = SGRE.getInstance();

    public PerfilViewController() throws EmpleadoException {
    }

    @FXML
    void actualizarUserEvent(ActionEvent event) {
        if (validarCampos() && validarCedula()) {
            actualizarUsuarioAction();
        }
    }

    private boolean validarCedula() {
        String cedulaIngresada = txtCedula.getText();
        String cedulaUsuario = SessionManager.getInstance().getUsuario().getId();

        if (!cedulaIngresada.equals(cedulaUsuario)) {
            mostrarAlerta("La cédula ingresada no coincide con la del usuario que inició sesión.");
            return false;
        }

        return true;
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

    private boolean validarCampos() {
        if (!validarNumero(txtCedula.getText())) {
            mostrarAlerta("Cédula inválida. Ingrese solo números.");
            return false;
        }
        if (contieneNumeros(txtNombre.getText())) {
            mostrarAlerta("El nombre no debe contener números.");
            return false;
        }

        return true;
    }

    private boolean validarNumero(String input) {
        return input.matches("\\d+");
    }

    private boolean contieneNumeros(String input) {
        return input.matches(".*\\d+.*");
    }

    private void actualizarUsuarioAction() {
        String cedula = txtCedula.getText();
        try {
            Usuario usuarioActualizado = new Usuario(
                    txtCedula.getText(),
                    txtNombre.getText(),
                    txtEmail.getText(),
                    txtUser.getText(),
                    txtContrasenia.getText()
            );

            sgre.actualizarUsuario(cedula, usuarioActualizado);
            SessionManager.getInstance().setUsuario(sgre.obtenerUsuario(cedula));
            Persistencia.actualizarUsuarioTxt(cedula, usuarioActualizado);
            Persistencia.actualizarUsuarioBinario(cedula, usuarioActualizado);
            Persistencia.actualizarUsuarioXML(cedula, usuarioActualizado);


            UsuarioDto usuarioDtoExistente = listaUsuariosDto.stream()
                    .filter(dto -> dto.id().equals(cedula))
                    .findFirst()
                    .orElse(null);

            if (usuarioDtoExistente != null) {
                listaUsuariosDto.remove(usuarioDtoExistente);
            }

            listaUsuariosDto.add(new UsuarioDto(
                    usuarioActualizado.getId(),
                    usuarioActualizado.getNombre(),
                    usuarioActualizado.getEmail(),
                    usuarioActualizado.getUsuario(),
                    usuarioActualizado.getContrasenia()
            ));




            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Se ha actualizado correctamente.");
            alert.show();
            limpiarTexto();

        } catch (UsuarioException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setHeaderText(null);
            alert.show();
        }
    }

    private void limpiarTexto() {
        txtNombre.setText("");
        txtCedula.setText("");
        txtEmail.setText("");
        txtUser.setText("");
        txtContrasenia.setText("");
    }

    @FXML
    void cerrarSesionEvent(ActionEvent event) {
        // Lógica para cerrar sesión
    }

    @FXML
    void volverEvent(ActionEvent event) {
        // Lógica para volver a la ventana anterior
    }

    @FXML
    void EliminarEvent(ActionEvent event) {
        // Lógica para eliminar el usuario
    }

    @FXML
    void initialize() {
        // Inicialización si es necesaria
    }


}
