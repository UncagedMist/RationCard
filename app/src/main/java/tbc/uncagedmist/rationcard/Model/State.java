package tbc.uncagedmist.rationcard.Model;

public class State {

    private String stateId,stateName, stateImage, stateDesc;

    public State() {
    }

    public State(String stateId, String stateName, String stateImage, String stateDesc) {
        this.stateId = stateId;
        this.stateName = stateName;
        this.stateImage = stateImage;
        this.stateDesc = stateDesc;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateImage() {
        return stateImage;
    }

    public void setStateImage(String stateImage) {
        this.stateImage = stateImage;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }
}
