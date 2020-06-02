package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class signUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button signUpButton;
    @FXML
    private Button loginEnterButton;

    @FXML
    private TextField signUpName;

    @FXML
    private TextField signUpSurname;

    @FXML
    private TextField signUpCountry;

    @FXML
    private RadioButton radioButtonMale;

    @FXML
    private RadioButton radioButtonFemale;

    @FXML
    private Button pressButton;

    @FXML
    void initialize() {
        signUpButton.setOnAction(event -> {
            signUpNewUser();
        });
        loginEnterButton.setOnAction(event -> {
            openNewScene("/sample/sample.fxml");
        });
        pressButton.setOnAction(event -> {
            openNewScene("/sample/appUser.fxml");
        });
    }

    private void signUpNewUser() {
        if(!signUpName.getText().trim().equals("") && !signUpSurname.getText().trim().equals("") &&
                !login_field.getText().trim().equals("") && !password_field.getText().trim().equals("")&&
                      !signUpCountry.getText().trim().equals("")) {
            DatabaseHandler dbHandler = new DatabaseHandler();

            String firstName = signUpName.getText().trim();
            String lastName = signUpSurname.getText().trim();
            String userName = login_field.getText().trim();
            String password = password_field.getText().trim();
            String location = signUpCountry.getText().trim();
            String gender = "";
            String status = "Worker/Employer";
            if (radioButtonMale.isSelected())
                gender = "male";
            else
                gender = "female";

            User user = new User(firstName, lastName, userName, password, location, gender,status);
            dbHandler.signUpUser(user);

            openNewScene("/sample/appUser.fxml");
        }
        else{
            signUpButton.setText("Empty field");
        }
    }
    public void openNewScene(String window){
        signUpButton.getScene().getWindow().hide();

        FXMLLoader loader =new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try{
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle(" RabotAj ");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
