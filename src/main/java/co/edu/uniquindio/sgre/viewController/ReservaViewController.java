package co.edu.uniquindio.sgre.viewController;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;



import co.edu.uniquindio.sgre.controller.EmpleadoController;
import co.edu.uniquindio.sgre.controller.EventoController;
import co.edu.uniquindio.sgre.controller.ReservaController;
import co.edu.uniquindio.sgre.controller.UsuarioController;
import co.edu.uniquindio.sgre.exceptions.EmpleadoException;
import co.edu.uniquindio.sgre.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.sgre.mapping.dto.EventoDto;
import co.edu.uniquindio.sgre.mapping.dto.ReservaDto;
import co.edu.uniquindio.sgre.mapping.dto.UsuarioDto;
import co.edu.uniquindio.sgre.model.*;
import co.edu.uniquindio.sgre.utils.Persistencia;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ReservaViewController {

    EventoController eventoControllerService;
    ObservableList<EventoDto> listaEventosDto = FXCollections.observableArrayList();
    EventoDto eventoSeleccionado;

    //////

    ReservaController reservaControllerService;
    ObservableList<ReservaDto> listaReservaDto = FXCollections.observableArrayList();
    ReservaDto reservaSeleccionado;

    ///////

    UsuarioController usuarioControllerService;
    ObservableList<UsuarioDto> listaUsuarioDto = FXCollections.observableArrayList();

    private UsuarioDto usuarioLogueado;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<EventoDto, String> columCapEvento;

    @FXML
    private TableColumn<ReservaDto, String> columCapReserva;

    @FXML
    private TableColumn<ReservaDto, Estado> columEstadoReserva;

    @FXML
    private TableColumn<EventoDto, String> columIdEvento;

    @FXML
    private TableColumn<ReservaDto, String> columIdReserva;

    @FXML
    private TableColumn<EventoDto, String> columNombreEvento;

    @FXML
    private TableColumn<ReservaDto, LocalDate> columFecha;

    @FXML
    private DatePicker dateFecha;

    @FXML
    private TableView<EventoDto> tablaEventos;

    @FXML
    private TableView<ReservaDto> tablaReservas;

    @FXML
    private TextField txtcapacidad;
    @FXML
    private TextField txtIdUsuario;
    private int capacidadMaximaEventoSeleccionado;


    @FXML
    void reservaEvent(ActionEvent event) throws EmpleadoException {
        if (validarCedula()) {
            registrarReserva();
        }

    }

    private void registrarReserva() {
        ReservaDto reservaDto = construirReservaDto();
        System.out.println("Intentando actualizar cliente...");
        if (datosValidos(reservaDto)) {
            System.out.println("Datos válidos para la reserva: " + reservaDto.id());
            int capacidadReserva;
            try {
                capacidadReserva = Integer.parseInt(txtcapacidad.getText());
            } catch (NumberFormatException e) {
                mostrarMensaje("Notificación reserva", "Error", "La capacidad de la reserva debe ser un número válido", Alert.AlertType.ERROR);
                return;
            }

            if (eventoSeleccionado != null) {
                int capacidadMaximaEventoSeleccionado = Integer.parseInt(eventoSeleccionado.capMax());

                if (capacidadReserva > capacidadMaximaEventoSeleccionado) {
                    mostrarMensaje("Notificación reserva", "Error", "La capacidad de la reserva excede la capacidad máxima del evento", Alert.AlertType.ERROR);
                    return;
                }


                if (reservaControllerService.agregarReserva(reservaDto)) {
                    listaReservaDto.add(reservaDto);


                  //  eventoSeleccionado.setCapMax(String.valueOf(capacidadMaximaEventoSeleccionado - capacidadReserva));


                    tablaEventos.refresh();

                    mostrarMensaje("Notificación reserva", "Reserva creada", "La reserva se ha creado con éxito", Alert.AlertType.INFORMATION);
                    limpiarCamposReserva();
                } else {
                    mostrarMensaje("Notificación reserva", "Reserva no creada", "La reserva no se ha creado", Alert.AlertType.ERROR);
                }
            } else {
                mostrarMensaje("Notificación reserva", "Error", "No se ha seleccionado un evento", Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Notificación reserva", "Reserva no creada", "Los datos ingresados son inválidos", Alert.AlertType.ERROR);
        }
        registrarAccionesSistema("Crear reserva", 1, "se creó la reserva " + reservaDto);
        System.out.println("Acción de actualización de cliente completa.");
    }




    /*

    private void enviarCorreoConfirmacion(ReservaDto reservaDto) {
        UsuarioDto usuario = usuarioControllerService.obtenerUsuario(reservaDto.id());
        String destinatario = usuario.email();  // Obtener el correo del usuario logueado
        String asunto = "Confirmación de Reserva";
        String contenido = "Estimado " + usuario.nombre() + ",\n\n" +
                "Su reserva ha sido creada con éxito. Aquí están los detalles:\n\n" +
                "ID de Reserva: " + reservaDto.id() + "\n" +
                "Evento: " + (eventoSeleccionado != null ? eventoSeleccionado.nombre() : "") + "\n" +
                "Fecha: " + reservaDto.fecha() + "\n" +
                "Capacidad: " + reservaDto.capacidad() + "\n\n" +
                "Gracias por usar nuestro servicio.";

        try {
            EmailService.enviarCorreo(destinatario, asunto, contenido);
        } catch (MessagingException e) {
            mostrarMensaje("Error de correo", "No se pudo enviar el correo de confirmación", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

     */

    private boolean validarCedula() {
        String cedulaIngresada = txtIdUsuario.getText();
        String cedulaUsuario = SessionManager.getInstance().getUsuario().getId();

        if (cedulaIngresada.equals(cedulaUsuario)) {
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

    private boolean datosValidos(ReservaDto eventoDto) {
        String mensaje = "";
        if (eventoDto.capacidad() == null || eventoDto.capacidad().equals(""))
            mensaje += "La capacidad es obligatoria \n";
        if (eventoDto.id() == null || eventoDto.id().equals(""))
            mensaje += "Ingrese su identificacion \n";
        if (eventoDto.fecha() == null || eventoDto.fecha().isBefore(LocalDate.now().plusDays(1)))
            mensaje += "La fecha debe ser posterior al día actual \n";
        if (eventoDto.eventoId() == null)
            mensaje += "Debe seleccionar un evento \n";
        if (mensaje.equals("")) {
            return true;
        } else {
            mostrarMensaje("Notificación reserva", "Datos inválidos", mensaje, Alert.AlertType.WARNING);
            return false;
        }
    }

    @FXML
    void cancelarEvent(ActionEvent event) throws IOException {
        cancelarAccion();

    }

    private void cancelarAccion() throws IOException {
        if (reservaSeleccionado != null) {

            Persistencia.eliminarReserva(reservaSeleccionado.id());

            listaReservaDto.remove(reservaSeleccionado);
            reservaSeleccionado = null;
            tablaEventos.getSelectionModel().clearSelection();
            limpiarCamposReserva();
            mostrarMensaje("Notificación reserva", "Reserva cancelada", "la reserva se ha cancelado con éxito", Alert.AlertType.INFORMATION);
        } else {
            mostrarMensaje("Notificación reserva", "Reserva no seleccionado", "Por favor, seleccione un reserva de la tabla", Alert.AlertType.WARNING);
        }
        registrarAccionesSistema("Cancelar reserva", 1, "se elimino la reserva ");
    }

    @FXML
    void initialize() {
        eventoControllerService = new EventoController();
        reservaControllerService = new ReservaController();
        usuarioControllerService = new UsuarioController();
        intiView();
    }

    private void intiView() {
        initDataBinding();
        obtenerReservas();
        obtenerEvento();
        obtenerEventoDisponibles();
        tablaEventos.getItems().clear();
        tablaEventos.setItems(listaEventosDto);
        tablaReservas.getItems().clear();
        tablaReservas.setItems(listaReservaDto);
        listenerSelection();
    }

    private void obtenerEventoDisponibles() {
        listaEventosDto.clear();
        ObservableList<EventoDto> todosEventos = FXCollections.observableArrayList(eventoControllerService.obtenerEventos());
        listaEventosDto.addAll(todosEventos);
    }

    private void initDataBinding() {
        columNombreEvento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        columIdEvento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        columCapEvento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().capMax()));

        columCapReserva.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().capacidad()));
        columEstadoReserva.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().estado()));
        columIdReserva.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        columFecha.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().fecha()));
    }

    private void obtenerReservas() {
        listaReservaDto.clear();
        List<ReservaDto> todasReservas = reservaControllerService.obtenerReservas();
        listaReservaDto.addAll(todasReservas);
    }

    private void obtenerEvento() {
        listaEventosDto.addAll(eventoControllerService.obtenerEventos());
    }

    private void listenerSelection() {
        tablaReservas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            reservaSeleccionado = newSelection;
            mostrarInformacionEvento(reservaSeleccionado);
        });
        tablaEventos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            eventoSeleccionado = newSelection;
        });
    }

    private ReservaDto construirReservaDto() {
        return new ReservaDto(
                txtIdUsuario.getText(),
                txtcapacidad.getText(),
                eventoSeleccionado != null ? new Evento(eventoSeleccionado.id(), eventoSeleccionado.nombre(), eventoSeleccionado.capMax()) : null,
                dateFecha.getValue(),
                Estado.PENDIENTE
        );
    }

    private void limpiarCamposReserva() {
        txtIdUsuario.setText("");
        txtcapacidad.setText("");
        dateFecha.setValue(null);
        eventoSeleccionado = null;
        registrarAccionesSistema("Limpiar campos de la reserva", 1, "se limpiaron los campos");
    }

    private void mostrarInformacionEvento(ReservaDto reservaSeleccionado) {
        if (reservaSeleccionado != null) {
            txtcapacidad.setText(reservaSeleccionado.capacidad());
            txtIdUsuario.setText(reservaSeleccionado.id());
            dateFecha.setValue(reservaSeleccionado.fecha());
        }
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    public void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        Persistencia.guardaRegistroLog(mensaje, nivel, accion);
    }
}
