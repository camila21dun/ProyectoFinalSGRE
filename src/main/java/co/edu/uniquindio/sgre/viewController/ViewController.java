package co.edu.uniquindio.sgre.viewController;

import co.edu.uniquindio.sgre.SGREMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class ViewController {

    public ViewController(AnchorPane currentAnchorPane, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(SGREMain.class.getResource(fxml)));
        Parent root = loader.load();
        currentAnchorPane.getChildren().clear();
        currentAnchorPane.getChildren().add(root);

        SGREViewController controller = loader.getController();
        String userType = SessionManager.getInstance().getUserType();
        if ("empleado".equals(userType)) {
            controller.configureTabsForEmpleado();
        } else if ("admin".equals(userType)) {
            controller.configureTabsForAdmin();
        } else if ("usuario".equals(userType)) {
            controller.configureTabsForUsuario();
        } else if ("usuario2".equals(userType)) {
            controller.configureTabsForUsuario2();
        }
    }
}
