module com.example.exercitiu {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.exercitiu.Controller to javafx.fxml;
    exports com.example.exercitiu.Controller;

    opens com.example.exercitiu to javafx.graphics, javafx.fxml;
    exports com.example.exercitiu;
    opens com.example.exercitiu.Service to javafx.fxml;

    opens com.example.exercitiu.Model to javafx.graphics, javafx.fxml, javafx.base;
}