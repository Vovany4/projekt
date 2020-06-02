package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {
    public static  String  USER_ID="";
    public static  String  USER_USERNAME="";
    public static  String  USER_PASSWORD="";
    public static String USER_STATUS="";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button loginEnterButton;

    @FXML
    private Button loginRegistrateButton;

    @FXML
    private Button pressButton;

    @FXML
    void initialize() {
        loginEnterButton.setOnAction(event ->{
                    String loginText = login_field.getText().trim();
                    String loginPassword = password_field.getText().trim();

                    if(!loginText.equals("") && !loginPassword.equals(""))
                        loginUser(loginText,loginPassword);
                    else
                        System.out.println("Login or Password is empty");

                });

        loginRegistrateButton.setOnAction(event -> {
            openNewScene("/sample/signUp.fxml");
       });

        pressButton.setOnAction(event -> {
            openNewScene("/sample/appUser.fxml");
        });
    }


    private void loginUser(String loginText, String loginPassword) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUserName(loginText);
        user.setPassword(loginPassword);
        ResultSet result = dbHandler.getUser(user);

        int counter=0;
        try{
            while(result.next()){
                USER_ID=result.getString(Const.USERS_ID);
                USER_USERNAME=result.getString(Const.USERS_USERNAME);
                USER_PASSWORD=result.getString(Const.USERS_PASSWORD);
                USER_STATUS=result.getString(Const.USERS_STATUS);
                counter++;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        if(counter>=1) {
            openNewScene("/sample/appUser.fxml");
        }
    }

    public void openNewScene(String window){
        loginRegistrateButton.getScene().getWindow().hide();

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

