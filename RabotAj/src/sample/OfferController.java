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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.sound.midi.SysexMessage;

public class OfferController {

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
    private TextField typeJob_field;

    @FXML
    private TextField city_field;

    @FXML
    private TextArea description_area;

    @FXML
    private Button errorButton;

    @FXML
    private TextField vacancy_field;

    @FXML
    private TextField sex_field;

    @FXML
    private Button pressButton;

    @FXML
    private Button loginButton;
    private Boolean isOnList=false;
    private String IdPicked="";

    @FXML
    void initialize() {
        if(!HomeController.offerIDpicked.equals(""))
            IdPicked=HomeController.offerIDpicked;
        else
            IdPicked=MyAccountController.offerIDpicked;

        openOffer();
        if(!Controller.USER_USERNAME.equals(""))
        {
            loginButton.setText(Controller.USER_USERNAME);
        }

        DatabaseHandler dbHandler =new DatabaseHandler();
        ResultSet result = dbHandler.getOfferFromList(IdPicked);
        try {
            if (result.next() && !Controller.USER_STATUS.equals("")) {
                addButton.setText("Sent CV");
                isOnList=true;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        if(Controller.USER_STATUS.equals("Admin"))
        {
            addButton.setText("Activate");
        }
        addButton.setOnAction(event -> {
            if(Controller.USER_USERNAME.equals(""))
            {
                errorButton.setText("You are not logged in");
            }
            else if(Controller.USER_STATUS.equals("Admin"))
            {
                DatabaseHandler dbHandler1 =new DatabaseHandler();
                  dbHandler1.updateOffer("activated",ConstOffer.OFFERS_STATUS,IdPicked);
                openNewScene("/sample/appUser.fxml");
            }
            else if(isOnList )
            {
                sendCV();
                errorButton.setText("CV sended");
                openNewScene("/sample/myAccount.fxml");
                isOnList=false;
            }else{
                DatabaseHandler dbHandler1 =new DatabaseHandler();
                dbHandler1.addOfferToList(IdPicked);
                openNewScene("/sample/appUser.fxml");
            }


        });
        pressButton.setOnAction(event -> {
            openNewScene("/sample/appUser.fxml");
        });
        loginButton.setOnAction(event -> {
            if(!Controller.USER_USERNAME.equals(""))
                openNewScene("/sample/myAccount.fxml");
            else
                openNewScene("/sample/sample.fxml");

        });

    }

    public void sendCV ()
    {
        String userFor="";
        DatabaseHandler dbHandler1 =new DatabaseHandler();
        ResultSet resultSet1=dbHandler1.getOfferFromList(IdPicked);
        try{
            if(resultSet1.next())
            {
                userFor=resultSet1.getString(ConstOfferList.OFFERS_IDUSER);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        DatabaseHandler dbHandler =new DatabaseHandler();
        ResultSet result =dbHandler.getCV(Controller.USER_ID,"");

        try{
            if(result.next())
            {
                CV cv=new CV();

                cv.setIdUser(result.getString(ConstCV.CV_IDUSER));
                cv.setTypeJob(result.getString(ConstCV.CV_TYPEJOB));
                cv.setFirstName(result.getString(ConstCV.CV_FIRSTNAME));
                cv.setLastName(result.getString(ConstCV.CV_LASTNAME));
                cv.setVacancy1(result.getString(ConstCV.CV_VACANCY1));
                cv.setVacancy2(result.getString(ConstCV.CV_VACANCY2));
                cv.setVacancy3(result.getString(ConstCV.CV_VACANCY3));
                cv.setDescription(result.getString(ConstCV.CV_DESCRIPTION));
                cv.setCity(result.getString(ConstCV.CV_CITY));
                cv.setSex(result.getString(ConstCV.CV_SEX));
                cv.setIdUserFor(userFor);

                dbHandler.addCV(cv);
            }
            else
            {
                errorButton.setText("You have not CV");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    public  void openOffer()
    {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getOffer(IdPicked);
        try {
            while(result.next()) {
                vacancy_field.setText(result.getString(ConstOffer.OFFERS_VACANCY));
                typeJob_field.setText(result.getString(ConstOffer.OFFERS_TYPEJOB));
                price_field.setText(result.getString(ConstOffer.OFFERS_PRICE));
                headOffer_field.setText(result.getString(ConstOffer.OFFERS_HEADOFFER));
                description_area.setText(result.getString(ConstOffer.OFFERS_DESCRIPTION));
                city_field.setText(result.getString(ConstOffer.OFFERS_CITY));
                sex_field.setText(result.getString(ConstOffer.OFFERS_GENDER));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
