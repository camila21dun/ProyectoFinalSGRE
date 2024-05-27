package co.edu.uniquindio.sgre.viewController;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import co.edu.uniquindio.sgre.controller.ReservaController;
import co.edu.uniquindio.sgre.controller.UsuarioController;
import co.edu.uniquindio.sgre.mapping.dto.ReservaDto;
import co.edu.uniquindio.sgre.mapping.dto.UsuarioDto;
import co.edu.uniquindio.sgre.model.Estado;
import co.edu.uniquindio.sgre.model.Reserva;
import co.edu.uniquindio.sgre.model.Usuario;
import co.edu.uniquindio.sgre.utils.Persistencia;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class GestionReserva {

    private ReservaController reservaControllerService;
    private ObservableList<ReservaDto> listaReservaDto = FXCollections.observableArrayList();
    private ReservaDto reservaSeleccionado;


    /////////////

    UsuarioController usuarioControllerService;
    ObservableList<UsuarioDto> listaUsuariosDto = FXCollections.observableArrayList();
    UsuarioDto usuarioSeleccionado;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Estado> cbEstados;

    @FXML
    private TableColumn<ReservaDto, String> columCapReserva;

    @FXML
    private TableColumn<ReservaDto, Estado> columEstadoReserva;

    @FXML
    private TableColumn<ReservaDto, LocalDate> columFecha;

    @FXML
    private TableColumn<ReservaDto, String> columIdReserva;

    @FXML
    private TableView<ReservaDto> tablaReservas;

    @FXML
    private TextField txtCedula;

    @FXML
    private PasswordField txtContrasenia;
    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TableView<UsuarioDto> tableUsuarios;

    @FXML
    private TableColumn<UsuarioDto,String> tcCedula;

    @FXML
    private TableColumn<UsuarioDto,String> tcCorreo;

    @FXML
    private TableColumn<UsuarioDto,String> tcNombre;

    @FXML
    void actualizarReservaEvent(ActionEvent event) throws IOException {
        actualizarReservaAction();
    }


    private void actualizarReservaAction() throws IOException {
        if (reservaSeleccionado == null) {
            mostrarMensaje("Notificación reserva", "Reserva no seleccionada", "Por favor, seleccione una reserva de la lista", Alert.AlertType.WARNING);
            return;
        }

        Estado nuevoEstado = cbEstados.getValue();
        if (nuevoEstado == null) {
            mostrarMensaje("Notificación reserva", "Estado no seleccionado", "Por favor, seleccione un estado de la lista", Alert.AlertType.WARNING);
            return;
        }

        Reserva reservaActualizada = new Reserva(
                reservaSeleccionado.id(),
                reservaSeleccionado.capacidad(),
                reservaSeleccionado.eventoId(),
                reservaSeleccionado.fecha(),
                nuevoEstado
        );

        Persistencia.actualizarReserva(reservaSeleccionado.id(), reservaActualizada);

        listaReservaDto.set(
                listaReservaDto.indexOf(reservaSeleccionado),
                new ReservaDto(
                        reservaActualizada.getId(),
                        reservaActualizada.getCapacidad(),
                        reservaActualizada.getEvento(),
                        reservaActualizada.getFecha(),
                        reservaActualizada.getEstado()
                )
        );

        tablaReservas.refresh();

        mostrarMensaje("Notificación reserva", "Reserva actualizada", "El estado de la reserva se ha actualizado con éxito", Alert.AlertType.INFORMATION);
        registrarAccionesSistema("Actualizar reserva", 1, "Se actualizó la reserva ");
    }

    @FXML
    void initialize() {
        reservaControllerService = new ReservaController();
        usuarioControllerService = new UsuarioController();
        cbEstados.setItems(FXCollections.observableArrayList(Estado.values()));
        intiView();
    }
    private void intiView() {
        initDataBinding();
        obtenerReservas();
        obtenerUsuarios();

        tablaReservas.getItems().clear();
        tablaReservas.setItems(listaReservaDto);

        tableUsuarios.getItems().clear();
        tableUsuarios.setItems(listaUsuariosDto);
        listenerSelection();
    }
    private void listenerSelection() {
        tablaReservas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            reservaSeleccionado = newSelection;
        });

        tableUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            usuarioSeleccionado = newSelection;
            mostrarInformacionusuario(usuarioSeleccionado);
        });

    }
    private void mostrarInformacionusuario(UsuarioDto usuarioSeleccionado) {
        if(usuarioSeleccionado != null){
            txtNombre.setText(usuarioSeleccionado.nombre());
            txtCedula.setText(usuarioSeleccionado.id());
            txtCorreo.setText(usuarioSeleccionado.email());
            txtUsuario.setText(usuarioSeleccionado.usuario());
            txtContrasenia.setText(usuarioSeleccionado.contrasenia());

        }
    }
    private void obtenerUsuarios() {
        listaUsuariosDto.addAll(usuarioControllerService.obtenerUsuario());
    }

    private void initDataBinding() {
        columIdReserva.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        columCapReserva.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().capacidad()));
        columFecha.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().fecha()));
        columEstadoReserva.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().estado()));

        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcCedula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().email()));
    }
    private void obtenerReservas() {
        listaReservaDto.clear();
        ObservableList<ReservaDto> todasReservas = FXCollections.observableArrayList(reservaControllerService.obtenerReservas());
        listaReservaDto.addAll(todasReservas);
    }

    private void mostrarMensaje(String titulo, String cabecera, String contenido, Alert.AlertType tipoAlerta) {
        Alert alert = new Alert(tipoAlerta);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecera);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
    @FXML
    void eliminarUsuarioAction(ActionEvent event) throws IOException {
        eliminarUsuarioEvent();

    }

    private void eliminarUsuarioEvent() throws IOException {
        boolean usuarioEliminado = false;
        if (usuarioSeleccionado != null) {

            Persistencia.eliminarUsuario(usuarioSeleccionado.id());
            Persistencia.eliminarUsuarioBinario(usuarioSeleccionado.id());
            Persistencia.eliminarUsuarioXML(usuarioSeleccionado.id());



            listaUsuariosDto.remove(usuarioSeleccionado);
            usuarioSeleccionado = null;
            tableUsuarios.getSelectionModel().clearSelection();
            limpiarCamposUsuario();
            mostrarMensaje("Notificación usuario", "Usuario eliminado", "El usuario se ha eliminado con éxito", Alert.AlertType.INFORMATION);
        } else {
            mostrarMensaje("Notificación usuario", "Usuario no seleccionado", "Por favor, seleccione un usuario de la lista", Alert.AlertType.WARNING);
        }
        registrarAccionesSistema("Eliminar usuario", 1, "se elimino el usuario ");
    }
    private void limpiarCamposUsuario() {
        txtNombre.setText("");
        txtCedula.setText("");
        txtCorreo.setText("");
        txtUsuario.setText("");
        txtContrasenia.setText("");
    }

    public void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        Persistencia.guardaRegistroLog(mensaje, nivel, accion);
    }

    @FXML
    void actualizarEvent(ActionEvent event) throws IOException {

        actualizarUsuario();
    }
    private void actualizarUsuario() throws IOException {
        boolean usuarioActualizado = false;

        Usuario usuarioActualizad = new Usuario(
                txtCedula.getText(),
                txtNombre.getText(),
                txtCorreo.getText(),
                txtUsuario.getText(),
                txtContrasenia.getText()
        );

        if (usuarioSeleccionado != null) {

            Persistencia.actualizarUsuarioBinario(usuarioSeleccionado.id(), usuarioActualizad);
            Persistencia.actualizarUsuarioXML(usuarioSeleccionado.id(), usuarioActualizad);
            Persistencia.actualizarUsuarioTxt(usuarioSeleccionado.id(),usuarioActualizad);



            listaUsuariosDto.remove(usuarioSeleccionado);

            listaUsuariosDto.add(new UsuarioDto(
                    usuarioActualizad.getId(),
                    usuarioActualizad.getNombre(),
                    usuarioActualizad.getEmail(),
                    usuarioActualizad.getUsuario(),
                    usuarioActualizad.getContrasenia()
            ));


            tableUsuarios.refresh();

            mostrarMensaje("Notificación usuario", "Usuario actualizado", "El usuario se ha actualizado con éxito", Alert.AlertType.INFORMATION);

            limpiarCamposUsuario();
        } else {

            mostrarMensaje("Notificación usuario", "Usuario no seleccionado", "Por favor, seleccione un usuario de la lista", Alert.AlertType.WARNING);
        }

        registrarAccionesSistema("Actualizar usuario", 1, "Se actualizó el usuario " + usuarioActualizado);
    }

}
