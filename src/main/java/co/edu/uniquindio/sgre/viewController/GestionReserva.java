package co.edu.uniquindio.sgre.viewController;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import co.edu.uniquindio.sgre.controller.ReservaController;
import co.edu.uniquindio.sgre.mapping.dto.ReservaDto;
import co.edu.uniquindio.sgre.model.Estado;
import co.edu.uniquindio.sgre.model.Reserva;
import co.edu.uniquindio.sgre.utils.Persistencia;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GestionReserva {

    private ReservaController reservaControllerService;
    private ObservableList<ReservaDto> listaReservaDto = FXCollections.observableArrayList();
    private ReservaDto reservaSeleccionado;

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
        cbEstados.setItems(FXCollections.observableArrayList(Estado.values()));
        intiView();
    }
    private void intiView() {
        initDataBinding();
        obtenerReservas();

        tablaReservas.getItems().clear();
        tablaReservas.setItems(listaReservaDto);
        listenerSelection();
    }
    private void listenerSelection() {
        tablaReservas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            reservaSeleccionado = newSelection;
        });

    }

    private void initDataBinding() {
        columIdReserva.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        columCapReserva.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().capacidad()));
        columFecha.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().fecha()));
        columEstadoReserva.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().estado()));

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

    public void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        Persistencia.guardaRegistroLog(mensaje, nivel, accion);
    }
}
