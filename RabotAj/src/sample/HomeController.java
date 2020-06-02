package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;

public class HomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton purchase_rb;

    @FXML
    private RadioButton transport_rb;

    @FXML
    private RadioButton building_rb;

    @FXML
    private RadioButton communication_rb;

    @FXML
    private RadioButton bar_rb;

    @FXML
    private RadioButton jurisprudence_rb;

    @FXML
    private RadioButton HR_rb;

    @FXML
    private RadioButton security_rb;

    @FXML
    private RadioButton home_staff_rb;

    @FXML
    private RadioButton beauty_Sport_rb;

    @FXML
    private RadioButton tourism_Recreation_rb;

    @FXML
    private RadioButton education_rb;

    @FXML
    private RadioButton culture_Art_rb;

    @FXML
    private RadioButton medicine_rb;

    @FXML
    private RadioButton IT_Computes_rb;

    @FXML
    private RadioButton banks_Finance_rb;

    @FXML
    private RadioButton marketing_rb;

    @FXML
    private RadioButton agronomy_rb;

    @FXML
    private RadioButton begin_career_Students_rb;

    @FXML
    private RadioButton work_abroad_rb;

    @FXML
    private RadioButton another_area_employment_rb;

    @FXML
    private TextField minSalary_field;

    @FXML
    private TextField maxSalary_field;

    @FXML
    private  Button background1;

    @FXML
    private  Button salary_field1;

    @FXML
    private  Button headOffer_field1;

    @FXML
    private  Button description1;

    @FXML
    private Button next_button;

    @FXML
    private Button prev_button;

    @FXML
    private  Button background3;

    @FXML
    private  Button background2;

    @FXML
    private  Button headOffer_field2;

    @FXML
    private  Button headOffer_field3;

    @FXML
    private  Button description3;

    @FXML
    private  Button salary_field2;

    @FXML
    private  Button salary_field3;

    @FXML
    private  Button description2;

    @FXML
    private  Button city2;

    @FXML
    private  Button city1;

    @FXML
    private  Button city3;

    @FXML
    private Button EnterButton;

    @FXML
    private Button pressButton;

    @FXML
    private Button add_offerButton;

    @FXML
    private Button apply_button;
    @FXML
    private Button cv_offer_button;

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
    private int  counter1=0;



    @FXML
    void initialize() {
        String s=getFilter();
        showOffers(s);
        if(!Controller.USER_USERNAME.equals(""))
        {
            EnterButton.setText(Controller.USER_USERNAME);
        }
        else{
            add_offerButton.setText("");
            add_offerButton.setStyle("-fx-background-color:  #0066CC");
        }

        EnterButton.setOnAction(event -> {
            if(!Controller.USER_USERNAME.equals("")){
            openNewScene("/sample/myAccount.fxml");
            }
            else
                openNewScene("/sample/sample.fxml");

        });
        add_offerButton.setOnAction(event -> {
            if(!Controller.USER_USERNAME.equals(""))
            openNewScene("/sample/addWork.fxml");
        });
        pressButton.setOnAction(event -> {
            offer=true;
            openNewScene("/sample/appUser.fxml");
        });
        next_button.setOnAction(event -> {
            showOffersNext();
        });
        prev_button.setOnAction(event -> {
            showOffersPrev();
        });
        apply_button.setOnAction(event -> {
            showOffers(filterOptions());
        });
        cv_offer_button.setOnAction(event -> {
            if(!Controller.USER_USERNAME.equals("")){
                if (offer) {
                    offer = false;
                    String tmp ="";
                    if(Controller.USER_STATUS.equals("Admin"))
                        tmp=" WHERE " + ConstCV.CV_STATUS+ "='for acceptation'";
                    else
                    tmp+=" GROUP BY " + ConstCV.CV_IDUSER;
                    cv_offer_button.setText("Offers");
                    showOffers(tmp);
                } else {
                    offer = true;
                    cv_offer_button.setText("CV");
                    showOffers(s);

                }
            }
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

    private  String getFilter()
    {
        String filter="";
        if(Controller.USER_STATUS.equals("Admin"))
            filter=" WHERE " + ConstOffer.OFFERS_STATUS+ "='for activation'";
        else
            filter=" WHERE " + ConstOffer.OFFERS_STATUS+ "='activated'";
        return filter;
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

    private String filterOptions()
    {
        String result="";
        if(offer){
        result=" WHERE " + ConstOffer.OFFERS_STATUS+ "='activated'" ;
        if(!minSalary_field.getText().trim().equals("") && !maxSalary_field.getText().trim().equals("") )
        {
            result+=" AND "+ ConstOffer.OFFERS_PRICE+" BETWEEN "+minSalary_field.getText().trim()+" AND "+
                    maxSalary_field.getText().trim();
        }
        else if(!minSalary_field.getText().trim().equals(""))
        {
            result+=" AND "+ ConstOffer.OFFERS_PRICE+" >= "+minSalary_field.getText().trim();
        }
        else if(!maxSalary_field.getText().trim().equals(""))
        {
            result+=" AND "+ ConstOffer.OFFERS_PRICE+" <= "+maxSalary_field.getText().trim();
        }
        }
        result+=radioButtonCheck(purchase_rb);
        result+=radioButtonCheck(communication_rb);
        result+=radioButtonCheck(transport_rb);
        result+=radioButtonCheck(building_rb);
        result+=radioButtonCheck(bar_rb);
        result+=radioButtonCheck(jurisprudence_rb);
        result+=radioButtonCheck(HR_rb);
        result+=radioButtonCheck(security_rb);
        result+=radioButtonCheck(home_staff_rb);
        result+=radioButtonCheck(beauty_Sport_rb);
        result+=radioButtonCheck(tourism_Recreation_rb);
        result+=radioButtonCheck(education_rb);
        result+=radioButtonCheck(culture_Art_rb);
        result+=radioButtonCheck(medicine_rb);
        result+=radioButtonCheck(IT_Computes_rb);
        result+=radioButtonCheck(banks_Finance_rb);
        result+=radioButtonCheck(marketing_rb);
        result+=radioButtonCheck(agronomy_rb);
        result+=radioButtonCheck(begin_career_Students_rb);
        result+=radioButtonCheck(work_abroad_rb);
        result+=radioButtonCheck(another_area_employment_rb);

        if(!offer)
            result+=" GROUP BY "+ConstCV.CV_IDUSER;
        counter1=0;
        return result;
    }
    private String radioButtonCheck(RadioButton rb) {
        String result="";
        if(rb.isSelected())
        {
            if(offer)
            result+=" AND "+ ConstOffer.OFFERS_VACANCY+" = '"+rb.getText().trim()+"'";
            else{
                if(counter1==0) {
                    result+=" WHERE "+ ConstCV.CV_VACANCY1+" = '"+rb.getText().trim()+"' OR " +
                            ConstCV.CV_VACANCY2+" = '"+rb.getText().trim()+"' OR " +
                            ConstCV.CV_VACANCY3+" = '"+rb.getText().trim()+"'";
                    counter1++;
                }
                else {
                    result+=" OR "+ ConstCV.CV_VACANCY1+" = '"+rb.getText().trim()+"' OR " +
                            ConstCV.CV_VACANCY2+" = '"+rb.getText().trim()+"' OR " +
                            ConstCV.CV_VACANCY3+" = '"+rb.getText().trim()+"'";
                }
            }
        }
        return result;
    }
    private void showOffers(String filter) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        if(offer)
        result = dbHandler.getAllOffers(filter);
        else
            result=dbHandler.getAllCV(filter);
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
