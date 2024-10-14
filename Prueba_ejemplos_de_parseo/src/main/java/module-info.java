module com.example.prueba_ejemplos_de_parseo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.prueba_ejemplos_de_parseo to javafx.fxml;
    exports com.example.prueba_ejemplos_de_parseo;
}