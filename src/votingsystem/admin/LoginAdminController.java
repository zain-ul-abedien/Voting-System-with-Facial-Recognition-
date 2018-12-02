package votingsystem.admin;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import votingsystem.Main;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;

public class LoginAdminController {
    private Main main;
    private String uname;
    private String pass;
    @FXML
    public Button loginBtn;
    @FXML
    private TextField userNameBtn;
    @FXML
    private TextField passwordBtn;
    @FXML
    private void mainAdminScene() throws IOException {
        uname=userNameBtn.getText();
        pass=passwordBtn.getText();
        if(uname.equals("admin") && pass.equals("admin")){
            main.showMainAdminScene();
            Stage stage=(Stage)  loginBtn.getScene().getWindow();
            stage.close();
        }
        else{
            System.out.println("User name or Password Incorrect");
        }

    }

}
