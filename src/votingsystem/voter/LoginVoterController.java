package votingsystem.voter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import votingsystem.Main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginVoterController {
    private Main main;
    private static Connection con=null;
    private static Statement stmt;
    private static ResultSet rs;
    private static   String voterCnic;
    private static String voterFname;
    private static String voterLname;
    private static String  voterDistrict;
    @FXML
    public Button loginBtn;
    @FXML
    private TextField cnicField;

    @FXML
    private void VoteScene() throws IOException, SQLException {


        if(validateLogin()==true){
            System.out.println("Login successful");
            System.out.println(getVoterCnic() + getVoterFname() + getVoterLname() + getVoterDistrict());
            main.showVoteScene();

            Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.close();
        }
        else{
            System.out.println("Unsuccessful");
            System.out.println(getVoterCnic() + getVoterFname() + getVoterLname() + getVoterDistrict());
        }


    }

    @FXML
    private  boolean validateLogin() throws SQLException {
        //setVoterCnic(cnicField.getText());
        String cnic=cnicField.getText();
        try {
            main.DBConnect();
            LoginVoterController.stmt=main.getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            LoginVoterController.rs=LoginVoterController.stmt.executeQuery("select * from votingsystem.voter_details");
            //VoteController.rs.next();
            if(LoginVoterController.rs!=null) {
                System.out.println("Add Record Controller RS Connection successful");

            }

            else
                System.out.println("Add Record Controller RS Connection Unsuccessful");
            while (LoginVoterController.rs.next()){

                if(LoginVoterController.rs.getString("CNIC").equals(cnic)) {

                    setVoterCnic(LoginVoterController.rs.getString("CNIC"));
                    setVoterFname(LoginVoterController.rs.getString("FIRST_NAME"));
                    setVoterLname(LoginVoterController.rs.getString("LAST_NAME"));
                    setVoterDistrict(LoginVoterController.rs.getString("NATIONAL_DIST"));
                }
            }
        }
        catch (SQLException ex){
            System.out.println("Exception occured : " + ex.getMessage());
        }
        finally {
            LoginVoterController.stmt.close();
        }

        if(cnic.equals(getVoterCnic()))
            return  true;
        else
            return false;


    }

    public static String getVoterCnic() {
        return voterCnic;
    }

    public void setVoterCnic(String voterCnic) {
        this.voterCnic = voterCnic;
    }

    public static String getVoterFname() {
        return voterFname;
    }

    public void setVoterFname(String voterFname) {
        this.voterFname = voterFname;
    }

    public static  String getVoterLname() {
        return voterLname;
    }

    public void setVoterLname(String voterLname) {
        this.voterLname = voterLname;
    }

    public static String getVoterDistrict() {
        return voterDistrict;
    }

    public void setVoterDistrict(String voterDistrict) {
        this.voterDistrict = voterDistrict;
    }
}

