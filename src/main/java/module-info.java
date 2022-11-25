module com.example.kotaidaniel_javafxrestclientdolgozat {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens hu.petrikKotaiDaniel_JavaFxRestClientDolgoaz to javafx.fxml, com.google.gson;
    exports hu.petrikKotaiDaniel_JavaFxRestClientDolgoaz;
}