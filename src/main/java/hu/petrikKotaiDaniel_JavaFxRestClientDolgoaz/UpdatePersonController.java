package hu.petrikKotaiDaniel_JavaFxRestClientDolgoaz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdatePersonController extends HelloController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private Spinner<Integer> ageField;

    @FXML
    private Spinner<Integer> heightField;
    @FXML
    private Button updateButton;

    private Person person;

    public void setPerson(Person person) {
        this.person = person;
        String name = this.person.getName();
        String email = this.person.getEmail();
        nameField.setText(this.person.getName());
        emailField.setText(this.person.getEmail());
        ageField.getValueFactory().setValue(this.person.getAge());
        heightField.getValueFactory().setValue(this.person.getHeight());
    }

    @FXML
    private void initialize() {
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200, 15);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory2 =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 215, 150);
        ageField.setValueFactory(valueFactory);
        heightField.setValueFactory(valueFactory2);
    }

    @FXML
    public void updateClick(ActionEvent actionEvent) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        int age = ageField.getValue();
        int height = heightField.getValue();
        if (name.isEmpty()) {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Név szükséges");
            alert.showAndWait();
            warning("Név szükséges");
            return;
        }
        if (email.isEmpty()) {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Email szükséges");
            alert.showAndWait();
            warning("Email szükséges");
            return;
        }
        this.person.setName(name);
        this.person.setEmail(email);
        this.person.setAge(age);
        this.person.setHeight(height);
        Gson converter = new Gson();
        String json = converter.toJson(this.person);
        try {
            String url = HelloApplication.BASE_URL + "/" + this.person.getId();
            Response response = RequestHandler.put(url, json);
            if (response.getResponseCode() == 200) {
                Stage stage = (Stage) this.updateButton.getScene().getWindow();
                stage.close();
            } else {
                error("Hiba módosítás során", response.getContent());
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Hiba módosítás során");
                alert.showAndWait();
            }
        } catch (IOException e) {
            error("Sikertelen csatlakozás a szerverhez");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Sikertelen csatlakozás a szerverhez");
            alert.showAndWait();
        }
    }
}