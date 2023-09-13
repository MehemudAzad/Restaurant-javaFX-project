module com.example.main_project_final {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.main_project_final to javafx.fxml;
    exports com.example.main_project_final;
    exports com.example.main_project_final.controllers;
    opens com.example.main_project_final.controllers to javafx.fxml;
    exports com.example.main_project_final.client;
    opens com.example.main_project_final.client to javafx.fxml;
    opens com.example.main_project_final.backend to javafx.base;
    opens com.example.main_project_final.utils to javafx.base;
}