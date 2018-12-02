package votingsystem.voter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import  javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import votingsystem.Main;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class VoteController  extends CandidateList{
    Main main;
    private static Connection con=null;
    private static Statement stmt;
    private static ResultSet rs;
    private  int countRows;
    Button[] button;
    LoginVoterController loginVoterController;

    private ObservableList<CandidateList> data;

    @FXML
    private TableView <CandidateList> tableCandidateList;

    @FXML
    private TableColumn <? , ? >  columnFirstName;
    @FXML
    private TableColumn <? , ? >  columnLastName;
    @FXML
    private TableColumn <? , ? >  columnPartyName;
    @FXML
    private TableColumn <? , ? >  columnDistrict;
    @FXML
    private TableColumn <? , ? >  columnVote;

    @FXML
    private Button backBtn;

    public  VoteController(){
        super();
    }
    public VoteController(String fname,String lname,String pname,String dist,Button button){

      super(fname,lname,pname,dist,button);
    }


    @FXML
    private void  initialize()  throws  SQLException{

        data=FXCollections.observableArrayList();
        setCellTable();

        System.out.println(loginVoterController.getVoterDistrict());
        setCountRows(countRows());
        System.out.println(getCountRows());

        button=new Button[getCountRows()];
        for(int j = 0; j< getCountRows(); j++){
            button[j]=new Button();

            button[j].setOnAction(this::handleButtonAction);
        }


        getCandfromDB();
        loadDataIntoTable();

    }

    @FXML
    private void setBackBtnAction() throws IOException {
        main.showmainVoterWindow();
    }




    private void handleButtonAction(ActionEvent actionEvent)  {

        for(int i=0;i<countRows;i++) {
            if (actionEvent.getSource() == button[i]) {
                System.out.println("Button " + i + " Pressed");
                getVoteDetails(i);
            }
        }

    }



    @FXML
    private  void setCellTable(){
        columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnPartyName.setCellValueFactory(new PropertyValueFactory<>("partyName"));
        columnDistrict.setCellValueFactory(new PropertyValueFactory<>("district"));
        columnVote.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    @FXML
    private  void loadDataIntoTable(){
        int x=0;
        for(int i = 0; i< getCountRows(); i++) {

            setFirstName("sadaqat");
            setLastName("ali");
            setPartyName("PTI");
            setDistrict("NA-01");

            String formattedString=Arrays.toString(candList.get(i)).replace(",", "").replace("[", "") .replace("]", "") .trim();
            String []str=formattedString.split("\\s+");
            for(String a:str){
                System.out.println(a);
            }

            data.add(new CandidateList(str[0], str[1], str[2], str[3],button[i]));
        }

        tableCandidateList.setItems(data);

    }
    @FXML
    private  void loadBtnIntoTable(){
        for(int i=0;i<5;i++) {
            setBtn(new Button("Vote" + i));
            //setBtn(button);

            data.add(new CandidateList(getBtn()));
            //btn[i]=getBtn();
            //data.add(new CandidateList("Sadaqat","ali","PTI","NA-01"));
        }

        tableCandidateList.setItems(data);

    }


    @FXML
    private  void printBtn(){
        /*
        for(int j=0;j<btn.length;j++){
            System.out.println(btn[j].getText());
        }
        */
        int rowsCount=tableCandidateList.getItems().size();
        //System.out.println(x);
    }
    @FXML
    private  int countRows() throws SQLException {
        int count=0;
        try {
            DBConnect();
            VoteController.stmt=VoteController.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            VoteController.rs=VoteController.stmt.executeQuery("select * from votingsystem.candidate_details");
            //VoteController.rs.next();
            if(VoteController.rs!=null) {
                System.out.println("Add Record Controller RS Connection successful");

            }

            else
                System.out.println("Add Record Controller RS Connection Unsuccessful");

            while(VoteController.rs.next()){
                if(VoteController.rs.getString("PLACE").equals(loginVoterController.getVoterDistrict())){
                    count++;
                }
            }
        }
        catch (SQLException ex){
            System.out.println("Exception occured : " + ex.getMessage());
        }
        finally {
            VoteController.stmt.close();
        }

        return  count;
    }

    ArrayList <String[]> candList=new ArrayList<String[]>();
    @FXML
    private  void getCandfromDB() throws SQLException {
        int x=0;
        try {
            DBConnect();
            VoteController.stmt = VoteController.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            VoteController.rs = VoteController.stmt.executeQuery("select * from votingsystem.candidate_details");
            //VoteController.rs.next();
            if (VoteController.rs != null) {
                System.out.println("Add Record Controller RS Connection successful");

            } else
                System.out.println("Add Record Controller RS Connection Unsuccessful");


                while (VoteController.rs.next()) {
                    if(VoteController.rs.getString("PLACE").equals(loginVoterController.getVoterDistrict())) {

                        String candDetails[ ]={ VoteController.rs.getString("FIRST_NAME"),VoteController.rs.getString("LAST_NAME"),VoteController.rs.getString("PARTY_NAME"),VoteController.rs.getString("PLACE")};
                        //CandidateList l1=new CandidateList(VoteController.rs.getString("FIRST_NAME"),VoteController.rs.getString("LAST_NAME"),VoteController.rs.getString("PARTY_NAME"),VoteController.rs.getString("PLACE"));
                        System.out.println(VoteController.rs.getString("FIRST_NAME") + VoteController.rs.getString("LAST_NAME"));
                        candList.add(candDetails);

                    }
                }

        }
        catch (SQLException ex){
            System.out.println("Exception occured : " + ex.getMessage());
        }
        finally {
            VoteController.stmt.close();
        }



        for(String[] cand:candList) {
            System.out.println(Arrays.toString(cand));
        }




        System.out.println("Size of arraylist is " + candList.size());
        System.out.println("Size of arraylist is " + Arrays.toString(candList.get(0)));
    }

    @FXML
    private void getCandidateDetails(int row) throws SQLException {
        int count=0;
        try {
            DBConnect();
            VoteController.stmt=VoteController.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            VoteController.rs=VoteController.stmt.executeQuery("select * from votingsystem.candidate_details");
            //VoteController.rs.next();
            if(VoteController.rs!=null) {
                System.out.println("Add Record Controller RS Connection successful");

            }

            else
                System.out.println("Add Record Controller RS Connection Unsuccessful");

            while(VoteController.rs.next()){
                if(VoteController.rs.getString("PLACE").equals("NA-01")){
                    count++;
                }
                if(count==row){
                    setFirstName(VoteController.rs.getString("FIRST_NAME"));
                    setLastName(VoteController.rs.getString("LAST_NAME"));
                    setPartyName(VoteController.rs.getString("PARTY_NAME"));
                }
            }
        }
        catch (SQLException ex){
            System.out.println("Exception occured : " + ex.getMessage());
        }
        finally {
            VoteController.stmt.close();
        }
    }

    @FXML
    private  void getVoteDetails(int rowNum)  {
        String firstName;
        String lastName;
        String partyName;
        String district;
        String formattedString=Arrays.toString(candList.get(rowNum)).replace(",", "").replace("[", "") .replace("]", "") .trim();
        String []str=formattedString.split("\\s+");
        firstName=str[0];
        lastName=str[1];
        partyName=str[2];
        district=str[3];
        System.out.println(firstName + lastName + partyName + district  + " Voter "+ loginVoterController.getVoterFname() + loginVoterController.getVoterLname()
                + loginVoterController.getVoterCnic() + loginVoterController.getVoterDistrict());
        try {
            saveVoteDesc(str[0], str[1], str[2], loginVoterController.getVoterCnic(), loginVoterController.getVoterFname(), loginVoterController.getVoterLname(), loginVoterController.getVoterDistrict());
        }catch (SQLException ex){
            System.out.println("Exception Occured : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private  void  saveVoteDesc(String candFname,String candLname,String candParty,String voterCnic,String voterFname,String voterLname,String voterDist)
    throws  SQLException
    {
        try{
            PreparedStatement ps = VoteController.con.prepareStatement("insert into votingsystem.vote_description (CANDIDATE_FNAME ," +
                    "CANDIDATE_LNAME,CANDIDATE_PARTY,VOTER_CNIC,VOTER_FNAME,VOTER_LNAME,VOTER_DISTRICT) values ( ?,?,?,?,?,?,? )");
            if (ps != null)
                System.out.println("Prepared Statement created");
            else
                System.out.println("Prepared Statement not created");

            ps.setString(1, candFname);
            ps.setString(2, candLname);
            ps.setString(3, candParty);
            ps.setString(4, voterCnic);
            ps.setString(5, voterFname);
            ps.setString(6, voterLname);
            ps.setString(7, voterDist);


            if (ps.execute()==true)
                System.out.println("Record insert Successfully");
            else
                System.out.println("Record insert failed");
        }
        catch (SQLException ex) {
            System.out.println("Exception Occured : " + ex.getMessage());
            ex.printStackTrace();
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


        } catch (SQLException ex) {
            System.out.println("Exception occured : " + ex.getMessage());
        }
    }


    public int getCountRows() {
        return countRows;
    }

    public void setCountRows(int countRows) {
        this.countRows = countRows;
    }
}


