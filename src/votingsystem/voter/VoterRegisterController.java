package votingsystem.voter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class VoterRegisterController {

    private static Connection con=null;
    private static Statement stmt;
    private static ResultSet rs;

    private String cnic;
    private  String firstName;
    private String lastName;
    private  String fatherName;
    private String nationalDistName;
    private  String provincialDistName;
    private String gender;
    private String adress;
    private LocalDate DoB;


    ObservableList<String> nationalDistNameList = FXCollections.observableArrayList("NA-01", "NA-02", "NA-03");
    ObservableList<String> provincialDistNameList = FXCollections.observableArrayList("PP-01", "PP-02", "PP-03");
    @FXML
    private TextField cnicField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField fatherNameField;
    @FXML
    private ChoiceBox nationalDistNameBox;
    @FXML
    private ChoiceBox provincialDistNameBox;
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton femaleRadioButton;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private  TextArea adressField;
    @FXML
    private  DatePicker dobPicker;

    @FXML
    private void initialize() throws IOException, SQLException {
        nationalDistNameBox.setValue("NA-01");
        nationalDistNameBox.setItems(nationalDistNameList);
        provincialDistNameBox.setItems(provincialDistNameList);
        provincialDistNameBox.setValue("PP-01");
        genderGroup = new ToggleGroup();
        maleRadioButton.setToggleGroup(genderGroup);
        femaleRadioButton.setToggleGroup(genderGroup);
        maleRadioButton.setSelected(true);
    }

    @FXML
    private  void registerVoter() throws SQLException {
        setCnic(cnicField.getText());
        setFirstName(firstNameField.getText());
        setLastName(lastNameField.getText());
        setFatherName(fatherNameField.getText());
        setNationalDistName(nationalDistNameBox.getValue().toString());
        setProvincialDistName(provincialDistNameBox.getValue().toString());
        setGender((femaleRadioButton.isSelected() ? "female" : "male"));
        setAdress(adressField.getText());
        LocalDate dob=dobPicker.getValue();
        setDoB(dob);
        DBConnect();
        insertRecord();
        System.out.println("cnic " + getCnic() + " First Name " + getFirstName() + " Last Name " +getLastName() + " Father Name " + getFatherName()
         + " National Distrrict Name " + getNationalDistName() + " Provincial Dist Name " + getProvincialDistName() + " Gender " + getGender() + " adress " + getAdress() +
        " Date of Birth " + getDoB());
    }
    @FXML

    private void DBConnect(){
        try {
            String host = "jdbc:mysql://localhost:3306/test?zeroDateTimeBehavior=convertToNull";
            String uName = "root";
            String uPass = "root";
            VoterRegisterController.con=(DriverManager.getConnection(host, uName, uPass));
            if (VoterRegisterController.con != null) {
                System.out.println("Connection Successful");
            } else {
                System.out.println("Connection Unsuccessful");
            }

        } catch (SQLException ex) {
            System.out.println("Exception occured : " + ex.getMessage());
        }
    }

    @FXML
    private  void insertRecord() throws SQLException {
        try {
            PreparedStatement ps = VoterRegisterController.con.prepareStatement("insert into votingsystem.voter_details (CNIC ,FIRST_NAME ," +
                    "LAST_NAME,FATHER_NAME,NATIONAL_DIST,PROVINCIAL_DIST,GENDER,ADRESS) values ( ?,?,?,?,?,?,?,? )");
            if (ps != null)
                System.out.println("Prepared Statement created");
            else
                System.out.println("Prepared Statement not created");

            ps.setString(1, getCnic());
            ps.setString(2, getFirstName());
            ps.setString(3, getLastName());
            ps.setString(4, getFatherName());
            ps.setString(5, getNationalDistName());
            ps.setString(6, getProvincialDistName());
            ps.setString(7, getGender());
            ps.setString(8, getAdress());


            if (ps.execute())
                System.out.println("Record insert Successfully");
        }
        catch (SQLException ex) {
            System.out.println("Exception Occured : " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getNationalDistName() {
        return nationalDistName;
    }

    public void setNationalDistName(String nationalDistName) {
        this.nationalDistName = nationalDistName;
    }

    public String getProvincialDistName() {
        return provincialDistName;
    }

    public void setProvincialDistName(String provincialDistName) {
        this.provincialDistName = provincialDistName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public LocalDate getDoB() {
        return DoB;
    }

    public void setDoB(LocalDate doB) {
        DoB = doB;
    }
}
