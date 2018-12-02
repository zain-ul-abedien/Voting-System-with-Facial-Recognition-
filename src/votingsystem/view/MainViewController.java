package votingsystem.view;

import javafx.fxml.FXML;
import votingsystem.Main;

import java.io.IOException;

public class MainViewController {
    private Main main;

    @FXML
    private  void loginAdminBtn() throws IOException {
        main.showLoginAdmin();
    }
    @FXML
    private void voterBtn() throws IOException {
        try {
           // main.showRegisterVoterWindow();
            main.showmainVoterWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
