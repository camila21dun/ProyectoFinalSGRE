module co.edu.uniquindio.sgre {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mapstruct;
    requires org.controlsfx.controls;
    requires java.logging;
    requires java.desktop;
    //requires com.sendgrid;

    opens co.edu.uniquindio.sgre to javafx.fxml;
    exports co.edu.uniquindio.sgre;
    exports co.edu.uniquindio.sgre.viewController;
    opens co.edu.uniquindio.sgre.viewController to javafx.fxml;
    opens co.edu.uniquindio.sgre.mapping.mappers;
    exports co.edu.uniquindio.sgre.controller;
    exports co.edu.uniquindio.sgre.mapping.dto;
    exports co.edu.uniquindio.sgre.mapping.mappers;
    exports co.edu.uniquindio.sgre.model;
    opens co.edu.uniquindio.sgre.controller to javafx.fxml;

}
