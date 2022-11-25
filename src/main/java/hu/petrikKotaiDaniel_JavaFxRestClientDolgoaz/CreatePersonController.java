package hu.petrikKotaiDaniel_JavaFxRestClientDolgoaz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class CreatePersonController extends HelloController{

    @FXML
    private int id;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private Spinner<Integer> ageField;
    @FXML
    private Spinner<Integer> heightField;
    @FXML
    private Button submitButton;

    @FXML
    private void initialize() {
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 15);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory2 =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 215, 150);
        ageField.setValueFactory(valueFactory);
        heightField.setValueFactory(valueFactory2);
    }

    @FXML
    public void submitClick(ActionEvent actionEvent) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        int age = ageField.getValue();
        int height = heightField.getValue();
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Név szükséges");
            alert.showAndWait();
            warning("Név szükséges");
            return;
        }
        if (email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Email szükséges");
            alert.showAndWait();
            warning("Email szükséges");
            return;
        }
        Person newPerson = new Person(name, email, age, height, id);
        Gson converter = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = converter.toJson(newPerson);
        Response response = null;
        try {
            response = RequestHandler.post(HelloApplication.BASE_URL, json);
            if (response.getResponseCode() == 201) {
                nameField.setText("");
                emailField.setText("");
                ageField.getValueFactory().setValue(15);
                heightField.getValueFactory().setValue(150);
            } else {
                error("Hiba felvétel során", response.getContent());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Hiba felvétel során");
                alert.showAndWait();
            }
        } catch (IOException e) {
            error("Hiba kapcsolodás során", response.getContent());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Hiba felvétel során");
            alert.showAndWait();
        }
    }
}
