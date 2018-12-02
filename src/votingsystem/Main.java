package votingsystem;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.stage.FileChooser;
import votingsystem.admin.AddRecordController;

public class Main extends Application {

    private static Stage primaryStage;
    private static BorderPane mainLayout;
    private static Connection con = null;
    Statement stmt;
    
    public Main() throws SQLException{
       // DBConnect();
    }

    //ResultSet rs;
    public static void main(String[] args) throws SQLException {
        launch(args);
        AddRecordController obj1=new AddRecordController();
    }

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Voting System");
        showMainView();
        DBConnect();
    }

    private void showMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/MainView.fxml"));
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showLoginAdmin() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("admin/LoginAdmin.fxml"));
        BorderPane loginAdmin = loader.load();

        Stage loginDialogStage = new Stage();
        loginDialogStage.setTitle("Log In");
        loginDialogStage.initModality(Modality.WINDOW_MODAL);
        loginDialogStage.initOwner(primaryStage);
        Scene scene = new Scene(loginAdmin);
        loginDialogStage.setScene(scene);
        loginDialogStage.show();
    }

    public static void showMainAdminScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("admin/MainAdmin.fxml"));
        BorderPane mainAdmin = loader.load();
        mainLayout.setCenter(mainAdmin);

    }

    public static void showHomeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/MainView.fxml"));
        BorderPane homeScene = loader.load();
        mainLayout.setCenter(homeScene);
    }

    public static void showAddRecordWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("admin/AddRecord.fxml"));
        BorderPane loginAdmin = loader.load();

        Stage addRecordDialogStage = new Stage();
        addRecordDialogStage.setTitle("Add Record");
        addRecordDialogStage.initModality(Modality.WINDOW_MODAL);
        addRecordDialogStage.initOwner(primaryStage);
        Scene scene = new Scene(loginAdmin);
        addRecordDialogStage.setScene(scene);
        addRecordDialogStage.showAndWait();
    }
    public static void showRegisterVoterWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("voter/VoterRegister.fxml"));
        BorderPane registerVoter = loader.load();

        Stage registerVoterStage = new Stage();
        registerVoterStage.setTitle("Add Record");
        registerVoterStage.initModality(Modality.WINDOW_MODAL);
        registerVoterStage.initOwner(primaryStage);
        Scene scene = new Scene(registerVoter);
        registerVoterStage.setScene(scene);
        registerVoterStage.showAndWait();
    }
    public static void showmainVoterWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("voter/mainVoter.fxml"));
        BorderPane mainVoter = loader.load();
        mainLayout.setCenter(mainVoter);
    }
    public static void showLoginVoter() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("voter/LoginVoter.fxml"));
        BorderPane loginVoter = loader.load();

        Stage loginDialogStage = new Stage();
        loginDialogStage.setTitle("Log In");
        loginDialogStage.initModality(Modality.WINDOW_MODAL);
        loginDialogStage.initOwner(primaryStage);
        Scene scene = new Scene(loginVoter);
        loginDialogStage.setScene(scene);
        loginDialogStage.show();
    }
    public static void showVoteScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("voter/Vote.fxml"));
        BorderPane vote = loader.load();
        mainLayout.setCenter(vote);

    }
    public static void showResultsScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("admin/ViewResults.fxml"));
        BorderPane vote = loader.load();
        mainLayout.setCenter(vote);

    }

    public static void DBConnect() throws SQLException {
        try {
            String host = "jdbc:mysql://localhost:3306/test?zeroDateTimeBehavior=convertToNull";
            String uName = "root";
            String uPass = "root";
            setCon(DriverManager.getConnection(host, uName, uPass));
            if (getCon() != null) {
                System.out.println("Connection Successful");
            } else {
                System.out.println("Connection Unsuccessful");
            }
          
        } catch (SQLException ex) {
            System.out.println("Exception occured : " + ex.getMessage());
        }
    }

    /**
     * @return the con
     */
    public static Connection getCon() {
        return con;
    }

    /**
     * @param aCon the con to set
     */
    public static void setCon(Connection aCon) {
        con = aCon;
    }

}
