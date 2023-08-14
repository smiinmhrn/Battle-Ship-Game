//module My.App {
//    requires javafx.base;
//    requires javafx.controls;
//    requires javafx.graphics;
//    requires javafx.fxml;
//    requires java.sql;
//    requires javafx.media;
//    exports MyApp;
//    exports MyApp.controller;
//    exports MyApp.model;
//    opens MyApp.model;
//    opens MyApp.controller;
//    opens MyApp.view;
//    opens MyApp to javafx.base;
//}
module My.App {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;

    exports MyApp;
    exports MyApp.controller;
    exports MyApp.model;

    opens MyApp.controller to javafx.fxml;
    opens MyApp.view to javafx.fxml;
//    opens MyApp.resources to javafx.fxml;

    opens MyApp.model;
}
