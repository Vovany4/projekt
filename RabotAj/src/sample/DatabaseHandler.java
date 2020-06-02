package sample;

import  java.sql.Connection;
import  java.sql.DriverManager;
import java.sql.PreparedStatement;
import  java.sql.SQLException;
import  java.sql.ResultSet;

public class DatabaseHandler extends  Config{
  Connection dbConnection;

  public Connection getDbConnection()
          throws ClassNotFoundException, SQLException{

      String connectionString ="jdbc:mysql://" + dbHost +":"
              + dbPort + "/" + dbName + "?autoReconnect=true&useSSL=false&serverTimezone=Europe/London";

      Class.forName("com.mysql.cj.jdbc.Driver");

      dbConnection = DriverManager.getConnection(connectionString, dbUser,dbPass);

      return dbConnection;
  }

  public void  signUpUser(User user)
  {
      String insert = "INSERT INTO " + Const.USER_TABLE +"(" + Const.USERS_FIRSTNAME +
              "," + Const.USERS_LASTNAME + "," + Const.USERS_USERNAME+","+
              Const.USERS_PASSWORD+","+Const.USERS_LOCATION+","+
              Const.USERS_GENDER+","+ Const.USERS_STATUS+")"+"VALUES(?,?,?,?,?,?,?)";

      try{
          PreparedStatement prSt = getDbConnection().prepareStatement(insert);
          prSt.setString(1,user.getFirstName());
          prSt.setString(2,user.getLastName());
          prSt.setString(3,user.getUserName());
          prSt.setString(4,user.getPassword());
          prSt.setString(5,user.getLocation());
          prSt.setString(6,user.getGender());
          prSt.setString(7,user.getStatus());

          prSt.executeUpdate();

      } catch (SQLException e) {
          e.printStackTrace();
      } catch (ClassNotFoundException e){
          e.printStackTrace();
      }
  }
  public ResultSet getUser (User user)
  {
      ResultSet resSet = null;

      String selecte ="SELECT * FROM "+ Const.USER_TABLE +" WHERE "+
              Const.USERS_USERNAME+ "=? AND "+ Const.USERS_PASSWORD+"=?";

      try{
          PreparedStatement prSt = getDbConnection().prepareStatement(selecte);
          prSt.setString(1,user.getUserName());
          prSt.setString(2,user.getPassword());

          resSet = prSt.executeQuery();
      } catch (SQLException e) {
          e.printStackTrace();
      } catch (ClassNotFoundException e){
          e.printStackTrace();
      }

      return resSet;
  }
    public ResultSet getUserStatus (String userId )
    {
        ResultSet resSet = null;

        String selecte ="SELECT * FROM "+ Const.USER_TABLE +" WHERE "+
                Const.USERS_ID+ "=" +userId;

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(selecte);

            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return resSet;
    }
  public void addOffer(Offer offer){
      String insert = "INSERT INTO " + ConstOffer.OFFER_TABLE +"(" + ConstOffer.OFFERS_VACANCY +
              "," + ConstOffer.OFFERS_PRICE + "," + ConstOffer.OFFERS_HEADOFFER+","+
              ConstOffer.OFFERS_CITY+","+ConstOffer.OFFERS_DESCRIPTION+","+
              ConstOffer.OFFERS_GENDER+","+ ConstOffer.OFFERS_IDUSERS+","+
              ConstOffer.OFFERS_USERNAME+","+ ConstOffer.OFFERS_STATUS+")"+"VALUES(?,?,?,?,?,?,?,?,?)";

      try{
          PreparedStatement prSt = getDbConnection().prepareStatement(insert);
          prSt.setString(1,offer.getVacancy());
          prSt.setString(2,offer.getPrice());
          prSt.setString(3,offer.getHeadOffer());
          prSt.setString(4,offer.getCity());
          prSt.setString(5,offer.getDescription());
          prSt.setString(6,offer.getGender());
          prSt.setString(7,Controller.USER_ID);
          prSt.setString(8,Controller.USER_USERNAME);
          prSt.setString(9,offer.getStatus());

          prSt.executeUpdate();

      } catch (SQLException e) {
          e.printStackTrace();
      } catch (ClassNotFoundException e){
          e.printStackTrace();
      }
  }
    public ResultSet getAllOffers (String filter)
    {
        ResultSet resSet = null;

        String selecte ="SELECT * FROM "+ ConstOffer.OFFER_TABLE + filter;

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(selecte,ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return resSet;
    }
    public ResultSet getOffer (String id)
    {
        ResultSet resSet = null;

        String selecte ="SELECT * FROM "+ ConstOffer.OFFER_TABLE +" WHERE "+
                ConstOffer.OFFERS_ID+ "=?";

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(selecte,ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            prSt.setString(1,id);

            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return resSet;
    }
    public void  addOfferToList(String idOffer)
    {
        ResultSet result =getOfferFromList(idOffer);
        try{
            if(result.next())
                return;
        }catch (SQLException e){
            e.printStackTrace();
        }

        String insert = "INSERT INTO " + ConstOfferList.OFFERSLIST_TABLE +"(" + ConstOfferList.OFFERS_IDOFFER +
                "," + ConstOfferList.OFFERS_IDUSER + ")" +"VALUES(?,?)";

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1,idOffer);
            prSt.setString(2,Controller.USER_ID);

            prSt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public ResultSet getAllOfferList (String idUser)
    {
        ResultSet resSet = null;

        String selecte ="SELECT * FROM "+ ConstOfferList.OFFERSLIST_TABLE +" WHERE "+
                ConstOfferList.OFFERS_IDUSER+ " = "+idUser;

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(selecte,ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return resSet;
    }
    public ResultSet getOfferFromList (String idOffer)
    {
        ResultSet resSet = null;

        String selecte ="SELECT * FROM "+ ConstOfferList.OFFERSLIST_TABLE +" WHERE "+
                ConstOfferList.OFFERS_IDOFFER+ " = "+idOffer;

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(selecte,ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return resSet;
    }
    public void  addCV(CV cv)
    {
        String insert = "INSERT INTO " + ConstCV.CV_TABLE +"(" + ConstCV.CV_IDUSER +
                "," +ConstCV.CV_TYPEJOB + "," + ConstCV.CV_FIRSTNAME + "," + ConstCV.CV_LASTNAME+","+
                ConstCV.CV_VACANCY1+","+ConstCV.CV_VACANCY2+","+
                ConstCV.CV_VACANCY3+","+ ConstCV.CV_DESCRIPTION+","+ ConstCV.CV_CITY
                +","+ ConstCV.CV_SEX+","+ ConstCV.CV_IDUSERFOR+")"+"VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1,cv.getIdUser());
            prSt.setString(2,cv.getTypeJob());
            prSt.setString(3,cv.getFirstName());
            prSt.setString(4,cv.getLastName());
            prSt.setString(5,cv.getVacancy1());
            prSt.setString(6,cv.getVacancy2());
            prSt.setString(7,cv.getVacancy3());
            prSt.setString(8,cv.getDescription());
            prSt.setString(9,cv.getCity());
            prSt.setString(10,cv.getSex());
            prSt.setString(11,cv.getIdUserFor());

            prSt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public ResultSet getCV (String idUser,String filter)
    {
        ResultSet resSet = null;

        String selecte ="SELECT * FROM "+ ConstCV.CV_TABLE +" WHERE "+
                ConstCV.CV_IDUSER+ "=?"+filter;
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(selecte,ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            prSt.setString(1,idUser);

            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return resSet;
    }
    public ResultSet getAllCV (String filter)
    {
        ResultSet resSet = null;

        String selecte ="SELECT * FROM "+ ConstCV.CV_TABLE +filter;
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(selecte,ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return resSet;
    }
    public void updateCV (String info,String column,String idUser)
    {
        String insert = " UPDATE " + ConstCV.CV_TABLE +" SET " + column +
                "=?"+" WHERE "+ConstCV.CV_IDUSER+"=?";

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1,info);
            prSt.setString(2,idUser);

            prSt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public void updateOffer (String info,String column,String idOffer)
    {
        String insert = " UPDATE " + ConstOffer.OFFER_TABLE +" SET " + column +
                "=?"+" WHERE "+ConstOffer.OFFERS_ID+"=?";

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1,info);
            prSt.setString(2,idOffer);

            prSt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

}
