package co.edu.uniquindio.sgre.viewController;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import co.edu.uniquindio.sgre.controller.EmpleadoController;
import co.edu.uniquindio.sgre.controller.EventoController;
import co.edu.uniquindio.sgre.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.sgre.mapping.dto.EventoDto;
import co.edu.uniquindio.sgre.model.Empleado;
import co.edu.uniquindio.sgre.model.Evento;
import co.edu.uniquindio.sgre.utils.Persistencia;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class EventoViewController {

    EventoController eventoControllerService;
    ObservableList<EventoDto> listaEventosDto = FXCollections.observableArrayList();
    EventoDto eventoSeleccionado;

    ////

    EmpleadoController empleadoControllerService;
    ObservableList<EmpleadoDto> listaEmpleadosDto = FXCollections.observableArrayList();
    EmpleadoDto empleadoSeleccionado;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<EventoDto, String> columCapEvento;

    @FXML
    private TableColumn<EmpleadoDto, String> columIdEmpleado;

    @FXML
    private TableColumn<EventoDto, String> columIdEvento;

    @FXML
    private TableColumn<EmpleadoDto, String> columNombreEmpleado;

    @FXML
    private TableColumn<EventoDto, String> columNombreEvento;

    @FXML
    private DatePicker dateFecha;

    @FXML
    private TableView<EmpleadoDto> tablaEmpleados;

    @FXML
    private TableView<EventoDto> tablaEventos;

    @FXML
    private TextField txtCapMax;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;

    @FXML
    private AnchorPane ventana;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnBuscar;
    @FXML
    private TextField txtBuscar;

    @FXML
    void initialize() {
        eventoControllerService = new EventoController();
        empleadoControllerService = new EmpleadoController();
        intiView();
    }


    private void obtenerEmpleadosDisponibles() {
        listaEmpleadosDto.clear();
        ObservableList<EmpleadoDto> todosEmpleados = FXCollections.observableArrayList(empleadoControllerService.obtenerEmpleados());
        listaEmpleadosDto.addAll(todosEmpleados);
    }


    private void intiView() {
        initDataBinding();
        obtenerEvento();
        obtenerEmpleadosDisponibles();
        tablaEventos.getItems().clear();
        tablaEventos.setItems(listaEventosDto);
        tablaEmpleados.getItems().clear();
        tablaEmpleados.setItems(listaEmpleadosDto);
        listenerSelection();
    }


    private void initDataBinding() {


        columNombreEvento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        columIdEvento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        columCapEvento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().capMax()));
        /////////
        columIdEmpleado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        columNombreEmpleado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));


    }

    private void obtenerEvento() {
        listaEventosDto.addAll(eventoControllerService.obtenerEventos());
    }

    private void obtenerEmpleados() {
        listaEmpleadosDto.addAll(empleadoControllerService.obtenerEmpleados());
    }

    private void listenerSelection() {
        tablaEventos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            eventoSeleccionado = newSelection;
            mostrarInformacionEvento(eventoSeleccionado);
        });
        tablaEmpleados.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            empleadoSeleccionado = newSelection;
        });
    }

    @FXML
    void agregarEvent(ActionEvent event) {
        agregarEventAction();

    }

    private void agregarEventAction() {
        EventoDto eventoDto = construirEventoDto();
        if (datosValidos(eventoDto)) {
            if (eventoControllerService.agregarEvento(eventoDto)) {
                listaEventosDto.add(eventoDto);
                mostrarMensaje("Notificación evento", "Evento creado", "El evento se ha creado con éxito", Alert.AlertType.INFORMATION);
                limpiarCamposEvento();
            } else {
                mostrarMensaje("Notificación evento", "Evento no creado", "El evento no se ha creado", Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Notificación evento", "Evento no creado", "Los datos ingresados son inválidos", Alert.AlertType.ERROR);
        }
        registrarAccionesSistema("Crear evento", 1, "se creó el evento " + eventoDto);
    }

    private boolean datosValidos(EventoDto eventoDto) {
        String mensaje = "";

        if (eventoDto.nombre() == null || eventoDto.nombre().equals(""))
            mensaje += "El nombre es obligatorio \n";
        if (eventoDto.id() == null || eventoDto.id().equals(""))
            mensaje += "El id es obligatorio \n";
        else if (idEventoExiste(eventoDto.id()))
            mensaje += "El id ya está asignado a otro evento \n";
        if (eventoDto.descripcion() == null || eventoDto.descripcion().equals(""))
            mensaje += "La descripción es obligatoria \n";
        if (eventoDto.empleadoAsignadoId() == null)
            mensaje += "Debe seleccionar un empleado \n";
        if (eventoDto.fecha() == null || eventoDto.fecha().isBefore(LocalDate.now().plusDays(1)))
            mensaje += "La fecha debe ser posterior al día actual \n";

        if (mensaje.equals("")) {
            return true;
        } else {
            mostrarMensaje("Notificación evento", "Datos inválidos", mensaje, Alert.AlertType.WARNING);
            return false;
        }
    }

    private boolean idEventoExiste(String id) {
        return listaEventosDto.stream().anyMatch(evento -> evento.id().equals(id));
    }


    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    private EventoDto construirEventoDto() {
        return new EventoDto(
                txtId.getText(),
                txtNombre.getText(),
                txtDescripcion.getText(),
                dateFecha.getValue(),
                txtCapMax.getText(),
                empleadoSeleccionado != null ? new Empleado(empleadoSeleccionado.id(), empleadoSeleccionado.nombre(), empleadoSeleccionado.email()) : null
        );
    }

    private void limpiarCamposEvento() {
        txtNombre.setText("");
        txtId.setText("");
      //  txtEmpleado.setText("");
        txtCapMax.setText("");
        txtDescripcion.setText("");
        dateFecha.setValue(null);
        empleadoSeleccionado = null;
        registrarAccionesSistema("Limpiar campos de evento", 1, "se limpiaron los campos");
    }

    @FXML
    void ActualizarEvent(ActionEvent event) throws IOException {
         actualizarEventoAction();
    }

    private void actualizarEventoAction() throws IOException {
        boolean eventoActualizado = false;

        Evento eventoActualizad = new Evento(
                txtId.getText(),
                txtNombre.getText(),
                txtDescripcion.getText(),
                dateFecha.getValue(),
                txtCapMax.getText(),
                empleadoSeleccionado != null ? new Empleado(empleadoSeleccionado.id(), empleadoSeleccionado.nombre(), empleadoSeleccionado.email()) : null
        );

        if (eventoSeleccionado != null) {

            Persistencia.actualizarEvento(eventoSeleccionado.id(), eventoActualizad);

            listaEventosDto.remove(eventoSeleccionado);

            listaEventosDto.add(new EventoDto(
                    eventoActualizad.getId(),
                    eventoActualizad.getNombre(),
                    eventoActualizad.getDescripcion(),
                    eventoActualizad.getFecha(),
                    eventoActualizad.getCapMax(),
                    eventoActualizad.getEmpleadoAsignado()
            ));


            tablaEventos.refresh();

            mostrarMensaje("Notificación evento", "Esuario actualizado", "El evento se ha actualizado con éxito", Alert.AlertType.INFORMATION);

            limpiarCamposEvento();
        } else {

            mostrarMensaje("Notificación evento", "Evento no seleccionado", "Por favor, seleccione un evento de la lista", Alert.AlertType.WARNING);
        }

        registrarAccionesSistema("Actualizar evento", 1, "Se actualizó el evento " + eventoActualizado);
    }

    @FXML
    void CancelarEvent(ActionEvent event) {
        cancelarEventoAction();
    }



    @FXML
    void limpiarEvent(ActionEvent event) {
        limpiarCamposEvento();
    }

    @FXML
    void EliminarEvent(ActionEvent event) throws IOException {
        eliminarEvent();
    }
    private void eliminarEvent() throws IOException {
        if (eventoSeleccionado != null) {

            Persistencia.eliminarEvento(eventoSeleccionado.id());

            listaEventosDto.remove(eventoSeleccionado);
            eventoSeleccionado = null;
            tablaEventos.getSelectionModel().clearSelection();
            limpiarCamposEvento();
            mostrarMensaje("Notificación evento", "Evento eliminado", "El evento se ha eliminado con éxito", Alert.AlertType.INFORMATION);
        } else {
            mostrarMensaje("Notificación evento", "Evento no seleccionado", "Por favor, seleccione un evento de la tabla", Alert.AlertType.WARNING);
        }
        registrarAccionesSistema("Eliminar evento", 1, "se elimino el evento ");
    }


    private void cancelarEventoAction() {

    }

    @FXML
    void buscarEvent(ActionEvent event) {
        buscarAction();

    }

    private void buscarAction() {
        String filtro = txtBuscar.getText().trim();
        if (filtro.isEmpty()) {
            tablaEventos.setItems(listaEventosDto);
        } else {
            ObservableList<EventoDto> eventosFiltrados = FXCollections.observableArrayList(
                    listaEventosDto.stream()
                            .filter(evento ->
                                    evento.nombre().toLowerCase().contains(filtro.toLowerCase()) ||
                                            evento.id().toLowerCase().contains(filtro.toLowerCase()) ||
                                            evento.capMax().toLowerCase().contains(filtro.toLowerCase()) ||
                                            (evento.fecha() != null && evento.fecha().toString().contains(filtro))
                            )
                            .collect(Collectors.toList())
            );
            tablaEventos.setItems(eventosFiltrados);
        }
        registrarAccionesSistema("Buscar evento", 1, "se aplico el filtro para obtener el evento que contenga  " + filtro);
    }


    private void mostrarInformacionEvento(EventoDto eventoSeleccionado) {
        if(eventoSeleccionado != null){
            txtNombre.setText(eventoSeleccionado.nombre());
            txtId.setText(eventoSeleccionado.id());
            txtCapMax.setText(eventoSeleccionado.capMax());
            txtDescripcion.setText(eventoSeleccionado.descripcion());
          //  txtEmpleado.setText(eventoSeleccionado.empleadoAsignadoId() != null ? eventoSeleccionado.empleadoAsignadoId().getNombre() : "");
            dateFecha.setValue(eventoSeleccionado.fecha());
        }
    }

    public void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        Persistencia.guardaRegistroLog(mensaje, nivel, accion);
    }
}