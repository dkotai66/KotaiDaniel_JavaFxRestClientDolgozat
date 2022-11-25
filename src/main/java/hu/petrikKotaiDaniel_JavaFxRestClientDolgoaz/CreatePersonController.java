package hu.petrikKotaiDaniel_JavaFxRestClientDolgoaz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class CreatePersonController extends HelloController {

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
            warning("Név kötelező");
            return;
        }
        if (email.isEmpty()) {
            warning("Email kötelező");
            return;
        }
        Person newPerson = new Person(name, email, age, height, id);
        Gson converter = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = converter.toJson(newPerson);
        try {
            Response response = RequestHandler.post(HelloApplication.BASE_URL, json);
            if (response.getResponseCode() == 201) {
                nameField.setText("");
                emailField.setText("");
                ageField.getValueFactory().setValue(15);
                heightField.getValueFactory().setValue(150);
            } else {
                error("Hiba történt a felvétel során", response.getContent());
            }
        } catch (IOException e) {
            error("Nem sikerült kapcsolódni a szerverhez");
        }
    }
}
