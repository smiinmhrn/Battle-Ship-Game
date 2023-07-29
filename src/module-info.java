module My.App {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    exports MyApp;
    exports MyApp.controller;
    opens MyApp.controller;
    opens MyApp.view; // Add this line to open the MyApp.view package
    opens MyApp to javafx.base;
}