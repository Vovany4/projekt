package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class addOfferController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private TextField price_field;

    @FXML
    private TextField headOffer_field;

    @FXML
    private RadioButton radioButtonMale;

    @FXML
    private RadioButton radioButtonFemale;

    @FXML
    private Button pressButton;
    @FXML
    private Button errorButton;

    @FXML
    private TextField typeJob_field;

    @FXML
    private TextField city_field;

    @FXML
    private ChoiceBox<String> choiceBoxVacancy;
    @FXML
    private TextArea description_area;


    @FXML
    void initialize() {
        addButton.setOnAction(event -> {
            addNewOffer();
        });
        pressButton.setOnAction(event -> {
            openNewScene("/sample/appUser.fxml");
        });

        choiceBoxVacancy.getItems().addAll("Vacancy","Purchase","Transport","Building","Communication","Bar",
                "Jurisprudence","HR","Security","Home staff","Beauty/Sport","Tourism/Recreation","Education","Culture/Art",
                "Medicine","IT/Computes","Banks/Finance","Marketing","Agronomy","Begin career/Students","Work abroad",
                "Another area employment");
        choiceBoxVacancy.setValue("Vacancy");
    }

    private void addNewOffer() {
        if(!price_field.getText().equals("") && !headOffer_field.getText().equals("") &&
                !typeJob_field.getText().equals("")&& !city_field.getText().equals("") &&
                !description_area.getText().equals("")) {
            DatabaseHandler dbHandler = new DatabaseHandler();

            String vacancy = choiceBoxVacancy.getValue();
            String price = price_field.getText();
            String headOffer = headOffer_field.getText();
            String typeJob = typeJob_field.getText();
            String city = city_field.getText();
            String description = description_area.getText();
            String status ="hidden";
            String gender = "";
            if(radioButtonMale.isSelected() &&radioButtonFemale.isSelected())
                gender = "both";
           else if (radioButtonMale.isSelected())
                gender = "male";
            else
                gender = "female";

            Offer offer = new Offer(vacancy, price, headOffer, typeJob, city,description, gender, status);
            dbHandler.addOffer(offer);

            openNewScene("/sample/appUser.fxml");
        }
        else{
            choiceBoxVacancy.setValue("Vacancy");
            price_field.setText("");
            headOffer_field.setText("");
            typeJob_field.setText("");
            city_field.setText("");
            errorButton.setText("One field is empty!!!");
        }
    }
    public void openNewScene(String window){
        addButton.getScene().getWindow().hide();

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
