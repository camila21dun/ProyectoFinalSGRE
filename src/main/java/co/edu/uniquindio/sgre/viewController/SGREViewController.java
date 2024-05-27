package co.edu.uniquindio.sgre.viewController;

import co.edu.uniquindio.sgre.controller.SGREController;
import co.edu.uniquindio.sgre.controller.service.ISGREControllerService;
import co.edu.uniquindio.sgre.utils.Persistencia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
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
        tabPane.getTabs().add(tabReserva);
        tabPane.getTabs().add(tabPerfil);
    }

    public void configureTabsForUsuario2() {
        tabPane.getTabs().clear();
        tabPane.getTabs().add(tabUsuario);

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
        registrarAccionesSistema("volvio al inicio", 1, "Se volvio al inicio: ");
    }
    public void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        Persistencia.guardaRegistroLog(mensaje, nivel, accion);
    }

}
