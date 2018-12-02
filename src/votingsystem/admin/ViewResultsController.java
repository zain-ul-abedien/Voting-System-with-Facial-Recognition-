package votingsystem.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import votingsystem.Main;
import votingsystem.voter.CandidateList;

import javax.swing.text.View;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ViewResultsController {

    Main main;
    private  static  Statement stmt;
    private static ResultSet rs;
    private String selectDistrict;
    private int candidatesNumber=0;
    ArrayList <String[]> candList=new ArrayList<String[]>();
    ArrayList <String[]>  votes=new ArrayList<String[]>();
    ArrayList <String[]>  voteList=new ArrayList<String[]>();


    public ObservableList<CandidatesResult> data=FXCollections.observableArrayList();



    ObservableList<String> districtsList = FXCollections.observableArrayList("NA-01", "NA-02", "NA-03");
    @FXML
    private ChoiceBox selectDistrictBox;
    @FXML
    private String districtSelected;


    @FXML
    private TableView<CandidatesResult> tableCandidateResult;
    @FXML
    private TableColumn<CandidatesResult ,String> candidateNameCol;
    @FXML
    private TableColumn<CandidatesResult ,String> candidatePartyCol;
    @FXML
    private  TableColumn<CandidatesResult ,Integer> candidateVoteCol;
    @FXML
    private BarChart<? ,?> VoteChart;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;

    @FXML
    private Button backBtn;



    @FXML
    private void initialize() throws SQLException {
        selectDistrictBox.setItems(districtsList);
        selectDistrictBox.setValue("NA-01");
        setCellTable();
        tableCandidateResult.setItems(data);
    }

    @FXML
    private  void viewResultBtnAction() throws SQLException {
        for ( int i = 0; i<tableCandidateResult.getItems().size(); i++) {
            tableCandidateResult.getItems().clear();
        }
        selectDistrict=selectDistrictBox.getValue().toString();
        System.out.println(selectDistrict);
        getCandList(selectDistrict);
        getVoteListFromDb();
        countVotes();
        loadDataIntoTbl();
        candList.clear();
        voteList.clear();
        votes.clear();
    }

    @FXML
    private  void backbtnAction() throws IOException {
        main.showMainAdminScene();
    }

    @FXML
    private void setCellTable(){
        candidateNameCol.setCellValueFactory(new PropertyValueFactory<>("candidateName"));
        candidatePartyCol.setCellValueFactory(new PropertyValueFactory<>("candPartyName"));
        candidateVoteCol.setCellValueFactory(new PropertyValueFactory<>("vote"));
    }

    @FXML
    private void loadDataIntoTbl(){
        VoteChart.getData().clear();
        XYChart.Series set1=new XYChart.Series<>();
        for(int i=0;i<votes.size();i++) {
            String formattedString=Arrays.toString(votes.get(i)).replace(",", "").replace("[", "") .replace("]", "") .trim();
            String []str=formattedString.split("\\s+");
            String firstName=str[0];
            String lastName=str[1];
            String partyName=str[2];
            String votes=str[3];
            data.add(new CandidatesResult(str[0],str[2],str[3]));
            set1.getData().add(new XYChart.Data(firstName,Integer.parseInt(votes)));
        }
        VoteChart.getData().addAll(set1);
        tableCandidateResult.setItems(data);
    }


    @FXML
    private void countVotes(){
        for(int i=0;i<candList.size();i++){
            String vote="0";
            String formattedString=Arrays.toString(candList.get(i)).replace(",", "").replace("[", "") .replace("]", "") .trim();
            String []str=formattedString.split("\\s+");
            String firstName=str[0];
            String lastName=str[1];
            String partyName=str[2];
            String place=str[3];

            String []str2={firstName,lastName,partyName,votesPerCandidate(firstName,lastName,partyName,place)};
            votes.add(str2);
 }
        for(int i=0;i<votes.size();i++){
            System.out.println(Arrays.toString(votes.get(i)));
        }

    }

    @FXML
    private  String votesPerCandidate(String fname, String lname, String pname,String place){
        int votes=0;
        for (int j=0;j<voteList.size();j++) {
            String formattedString = Arrays.toString(voteList.get(j)).replace(",", "").replace("[", "").replace("]", "").trim();
            String[] str = formattedString.split("\\s+");
            String firstName=str[0];
            String lastName=str[1];
            String partyName=str[2];
            String Place=str[3];
            if(fname.equals(firstName) && lname.equals(lastName) && pname.equals(partyName) && place.equals(Place))
                votes++;
        }
        return Integer.toString(votes);
    }

    @FXML
    private void getVoteListFromDb() throws SQLException {
        try {
            ViewResultsController.stmt = main.getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ViewResultsController.rs=ViewResultsController.stmt.executeQuery("select * from votingsystem.vote_description");
           // ViewResultsController.rs.next();
            if(ViewResultsController.rs!=null) {
                System.out.println("Add Record Controller RS Connection successful");

            }

            else
                System.out.println("Add Record Controller RS Connection Unsuccessful");
            while (ViewResultsController.rs.next()){

                String []str={ViewResultsController.rs.getString("CANDIDATE_FNAME"),ViewResultsController.rs.getString("CANDIDATE_LNAME"),
                        ViewResultsController.rs.getString("CANDIDATE_PARTY"),ViewResultsController.rs.getString("VOTER_DISTRICT")};

                voteList.add(str);

            }

            System.out.println("Size of vote list is  : " + voteList.size());

            for(int i=0;i<voteList.size();i++){
                System.out.println(Arrays.toString(voteList.get(i)));
            }


        }
        catch (SQLException ex) {
            System.out.println("Exception occured : " + ex.getMessage());
        }
    }
    @FXML
    private void getCandList(String selectDistrict) throws SQLException {
        try {
            ViewResultsController.stmt = main.getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ViewResultsController.rs=ViewResultsController.stmt.executeQuery("select * from votingsystem.candidate_details");
            if(ViewResultsController.rs!=null) {
                System.out.println("Add Record Controller RS Connection successful");

            }

            else
                System.out.println("Add Record Controller RS Connection Unsuccessful");
            while (ViewResultsController.rs.next()){

                if(ViewResultsController.rs.getString("PLACE").equals(selectDistrict)) {
                   //setCandidatesNumber(getCandidatesNumber()+1);
                    String str[]={ViewResultsController.rs.getString("FIRST_NAME"),ViewResultsController.rs.getString("LAST_NAME"),
                    ViewResultsController.rs.getString("PARTY_NAME"),ViewResultsController.rs.getString("PLACE")};
                   candList.add(str);
                }

            }
            for(int i=0;i<candList.size();i++){
                System.out.println(Arrays.toString(candList.get(i)));
            }
            System.out.println("no of cand " + candList.size());

        }
        catch (SQLException ex) {
            System.out.println("Exception occured : " + ex.getMessage());
        }
        finally {
            ViewResultsController.rs.close();
            ViewResultsController.stmt.close();
        }
    }

    public String getSelectDistrict() {
        return selectDistrict;
    }

    public void setSelectDistrict(String selectDistrict) {
        this.selectDistrict = selectDistrict;
    }

    public int getCandidatesNumber() {
        return candidatesNumber;
    }

    public void setCandidatesNumber(int candidatesNumber) {
        this.candidatesNumber = candidatesNumber;
    }
}







