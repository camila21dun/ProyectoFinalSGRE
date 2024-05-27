package co.edu.uniquindio.sgre.viewController;

import co.edu.uniquindio.sgre.controller.SGREController;
import co.edu.uniquindio.sgre.controller.service.ISGREControllerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SGREViewController {
    ISGREControllerService sgreControllerService;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Tab tabEmpleado;

    @FXML
    private Tab tabEvento;

    @FXML
    private Tab tabGestion;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabPerfil;

    @FXML
    private Tab tabReserva;

    @FXML
    private Tab tabUsuario;

    @FXML
    private AnchorPane ventana;

    @FXML
    void initialize() {
        sgreControllerService = new SGREController();
    }

    public void configureTabsForEmpleado() {
        tabPane.getTabs().clear();
        tabPane.getTabs().add(tabEvento);
        tabPane.getTabs().add(tabGestion);
    }

    public void configureTabsForAdmin() {
        tabPane.getTabs().clear();
        tabPane.getTabs().add(tabEmpleado);
        tabPane.getTabs().add(tabEvento);
        tabPane.getTabs().add(tabGestion);
    }

    public void configureTabsForUsuario() {
        tabPane.getTabs().clear();
        tabPane.getTabs().add(tabPerfil);
        tabPane.getTabs().add(tabReserva);
    }

    public void configureTabsForUsuario2() {
        tabPane.getTabs().clear();
        tabPane.getTabs().add(tabUsuario);

    }

}
