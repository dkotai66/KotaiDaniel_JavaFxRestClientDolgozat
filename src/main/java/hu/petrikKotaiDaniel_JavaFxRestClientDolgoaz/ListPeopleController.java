package hu.petrikKotaiDaniel_JavaFxRestClientDolgoaz;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

public class ListPeopleController extends HelloController {

    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView<Person> peopleTable;
    @FXML
    private TableColumn<Person, Integer> idCol;
    @FXML
    private TableColumn<Person, Integer> nameCol;
    @FXML
    private TableColumn<Person, String> emailCol;
    @FXML
    private TableColumn<Person, String> ageCol;
    @FXML
    private TableColumn<Person, Integer> heightCol;

    @FXML
    private void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name")); //getId() függvény eredményét jeleníti meg
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        heightCol.setCellValueFactory(new PropertyValueFactory<>("height"));
        Platform.runLater(() -> {
            try {
                loadPeopleFromServer();
            } catch (IOException e) {
                error("Hiba történt az adatok lekérése során", e.getMessage());
                Platform.exit();
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Hiba a lekérdezés során");
                alert.showAndWait();
            }
        });
    }

    private void loadPeopleFromServer() throws IOException {
        Response response = RequestHandler.get(HelloApplication.BASE_URL);
        String content = response.getContent();
        Gson converter = new Gson();
        Person[] people = converter.fromJson(content, Person[].class);
        peopleTable.getItems().clear();
        for (Person person : people) {
            peopleTable.getItems().add(person);
        }
    }

    @FXML
    public void insertClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-person-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            Stage stage = new Stage();
            stage.setTitle("Create person");
            stage.setScene(scene);
            stage.setOnCloseRequest(event -> {
                try {
                    loadPeopleFromServer();
                } catch (IOException e) {
                    error("Sikertelen kapcsolódás a szerverhez");
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Sikertelen kapcsolódás a szerverhez");
                    alert.showAndWait();
                }
            });
            stage.show();
        } catch (IOException e) {
            error("Hiba történt az űrlap betöltése során", e.getMessage());
        }
    }

    @FXML
    public void updateClick(ActionEvent actionEvent) {
        Person selected = peopleTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            warning("Módosításhoz szükséges egy elem!");
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Módosításhoz szükséges egy elem");
            alert.showAndWait();
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("update-person-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            //UpdatePersonController controller = fxmlLoader.getController();
            //controller.setPerson(selected);
            Stage stage = new Stage();
            stage.setTitle("Update "+ selected.getName());
            stage.setScene(scene);
            stage.setOnHidden(event -> {
                try {
                    loadPeopleFromServer();
                } catch (IOException e) {
                    error("Sikertelen kapcsolódás a szerverhez");
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Sikertelen kapcsolódás a szerverhez");
                    alert.showAndWait();
                }
            });
            stage.show();
        } catch (IOException e) {
            error("Hiba történt az űrlap betöltése során", e.getMessage());
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba a betöltés során");
            alert.showAndWait();
        }
    }

    @FXML
    public void deleteClick(ActionEvent actionEvent) {
        Person selected = peopleTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            warning("Törléshez szükséges egy elem!");
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Sikertelen törlés");
            alert.showAndWait();
            return;
        }

        Optional<ButtonType> optionalButtonType =
                alert(Alert.AlertType.CONFIRMATION, "Törlés",
                        "Biztosan szeretné törölni az elemet: "
                                + selected.getName(),
                        "");
        if (optionalButtonType.isPresent() &&
                optionalButtonType.get().equals(ButtonType.OK)
        ) {
            String url = HelloApplication.BASE_URL + "/" + selected.getId();
            try {
                RequestHandler.delete(url);
                loadPeopleFromServer();
            } catch (IOException e) {
                error("");
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Sikertelen kapcsolódás a szerverhez");
                alert.showAndWait();
            }
        }
    }
}
