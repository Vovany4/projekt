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
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

public class MyAccountController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button background1;

    @FXML
    private Button salary_field1;

    @FXML
    private Button headOffer_field1;

    @FXML
    private Button description1;

    @FXML
    private Button next_button;

    @FXML
    private Button prev_button;

    @FXML
    private Button background3;

    @FXML
    private Button background2;

    @FXML
    private Button headOffer_field2;

    @FXML
    private Button headOffer_field3;

    @FXML
    private Button description3;

    @FXML
    private Button salary_field2;

    @FXML
    private Button salary_field3;

    @FXML
    private Button description2;

    @FXML
    private Button city2;

    @FXML
    private Button city1;

    @FXML
    private Button city3;

    @FXML
    private Button offer_list_button;

    @FXML
    private Button my_offers_button;

    @FXML
    private Button EnterButton;

    @FXML
    private Button pressButton;

    @FXML
    private Button add_offerButton;
    @FXML
    private Button my_CV_button;

    @FXML
    private Button gotted_CV_button;

    private   ResultSet result;

    public static String offerIDpicked="";
    private  String offerID1;
    private  String offerID2;
    private  String offerID3;
    public static String userIDpicked="";
    private  String userID1;
    private  String userID2;
    private  String userID3;

    private  Boolean offer= true;
    private String offerList="";

    @FXML
    void initialize() {
        offer=true;
        offerList=offersList();
        if(!offerList.equals("")) {
            showOffers(offerList);
            offerList="";
        }
        else
            hideAll();
        EnterButton.setText(Controller.USER_USERNAME);

        EnterButton.setOnAction(event -> {
            offer=true;
            showOffers(offersList());
        });
        gotted_CV_button.setOnAction(event -> {
            offer=false;
            showGottedCv();
        });
        my_CV_button.setOnAction(event -> {
            openNewScene("/sample/cv.fxml");
        });
        offer_list_button.setOnAction(event -> {
            offer=true;
            if(!offerList.equals("")) {
                showOffers(offerList);
                offerList="";
            }
            else
                hideAll();
        });
        my_offers_button.setOnAction(event -> {
            offer=true;
            showOffers(myOffers());
        });
        add_offerButton.setOnAction(event -> {
            openNewScene("/sample/addWork.fxml");
        });
        pressButton.setOnAction(event -> {
            openNewScene("/sample/appUser.fxml");
        });
        next_button.setOnAction(event -> {
            showOffersNext();
        });
        prev_button.setOnAction(event -> {
            showOffersPrev();
        });

        city1.setOnAction(event -> {  openChosenOffer(city1,offerID1); });
        city2.setOnAction(event -> { openChosenOffer(city2,offerID2); });
        city3.setOnAction(event -> { openChosenOffer(city3,offerID3); });

        salary_field1.setOnAction(event -> {  openChosenOffer(salary_field1,offerID1); });
        salary_field2.setOnAction(event -> {openChosenOffer(salary_field2,offerID2); });
        salary_field3.setOnAction(event -> { openChosenOffer(salary_field3,offerID3); });

        description1.setOnAction(event -> { openChosenOffer(description1,offerID1); });
        description2.setOnAction(event -> { openChosenOffer(description2,offerID2); });
        description3.setOnAction(event -> { openChosenOffer(description3,offerID3); });

        headOffer_field1.setOnAction(event -> { openChosenOffer(headOffer_field1,offerID1); });
        headOffer_field2.setOnAction(event -> { openChosenOffer(headOffer_field2,offerID2); });
        headOffer_field3.setOnAction(event -> { openChosenOffer(headOffer_field3,offerID3); });

        background1.setOnAction(event -> {  openChosenOffer(background1,offerID1); });
        background2.setOnAction(event -> { openChosenOffer(background2,offerID2); });
        background3.setOnAction(event -> { openChosenOffer(background3,offerID3); });


    }

    private void showGottedCv() {
        String filter2="", filter1="";

        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet res = dbHandler.getCV(Controller.USER_ID,"");
        try{
            int counter=0;
            res.next();
            while (res.next())
            {
                if(counter==0) {
                    filter1 = res.getString(ConstCV.CV_IDUSERFOR);
                    counter++;
                }
               filter2+=" OR "+ConstCV.CV_IDUSER+"= '"+ res.getString(ConstCV.CV_IDUSERFOR)+"'";
            }
            filter2+=" GROUP BY "+ConstCV.CV_IDUSER;
        }catch (SQLException e){
            e.printStackTrace();
        }
        result=dbHandler.getCV(filter1,filter2);
        hideAll();
        showOffersNext();

    }


    public void openChosenOffer(Button button,String offerID) {
        if(offer){
            offerIDpicked=offerID;
            if(button.getStyle().equals("-fx-background-color: white")) {
                openNewScene("/sample/offer.fxml");
                offerIDpicked="";
            }
        }
        else {
            if(offerID.equals(offerID1))
                userIDpicked=userID1;
            else if(offerID.equals(offerID2))
                userIDpicked=userID2;
            else
            userIDpicked=userID3;
            if(button.getStyle().equals("-fx-background-color: white")) {
                openNewScene("/sample/cv.fxml");
                userIDpicked="";
            }
        }
    }


    private void showOffers(String filter) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        result = dbHandler.getAllOffers(filter);
        hideAll();
        showOffersNext();
    }
    private  void  hideAll(){
        hide( headOffer_field1,  salary_field1, description1 , city1,  background1);
        hide( headOffer_field2,  salary_field2, description2, city2,  background2);
        hide( headOffer_field3,  salary_field3, description3, city3,  background3);
    }
    private  void hide (Button headOffer_field, Button salary_field,Button description
            ,Button city, Button background)
    {
        String backGroundColor="-fx-background-color:  #CCCCCC";
        headOffer_field.setStyle(backGroundColor);
        salary_field.setStyle(backGroundColor);
        description.setStyle(backGroundColor);
        city.setStyle(backGroundColor);
        background.setStyle(backGroundColor);

        headOffer_field.setText("");
        salary_field.setText("");
        description.setText("");
        city.setText("");
    }
    public void showOffersNext(){
        int counter=0;
        try{
            while(result.next()){
                counter++;

                if(counter==1)
                {hideAll();
                    offerLook( headOffer_field1,  salary_field1, description1
                            , city1,  background1);
                    if(offer)
                    offerID1=result.getString(ConstOffer.OFFERS_ID);
                    else
                        userID1=result.getString(ConstCV.CV_IDUSER);
                }
                else if(counter==2) {
                    offerLook( headOffer_field2,  salary_field2, description2
                            , city2, background2);
                    if(offer)
                        offerID2=result.getString(ConstOffer.OFFERS_ID);
                    else
                        userID2=result.getString(ConstCV.CV_IDUSER);
                }
                else if(counter==3) {
                    offerLook( headOffer_field3,  salary_field3, description3
                            , city3,  background3);
                    if(offer)
                        offerID3=result.getString(ConstOffer.OFFERS_ID);
                    else
                        userID3=result.getString(ConstCV.CV_IDUSER);
                }
                else if(counter==4){
                    result.previous();
                    break;
                }
            }
            if(counter==2)
            {
                result.previous();
                result.previous();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void showOffersPrev(){
        int counter=0;
        try{
            while(result.previous()){
                counter++;

                if(counter==1) {
                    hideAll();

                    offerLook( headOffer_field1,  salary_field1, description1
                            , city1,  background1);
                    if(offer)
                        offerID1=result.getString(ConstOffer.OFFERS_ID);
                    else
                        userID1=result.getString(ConstCV.CV_IDUSER);
                }
                else if(counter==2) {
                    offerLook(headOffer_field2, salary_field2, description2
                            , city2, background2);
                    if(offer)
                        offerID2=result.getString(ConstOffer.OFFERS_ID);
                    else
                        userID2=result.getString(ConstCV.CV_IDUSER);
                }
                else if(counter==3) {
                    offerLook( headOffer_field3,  salary_field3, description3
                            , city3,  background3);
                    if(offer)
                        offerID3=result.getString(ConstOffer.OFFERS_ID);
                    else
                        userID3=result.getString(ConstCV.CV_IDUSER);
                }
                else if(counter==4){
                    result.previous();
                    result.previous();
                    break;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public  void offerLook(Button headOffer_field, Button salary_field,Button description
            ,Button city, Button background){
        try {
            String backGroundColor="-fx-background-color: white";

            headOffer_field.setStyle(backGroundColor);
            salary_field.setStyle(backGroundColor);
            description.setStyle(backGroundColor);
            city.setStyle(backGroundColor);
            background.setStyle(backGroundColor);

            if(offer) {
                headOffer_field.setText(result.getString(ConstOffer.OFFERS_HEADOFFER));
                salary_field.setText(result.getString(ConstOffer.OFFERS_PRICE));
                description.setText(result.getString(ConstOffer.OFFERS_DESCRIPTION));
                city.setText(result.getString(ConstOffer.OFFERS_CITY));
            }else{
                headOffer_field.setText(result.getString(ConstCV.CV_LASTNAME));
                salary_field.setText(result.getString(ConstCV.CV_FIRSTNAME));
                description.setText(result.getString(ConstCV.CV_DESCRIPTION));
                city.setText(result.getString(ConstCV.CV_VACANCY1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public String offersList(){
        String res="";
        try{
            DatabaseHandler dbHandler =new DatabaseHandler();
            ResultSet offerList = dbHandler.getAllOfferList(Controller.USER_ID);
            int counter=0;
            while(offerList.next())
            {
                if(counter==0)
                {
                    res+=" WHERE "+ ConstOffer.OFFERS_ID+" = "+offerList.getString(ConstOfferList.OFFERS_IDOFFER);
                    counter++;
                }else
                res+=" AND "+ ConstOffer.OFFERS_ID+" = "+offerList.getString(ConstOfferList.OFFERS_IDOFFER);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    public String myOffers(){
            String filter =" WHERE " + Const.USERS_ID+ " = "+Controller.USER_ID;
        return filter;
    }


    public void openNewScene(String window){
        EnterButton.getScene().getWindow().hide();

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
