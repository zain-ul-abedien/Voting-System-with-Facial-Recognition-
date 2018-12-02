package votingsystem.admin;

import javafx.fxml.FXML;
import votingsystem.Main;

import java.io.IOException;

public class MainAdminController {
    private Main main;

    @FXML
    private void goHome() throws IOException {
        main.showHomeScene();
    }
    @FXML
    private  void  addRecordWindow() throws IOException {
        main.showAddRecordWindow();
    }

    @FXML
    private  void showViewResults() throws IOException {
        main.showResultsScene();
    }
}
