package votingsystem.admin;

public class CandidatesResult {
    private String candidateName;
    private String candPartyName;
    private String vote;

    public CandidatesResult(String candidateName, String candPartyName, String vote) {
        this.setCandidateName(candidateName);
        this.setCandPartyName(candPartyName);
        this.setVote(vote);
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getCandPartyName() {
        return candPartyName;
    }

    public void setCandPartyName(String candPartyName) {
        this.candPartyName = candPartyName;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

}
