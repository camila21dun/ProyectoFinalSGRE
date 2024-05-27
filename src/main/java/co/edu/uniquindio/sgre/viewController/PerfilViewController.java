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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import co.edu.uniquindio.sgre.model.Usuario;
import javafx.stage.Stage;

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
        String cedulaUsuario = SessionManager.getInstance().getUsuario().getId();
        String nombreUsuario = txtCedula.getText();
        try {
            Usuario usuarioActual = sgre.obtenerUsuarioPorId(cedulaUsuario);

            if (usuarioActual == null) {
                mostrarAlerta("El usuario a actualizar no existe");
                return;
            }

            Usuario usuarioActualizado = new Usuario(
                    usuarioActual.getId(),
                    txtNombre.getText(),
                    txtEmail.getText(),
                    nombreUsuario,
                    txtContrasenia.getText()
            );

            sgre.actualizarUsuario(usuarioActual.getId(), usuarioActualizado);
            SessionManager.getInstance().setUsuario(sgre.obtenerUsuarioPorId(usuarioActual.getId()));
            Persistencia.actualizarUsuarioTxt(usuarioActual.getId(), usuarioActualizado);
            Persistencia.actualizarUsuarioBinario(usuarioActual.getId(), usuarioActualizado);
            Persistencia.actualizarUsuarioXML(usuarioActual.getId(), usuarioActualizado);


            Usuario finalUsuarioActual = usuarioActual;
            UsuarioDto usuarioDtoExistente = listaUsuariosDto.stream()
                    .filter(dto -> dto.id().equals(finalUsuarioActual.getId()))
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
    private boolean validarCedula(Usuario usuarioActual) {
        String cedulaIngresada = txtCedula.getText();
        String cedulaUsuario = SessionManager.getInstance().getUsuario().getId();
        return cedulaIngresada.equals(cedulaUsuario);
    }


    private void limpiarTexto() {
        txtNombre.setText("");
        txtCedula.setText("");
        txtEmail.setText("");
        txtUser.setText("");
        txtContrasenia.setText("");
    }


    @FXML
    void volverEvent(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/sgre/login.fxml"));
        Parent loginRoot = fxmlLoader.load();
        Scene loginScene = new Scene(loginRoot);
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");
        loginStage.setScene(loginScene);
        loginStage.show();
        Stage currentStage = (Stage) ventana.getScene().getWindow();
        currentStage.close();
        registrarAccionesSistema("Volver atras", 1, "Se volvio al login ");
    }

    @FXML
    void EliminarEvent(ActionEvent event) throws IOException {
        eliminarUsuario();

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

    private void eliminarUsuario() throws IOException {
        String idUsuario = txtCedula.getText();
        boolean usuarioEliminado = false;

        for (UsuarioDto usuarioDto : listaUsuariosDto) {
            if (usuarioDto.id().equals(idUsuario)) {
                listaUsuariosDto.remove(usuarioDto);
                Persistencia.eliminarUsuario(idUsuario);
                Persistencia.eliminarUsuarioBinario(idUsuario);
                Persistencia.eliminarUsuarioXML(idUsuario);
                usuarioEliminado = true;
                break;
            }
        }

        if (usuarioEliminado) {
            limpiarCamposUsuario();
            mostrarMensaje("Notificación usuario", "Usuario eliminado", "El usuario se ha eliminado con éxito", Alert.AlertType.INFORMATION);
            registrarAccionesSistema("Eliminar usuario", 1, "Se eliminó el usuario con ID: " + idUsuario);
            volverEvent(new ActionEvent());
        } else {
            mostrarMensaje("Notificación usuario", "Usuario no encontrado", "No se encontró ningún usuario con el ID especificado", Alert.AlertType.WARNING);
        }
    }
    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }
    private void limpiarCamposUsuario() {
        txtNombre.setText("");
        txtCedula.setText("");
        txtEmail.setText("");
        txtUser.setText("");
        txtContrasenia.setText("");
    }

    @FXML
    void initialize() {
        cargarInformacionUsuario();
    }

    private void cargarInformacionUsuario() {
        Usuario usuarioLogueado = SessionManager.getInstance().getUsuario();

        if (usuarioLogueado != null) {
            txtCedula.setText(usuarioLogueado.getId());
            txtNombre.setText(usuarioLogueado.getNombre());
            txtEmail.setText(usuarioLogueado.getEmail());
            txtUser.setText(usuarioLogueado.getUsuario());
            txtContrasenia.setText(usuarioLogueado.getContrasenia());
        } else {
            mostrarAlerta("No se encontró información del usuario.");
        }
    }
    public void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        Persistencia.guardaRegistroLog(mensaje, nivel, accion);
    }






}
