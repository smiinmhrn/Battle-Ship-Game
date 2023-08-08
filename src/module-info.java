module My.App {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql; // Add this line to require the JDBC package
    exports MyApp;
    exports MyApp.controller;
    exports MyApp.model;
    opens MyApp.model;
    opens MyApp.controller;
    opens MyApp.view;
    opens MyApp to javafx.base;
}