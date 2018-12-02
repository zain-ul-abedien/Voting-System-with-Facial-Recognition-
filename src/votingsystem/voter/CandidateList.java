package votingsystem.voter;
import  javafx.scene.control.Button;

public class CandidateList {
    private  String firstName;
    private String lastName;
    private  String partyName;
    private  String district;
    private Button btn;
    private String candidateName;
    private int votes;

    public  CandidateList(){
        this.firstName="abc";
        this.lastName="ABC";
        this.partyName="aBc";
        this.district="AbC";
        this.btn=new Button("Vote");
    }
    /*
    public CandidateList(String firstName, String lastName, String partyName, String district) {
        setFirstName("sadaqat");
        setLastName("ali");
        setPartyName("PTI");
        setDistrict("NA-01");

    }

*/
    public  CandidateList(String candidateName ,String partyName ,int votes){
        this.candidateName=candidateName;
        this.partyName=partyName;
        this.votes=votes;
    }

        public CandidateList(String firstName, String lastName, String partyName, String district,Button button) {

       this(firstName,lastName,partyName,district);
        this.btn=button;
        this.btn.setText("Vote");
    }
    public CandidateList(String firstName, String lastName, String partyName, String district) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPartyName(partyName);
        this.setDistrict(district);
    }

    public CandidateList(Button btn) {
            setBtn(btn);
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

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
