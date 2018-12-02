package votingsystem.admin;

import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import votingsystem.Main;
import  javafx.scene.control.Button;

import static java.sql.DriverManager.*;

public class AddRecordController {

    Main main;
    private static Connection  con=null;
    private static Statement stmt;
    private static ResultSet rs;
    private String path;
    int curRow = 0;
    private int idCol;
    private String firstNameCol;
    private String lastNameCol;
    private String partNameCol;
    private String genderCol;
    private String postCol;
    private String placeCol;

    public AddRecordController() throws SQLException{
       // Main.DBConnect();
       // this.DBConnect();
    }
    @FXML
    private BorderPane addRecordPane;
    ObservableList<String> partyNameList = FXCollections.observableArrayList("PTI", "PML-N", "PPP", "MQM", "Others");
    ObservableList<String> postBoxList = FXCollections.observableArrayList("MNA", "MPA");
    ObservableList<String> naList = FXCollections.observableArrayList("NA-01", "NA-02", "NA-03");
    ObservableList<String> paList = FXCollections.observableArrayList("PP-01", "PP-02", "PP-03");

    @FXML
    private TextField idField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private ChoiceBox partyNameBox;
    @FXML
    private ComboBox postBox;
    @FXML
    private ComboBox placeBox;
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton femaleRadioButton;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private Button elecSignBrowse;
    @FXML
    private ImageView elecSignImg;
    @FXML
    private Button saveRecordbtn;
    @FXML
    private Button cancelRecordBtn;

    @FXML
    private  Button newRecordBtn;
    @FXML
    private Button updateRecordBtn;
    @FXML
    private  Button deleteRecordBtn;

    @FXML
    private void initialize() throws IOException, SQLException {
        partyNameBox.setValue("Others");
        partyNameBox.setItems(partyNameList);
        postBox.setValue("MNA");
        postBox.setItems(postBoxList);
        placeBox.setValue("NA-01");
        placeBox.setItems(naList);
        genderGroup = new ToggleGroup();
        maleRadioButton.setToggleGroup(genderGroup);
        femaleRadioButton.setToggleGroup(genderGroup);
        setPath("C:\\Users\\Sadaqat Ali\\Desktop\\java\\VotingSystem\\src\\votingsystem\\images\\image1.png");
        saveRecordbtn.setDisable(true);
        cancelRecordBtn.setDisable(true);
        try {
            viewCandidateDetailsBtnFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void DBConnect(){
        try {
            String host = "jdbc:mysql://localhost:3306/test?zeroDateTimeBehavior=convertToNull";
            String uName = "root";
            String uPass = "root";
            con=DriverManager.getConnection(host,uName,uPass);

            if (con != null) {
                System.out.println("Add Record Controller Connection Successful");
            } else {
                System.out.println("Add Record Controller Connection Unsuccessful");
            }
            AddRecordController.stmt=AddRecordController.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            AddRecordController.rs=AddRecordController.stmt.executeQuery("select * from votingsystem.candidate_details");
            AddRecordController.rs.next();
            if(AddRecordController.rs!=null) {
                System.out.println("Add Record Controller RS Connection successful");

            }

            else
                System.out.println("Add Record Controller RS Connection Unsuccessful");

        } catch (SQLException ex) {
            System.out.println("Exception occured : " + ex.getMessage());
        }
    }

    @FXML
    private void placeChoice() {
        if (postBox.getValue().equals("MNA")) {
            placeBox.setValue("NA-01");
            placeBox.setItems(naList);
        } else {

            placeBox.setValue("PP-01");
            placeBox.setItems(paList);
        }
    }

    @FXML
    private void chooseElectionSign() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File Dialog");
        Stage stage = (Stage) addRecordPane.getScene().getWindow();
        //file extension filter
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File file = fileChooser.showOpenDialog(stage);
        String s = file.toString();
        this.setPath(s);
        if (file != null) {
            System.out.println("File path is " + s);
        }

    }

    @FXML
    private void insertCandRecord() throws SQLException, FileNotFoundException, IOException {
        try {
            String fName = firstNameField.getText();
            String lName = lastNameField.getText();
            String partyName = partyNameBox.getValue().toString();
            String postName = postBox.getValue().toString();
            String placeName = placeBox.getValue().toString();
            String gender = (femaleRadioButton.isSelected() ? "female" : "male");
            String s = this.getPath();
            System.out.println("File path is " + s);
            InputStream is = new FileInputStream(new File(s));

            PreparedStatement ps =AddRecordController.con.prepareStatement("insert into votingsystem.candidate_details (FIRST_NAME ," +
                    "LAST_NAME,PARTY_NAME,GENDER,POST,PLACE,ELECTION_SIGN) values ( ?,?,?,?,?,?,? )");
            if(ps!=null)
                System.out.println("Prepared Statement created");
            else
                System.out.println("Prepared Statement not created");

            ps.setString(1, fName);
            ps.setString(2, lName);
            ps.setString(3, partyName);
            ps.setString(4, gender);
            ps.setString(5, postName);
            ps.setString(6, placeName);
            ps.setBlob(7,is);


            if(ps.execute())
                System.out.println("Record insert Successfully");

            System.out.println(fName + " " + lName + " " + partyName + " " + placeName + " " + gender + " ");

        }
        catch (SQLException ex) {
            System.out.println("Exception Occured : " + ex.getMessage());
            ex.printStackTrace();
        }


    }

    //Update Record
    @FXML
    private void updateRecord() throws SQLException, FileNotFoundException {


        //get data from fields
        setIdCol(Integer.parseInt(idField.getText()));
        setFirstNameCol(firstNameField.getText());
        setLastNameCol(lastNameField.getText());
        setPartNameCol(partyNameBox.getValue().toString());
        setPostCol(postBox.getValue().toString());
        setPlaceCol(placeBox.getValue().toString());
        setGenderCol((femaleRadioButton.isSelected() ? "female" : "male"));

        System.out.println("File path is " + getPath());
        InputStream is = new FileInputStream(new File(getPath()));
        System.out.println(rs.getRow());
        try{
        PreparedStatement ps1 = AddRecordController.con.prepareStatement("update votingsystem.candidate_details set FIRST_NAME=?, "
                + "LAST_NAME=?,PARTY_NAME=?,GENDER=?,POST=?, PLACE=? ,ELECTION_SIGN=? where ID=?");
       
        ps1.setString(1, getFirstNameCol());
            ps1.setString(2, getLastNameCol());
            ps1.setString(3, getPartNameCol());
            ps1.setString(4, getGenderCol());
            ps1.setString(5, getPostCol());
            ps1.setString(6, getPlaceCol());
            ps1.setBlob(7, is);
            ps1.setInt(8, getIdCol());
        int rowAffect=ps1.executeUpdate();


        System.out.println("Updated Successfully" + rowAffect );
        }
        catch (SQLException ex) {
            System.out.println("Exception Occured : " + ex.getMessage());
        
    }


    }

    @FXML
    private  void deleteRecord() throws SQLException {
        try {
            AddRecordController.rs.deleteRow();
            getData();
            setData();
        }
        catch (SQLException ex){
            System.out.println("Exception Occured : " + ex.getMessage());
        }

    }

    @FXML
    private void retrieveImage(int id) throws SQLException, FileNotFoundException, IOException {
        try {
            //Main.DBConnect();
            File file = new File("C:\\Users\\Sadaqat Ali\\Desktop\\java\\VotingSystem\\src\\votingsystem\\images\\image1.png");
            FileOutputStream fos = new FileOutputStream(file);
            byte[] b;
            Blob blob;

            PreparedStatement ps = Main.getCon().prepareStatement("select *  from votingsystem.candidate_details where ID=" + id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                blob = rs.getBlob("ELECTION_SIGN");
                b = blob.getBytes(1, (int) blob.length());
                fos.write(b);

                changeImage();

            }
        } catch (SQLException ex) {
            System.out.println("Exception Occured : " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Exception Occured : " + ex.getMessage());
        }

    }

    @FXML
    private void changeImage() throws FileNotFoundException {
        try {
            FileInputStream input = new FileInputStream("C:\\Users\\Sadaqat Ali\\Desktop\\java\\VotingSystem\\src\\votingsystem\\images\\image1.png");
            Image image = new Image(input);
            elecSignImg.setImage(image);
            if (elecSignImg != null) {
                System.out.println("Changed successfully");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Exception Occured : " + ex.getMessage());
        }
    }

    @FXML
    private void viewCandidateDetailsBtnFirst() throws SQLException, IOException {
        try {
            DBConnect();
            AddRecordController.rs.first();
            getData();
            setData();
            if (rs != null) {
                System.out.println("RS is Not null");
            } else {
                System.out.println("RS is null");
            }

            retrieveImage(getIdCol());

        } catch (SQLException ex) {
            System.out.println("Exception Occured : " + ex.getMessage());
        }

    }

    @FXML
    private void viewCandidateDetailsBtnLast() throws SQLException, IOException {

        try {
            //DBConnect();
            AddRecordController.rs.last();
            getData();
            setData();
            if (rs != null) {
                System.out.println("Not null");
            }

            retrieveImage(getIdCol());

        } catch (SQLException ex) {
            System.out.println("Exception Occured : " + ex.getMessage());
        }

    }

    @FXML
    private void viewCandidateDetailsBtnNext() throws SQLException, IOException {
        try {
            //DBConnect();
            if (AddRecordController.rs.next()) {
                getData();
                setData();
                curRow = rs.getRow();
                System.out.println(curRow);

                retrieveImage(getIdCol());
            } else {
                rs.previous();
                System.out.println("End Record");
            }

        } catch (SQLException ex) {
            System.out.println("Exception Occured : " + ex.getMessage());
        }
    }

    @FXML
    private void viewCandidateDetailsBtnPrevious() throws SQLException, IOException {
        try {
            if (AddRecordController.rs.previous()) {
                getData();
                setData();
                curRow = rs.getRow();
                System.out.println(curRow);
                retrieveImage(getIdCol());
            } else {
                rs.next();
                System.out.println("End Record");
            }

        } catch (SQLException ex) {
            System.out.println("Exception Occured : " + ex.getMessage());
        }
    }

    @FXML
    private void viewCandidateDetailsBtnNew() throws SQLException {
        try {
            saveRecordbtn.setDisable(false);
            cancelRecordBtn.setDisable(false);
            updateRecordBtn.setDisable(true);
            deleteRecordBtn.setDisable(true);
            newRecordBtn.setDisable(true);
            curRow = AddRecordController.rs.getRow();
            idField.setText("");
            firstNameField.setText("");
            lastNameField.setText("");
            partyNameBox.setValue("Others");
            postBox.setValue("MNA");
            placeBox.setValue("NA-01");
            maleRadioButton.setSelected("male".equals("male"));

        }
        catch (SQLException ex){
            System.out.println("Exception Occured : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    @FXML
    private  void viewCandidateDetailsBtnCancel() throws SQLException, IOException {
        viewCandidateDetailsBtnFirst();
        saveRecordbtn.setDisable(true);
        cancelRecordBtn.setDisable(true);
        updateRecordBtn.setDisable(false);
        deleteRecordBtn.setDisable(false);
        newRecordBtn.setDisable(false);
        try{
            DBConnect();
            rs.absolute(curRow);

            getData();
            setData();


        }
        catch (SQLException ex){
            System.out.println("Exception Occured : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void getData() throws SQLException {
        try {
            setIdCol(AddRecordController.rs.getInt("ID"));
            setFirstNameCol(AddRecordController.rs.getString("FIRST_NAME"));
            setLastNameCol(AddRecordController.rs.getString("LAST_NAME"));
            setPartNameCol(AddRecordController.rs.getString("PARTY_NAME"));
            setPostCol(AddRecordController.rs.getString("POST"));
            setPlaceCol(AddRecordController.rs.getString("PLACE"));
            setGenderCol(AddRecordController.rs.getString("GENDER"));
        } catch (SQLException ex) {
            System.out.println("Exception Occured : " + ex.getMessage());
        }
    }

    @FXML
    private void setData() {

        idField.setText(Integer.toString(getIdCol()));
        firstNameField.setText(getFirstNameCol());
        lastNameField.setText(getLastNameCol());
        partyNameBox.setValue(getPartNameCol());
        postBox.setValue(getPostCol());
        placeBox.setValue(getPlaceCol());
        femaleRadioButton.setSelected(getGenderCol().equals("female"));
        maleRadioButton.setSelected(getGenderCol().equals("male"));

    }



    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the idCol
     */
    public int getIdCol() {
        return idCol;
    }

    /**
     * @param idCol the idCol to set
     */
    public void setIdCol(int idCol) {
        this.idCol = idCol;
    }

    /**
     * @return the firstNameCol
     */
    public String getFirstNameCol() {
        return firstNameCol;
    }

    /**
     * @param firstNameCol the firstNameCol to set
     */
    public void setFirstNameCol(String firstNameCol) {
        this.firstNameCol = firstNameCol;
    }

    /**
     * @return the lastNameCol
     */
    public String getLastNameCol() {
        return lastNameCol;
    }

    /**
     * @param lastNameCol the lastNameCol to set
     */
    public void setLastNameCol(String lastNameCol) {
        this.lastNameCol = lastNameCol;
    }

    /**
     * @return the partNameCol
     */
    public String getPartNameCol() {
        return partNameCol;
    }

    /**
     * @param partNameCol the partNameCol to set
     */
    public void setPartNameCol(String partNameCol) {
        this.partNameCol = partNameCol;
    }

    /**
     * @return the genderCol
     */
    public String getGenderCol() {
        return genderCol;
    }

    /**
     * @param genderCol the genderCol to set
     */
    public void setGenderCol(String genderCol) {
        this.genderCol = genderCol;
    }

    /**
     * @return the postCol
     */
    public String getPostCol() {
        return postCol;
    }

    /**
     * @param postCol the postCol to set
     */
    public void setPostCol(String postCol) {
        this.postCol = postCol;
    }

    /**
     * @return the placeCol
     */
    public String getPlaceCol() {
        return placeCol;
    }

    /**
     * @param placeCol the placeCol to set
     */
    public void setPlaceCol(String placeCol) {
        this.placeCol = placeCol;
    }

}
