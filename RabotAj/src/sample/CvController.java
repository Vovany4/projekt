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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CvController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button edit_button;

    @FXML
    private TextField lastName_field;

    @FXML
    private TextField firstName_field;

    @FXML
    private TextField typeJob_field;

    @FXML
    private TextField city_field;

    @FXML
    private TextArea description_area;

    @FXML
    private Button errorButton;

    @FXML
    private ChoiceBox<String> choiceBoxVacancy1;

    @FXML
    private ChoiceBox<String> choiceBoxVacancy2;

    @FXML
    private ChoiceBox<String> choiceBoxVacancy3;

    @FXML
    private TextField sex_field;

    @FXML
    private Button pressButton;

    @FXML
    private Button userName_button;
    private Boolean fake =false;
    private String IdPicked="";
    @FXML
    void initialize() {
        if(!MyAccountController.userIDpicked.equals(""))
            IdPicked=MyAccountController.userIDpicked;
        else
            IdPicked=HomeController.userIDpicked;

        if(fake && Controller.USER_STATUS.equals("Admin"))
            edit_button.setText("Sent");
        else if(fake)
            edit_button.setText("Give contact");

        choiceBoxVacancy1.getItems().addAll("Vacancy","Purchase","Transport","Building","Communication","Bar",
                "Jurisprudence","HR","Security","Home staff","Beauty/Sport","Tourism/Recreation","Education","Culture/Art",
                "Medicine","IT/Computes","Banks/Finance","Marketing","Agronomy","Begin career/Students","Work abroad",
                "Another area employment");
        choiceBoxVacancy2.getItems().addAll("Vacancy","Purchase","Transport","Building","Communication","Bar",
                "Jurisprudence","HR","Security","Home staff","Beauty/Sport","Tourism/Recreation","Education","Culture/Art",
                "Medicine","IT/Computes","Banks/Finance","Marketing","Agronomy","Begin career/Students","Work abroad",
                "Another area employment");
        choiceBoxVacancy3.getItems().addAll("Vacancy","Purchase","Transport","Building","Communication","Bar",
                "Jurisprudence","HR","Security","Home staff","Beauty/Sport","Tourism/Recreation","Education","Culture/Art",
                "Medicine","IT/Computes","Banks/Finance","Marketing","Agronomy","Begin career/Students","Work abroad",
                "Another area employment");

        if(MyAccountController.userIDpicked.equals("") && HomeController.userIDpicked.equals(""))
             addInfo();
        else
            addUneditableInfo();


        pressButton.setOnAction(event -> {
            openNewScene("/sample/appUser.fxml");
        });
        userName_button.setOnAction(event -> {
            openNewScene("/sample/myAccount.fxml");
        });
        edit_button.setOnAction(event -> {
            if(!fake) {
                editCV();
                errorButton.setText("CV edited");
            }
            else if(fake && Controller.USER_STATUS.equals("Admin"))
            {
                editColumn("'activated'",ConstCV.CV_STATUS);
                errorButton.setText("Status edited");
            }
            else if(fake)
                errorButton.setText("You got contacts");

        });

    }
    private  void addInfo()
    {
        fake=false;
        choiceBoxVacancy1.setValue("Vacancy");
        choiceBoxVacancy2.setValue("Vacancy");
        choiceBoxVacancy3.setValue("Vacancy");

        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUserName(Controller.USER_USERNAME);
        user.setPassword(Controller.USER_PASSWORD);
        ResultSet result = dbHandler.getUser(user);
        try{
            while (result.next()) {
                userName_button.setText(result.getString(Const.USERS_USERNAME));
                firstName_field.setText(result.getString(Const.USERS_FIRSTNAME));
                lastName_field.setText(result.getString(Const.USERS_LASTNAME));
                city_field.setText(result.getString(Const.USERS_LOCATION));
                sex_field.setText(result.getString(Const.USERS_GENDER));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseHandler dbHandler1 = new DatabaseHandler();
        ResultSet result1 = dbHandler.getCV(IdPicked,"");
        try {
            if(result1.next())
            {
                typeJob_field.setText(result1.getString(ConstCV.CV_TYPEJOB));
                choiceBoxVacancy1.setValue(result1.getString(ConstCV.CV_VACANCY1));
                choiceBoxVacancy2.setValue(result1.getString(ConstCV.CV_VACANCY2));
                choiceBoxVacancy3.setValue(result1.getString(ConstCV.CV_VACANCY3));
                description_area.setText(result1.getString(ConstCV.CV_DESCRIPTION));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void addUneditableInfo()
    {
        fake=true;
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getCV(IdPicked,"");
        try {
            if(result.next())
            {
                edit_button.setText("Get contact");
                userName_button.setText(Controller.USER_USERNAME);
                typeJob_field.setText(result.getString(ConstCV.CV_TYPEJOB));
                typeJob_field.setEditable(false);
                choiceBoxVacancy1.setValue(result.getString(ConstCV.CV_VACANCY1));
                choiceBoxVacancy1.setDisable(true);
                choiceBoxVacancy2.setValue(result.getString(ConstCV.CV_VACANCY2));
                choiceBoxVacancy2.setDisable(true);
                choiceBoxVacancy3.setValue(result.getString(ConstCV.CV_VACANCY3));
                choiceBoxVacancy3.setDisable(true);
                description_area.setText(result.getString(ConstCV.CV_DESCRIPTION));
                description_area.setDisable(true);
                firstName_field.setText(result.getString(ConstCV.CV_FIRSTNAME));
                lastName_field.setText(result.getString(ConstCV.CV_LASTNAME));
                city_field.setText(result.getString(ConstCV.CV_CITY));
                sex_field.setText(result.getString(ConstCV.CV_SEX));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void editCV()
    {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getCV(IdPicked,"");
        try {

            if(result.next())
            {
                result.previous();
                while (result.next()) {
                    editColumn(typeJob_field.getText().trim(), ConstCV.CV_TYPEJOB);
                    editColumn(choiceBoxVacancy1.getValue(), ConstCV.CV_VACANCY1);
                    editColumn(choiceBoxVacancy2.getValue(), ConstCV.CV_VACANCY2);
                    editColumn(choiceBoxVacancy3.getValue(), ConstCV.CV_VACANCY3);
                    editColumn(description_area.getText().trim(), ConstCV.CV_DESCRIPTION);
                }
            }
            else
                addCV("None");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void addCV(String idUserFor)
    {
        if(!typeJob_field.equals("") && (!choiceBoxVacancy1.getValue().equals("") || !choiceBoxVacancy2.getValue().equals("")
        || !choiceBoxVacancy3.getValue().equals("")) && !description_area.getText().equals("") )
        {
            DatabaseHandler dbHandler = new DatabaseHandler();
                String idUser=IdPicked ;
                String firstName =firstName_field.getText().trim();
                String lastName=lastName_field.getText().trim();
                String city =city_field.getText().trim();
                String sex =sex_field.getText().trim();
                String typeJob=typeJob_field.getText().trim();
                String vacancy1=choiceBoxVacancy1.getValue();
                String vacancy2=choiceBoxVacancy2.getValue();
                String vacancy3=choiceBoxVacancy3.getValue();
                String description=description_area.getText().trim();

                CV cv = new CV(idUser,typeJob,firstName,lastName,vacancy1,vacancy2,vacancy3,description,city,sex,idUserFor);
            dbHandler.addCV(cv);
            errorButton.setText("CV edited");
        }
        else {
            choiceBoxVacancy1.setValue("Vacancy");
            choiceBoxVacancy2.setValue("Vacancy");
            choiceBoxVacancy3.setValue("Vacancy");
            typeJob_field.setText("");
            description_area.setText("");
            errorButton.setText("One field is empty!!!");
        }
    }
    private void editColumn(String info,String column)
    {
        DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.updateCV( info, column, IdPicked);
    }

    private void openNewScene(String window){
        edit_button.getScene().getWindow().hide();

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
