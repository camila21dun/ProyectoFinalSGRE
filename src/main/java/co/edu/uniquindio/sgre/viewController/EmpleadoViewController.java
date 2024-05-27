package co.edu.uniquindio.sgre.viewController;

import co.edu.uniquindio.sgre.controller.EmpleadoController;
import co.edu.uniquindio.sgre.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.sgre.model.Empleado;
import co.edu.uniquindio.sgre.utils.Persistencia;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import co.edu.uniquindio.sgre.utils.SGREUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmpleadoViewController {
    EmpleadoController empleadoControllerService;
    ObservableList<EmpleadoDto> listaEmpleadosDto = FXCollections.observableArrayList();
    EmpleadoDto empleadoSeleccionado;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private TableView<EmpleadoDto> tableEmpleados;

    @FXML
    private TableColumn<EmpleadoDto, String> tcCedula;

    @FXML
    private TableColumn<EmpleadoDto, String> tcCorreo;

    @FXML
    private TableColumn<EmpleadoDto, String> tcNombre;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtContrasenia;


    @FXML
    void initialize() {
        empleadoControllerService = new EmpleadoController();
        intiView();
    }

    private void intiView() {
        initDataBinding();
        obtenerEmpleados();
        tableEmpleados.getItems().clear();
        tableEmpleados.setItems(listaEmpleadosDto);
        listenerSelection();
    }

    private void initDataBinding() {


        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcCedula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().email()));

    }

    private void obtenerEmpleados() {
        listaEmpleadosDto.addAll(empleadoControllerService.obtenerEmpleados());
    }

    private void listenerSelection() {
        tableEmpleados.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            empleadoSeleccionado = newSelection;
            mostrarInformacionEmpleado(empleadoSeleccionado);
        });
    }
    @FXML
    void agregarEmpleadoAction(ActionEvent event) {
        crearEmpleado();
    }

    private void crearEmpleado() {
        EmpleadoDto empleadoDto = construirEmpleadoDto();
        if(datosValidos(empleadoDto)){
            if(empleadoControllerService.agregarEmpleado(empleadoDto)){
                listaEmpleadosDto.add(empleadoDto);
                mostrarMensaje("Notificación empleado", "Empleado creado", "El empleado se ha creado con éxito", Alert.AlertType.INFORMATION);
                limpiarCamposEmpleado();
            }else{
                mostrarMensaje("Notificación empleado", "Empleado no creado", "El empleado no se ha creado con éxito", Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación empleado", "Empleado no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
        }
        registrarAccionesSistema("Crear empleado", 1, "se creo en empleado "+empleadoDto);
    }

    @FXML
    void eliminarEmpleadoAction(ActionEvent event) {
        eliminarEmpleado();
    }
    private void eliminarEmpleado() {
        boolean empleadoEliminado = false;
        if (empleadoSeleccionado != null) {
            try {
                Persistencia.eliminarEmpleado(empleadoSeleccionado.id());
                Persistencia.eliminarEmpleadoBinario(empleadoSeleccionado.id());
                Persistencia.eliminarEmpleadoXML(empleadoSeleccionado.id());

                listaEmpleadosDto.remove(empleadoSeleccionado);
                empleadoSeleccionado = null;
                tableEmpleados.getSelectionModel().clearSelection();
                limpiarCamposEmpleado();
                mostrarMensaje("Notificación empleado", "Empleado eliminado", "El empleado se ha eliminado con éxito", Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                e.printStackTrace();
                mostrarMensaje("Notificación empleado", "Error al eliminar empleado", "Hubo un error al intentar eliminar el empleado", Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Notificación empleado", "Empleado no seleccionado", "Por favor, seleccione un empleado de la lista", Alert.AlertType.WARNING);
        }
        registrarAccionesSistema("Eliminar empleado", 1, "se elimino el empleado ");
    }

    @FXML
    void actualizarEmpleadoAction(ActionEvent event) {
        actualizarEmpleado();
    }
    private void actualizarEmpleado() {
        boolean empleadoActualizado = false;

        Empleado empleadoActualizad = new Empleado(
                txtCedula.getText(),
                txtNombre.getText(),
                txtCorreo.getText(),
                txtUser.getText(),
                txtContrasenia.getText()

        );

        if (empleadoSeleccionado != null) {
            try {

                Persistencia.actualizarEmpleadoBinario(empleadoSeleccionado.id(), empleadoActualizad);
                Persistencia.actualizarEmpleadoXML(empleadoSeleccionado.id(), empleadoActualizad);
                Persistencia.actualizarEmpleadoTxt(empleadoSeleccionado.id(),empleadoActualizad);
                listaEmpleadosDto.remove(empleadoSeleccionado);
                listaEmpleadosDto.add(new EmpleadoDto(
                        empleadoActualizad.getId(),
                        empleadoActualizad.getNombre(),
                        empleadoActualizad.getEmail(),
                        empleadoActualizad.getUsuario(),
                        empleadoActualizad.getContrasenia()
                ));
                tableEmpleados.refresh();

                mostrarMensaje("Notificación empleado", "Empleado actualizado", "El empleado se ha actualizado con éxito", Alert.AlertType.INFORMATION);

                limpiarCamposEmpleado();
            } catch (IOException e) {
                e.printStackTrace();

                mostrarMensaje("Notificación empleado", "Empleado no actualizado", "El empleado no se ha actualizado correctamente", Alert.AlertType.ERROR);
            }
        } else {

            mostrarMensaje("Notificación empleado", "Empleado no seleccionado", "Por favor, seleccione un empleado de la lista", Alert.AlertType.WARNING);
        }
       
        registrarAccionesSistema("Actualizar empleado", 1, "Se actualizó el empleado " + empleadoActualizado);
    }

    @FXML
    void nuevoEmpleadoAction(ActionEvent event) {
        limpiarCamposEmpleado();
        establecerPromptText();
    }

    private void establecerPromptText() {
        txtNombre.setPromptText("Ingrese el nombre");
        txtCedula.setPromptText("Ingrese la cedula");
        txtCorreo.setPromptText("Ingrese el correo");
        txtUser.setPromptText("Ingrese el usuario");
        txtContrasenia.setPromptText("Ingrese el contraseña");
    }


    private EmpleadoDto construirEmpleadoDto() {
        return new EmpleadoDto(
                txtCedula.getText(),
                txtNombre.getText(),
                txtCorreo.getText(),
                txtUser.getText(),
                txtContrasenia.getText()

        );
    }
    private void limpiarCamposEmpleado() {
        txtNombre.setText("");
        txtCedula.setText("");
        txtCorreo.setText("");
        txtUser.setText("");
        txtContrasenia.setText("");
    }

    private boolean datosValidos(EmpleadoDto empleadoDto) {
        String mensaje = "";
        if(empleadoDto.nombre() == null || empleadoDto.nombre().equals(""))
            mensaje += "El nombre es invalido \n" ;
        if(empleadoDto.id() == null || empleadoDto.id() .equals(""))
            mensaje += "El id es invalido \n" ;
        if(empleadoDto.email() == null || empleadoDto.email().equals(""))
            mensaje += "El email es invalido \n" ;
        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación cliente","Datos invalidos",mensaje, Alert.AlertType.WARNING);
            return false;
        }
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    private boolean mostrarMensajeConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }
    private void mostrarInformacionEmpleado(EmpleadoDto empleadoSeleccionado) {
        if (empleadoSeleccionado != null) {


            txtNombre.setText(empleadoSeleccionado.nombre());
            txtCedula.setText(empleadoSeleccionado.id());
            txtCorreo.setText(empleadoSeleccionado.email());
            txtUser.setText(empleadoSeleccionado.usuario());
            txtContrasenia.setText(empleadoSeleccionado.contrasenia());
        }
    }

    public void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        Persistencia.guardaRegistroLog(mensaje, nivel, accion);
    }


}


