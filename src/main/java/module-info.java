module com.example.kotaidaniel_javafxrestclientdolgozat {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.kotaidaniel_javafxrestclientdolgozat to javafx.fxml;
    exports com.example.kotaidaniel_javafxrestclientdolgozat;
}