module com.example.testinggradle {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens at.ac.fhcampuswien.xsolutions to javafx.fxml;
    exports at.ac.fhcampuswien.xsolutions;
}