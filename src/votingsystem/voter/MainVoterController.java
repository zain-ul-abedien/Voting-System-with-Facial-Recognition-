package votingsystem.voter;

import javafx.fxml.FXML;
import votingsystem.Main;

import java.io.IOException;

public class MainVoterController {
    private Main main;
    @FXML
    private void goHome() throws IOException {
        main.showHomeScene();
    }
    @FXML
    private void voterBtn() throws IOException {
        try {
             main.showRegisterVoterWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private  void loginVoterBtn() throws IOException {
        main.showLoginVoter();
    }



}
