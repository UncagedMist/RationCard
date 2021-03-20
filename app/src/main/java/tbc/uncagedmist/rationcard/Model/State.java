package tbc.uncagedmist.rationcard.Model;

public class State {

    private String stateId,stateName, stateImage;

    public State() {
    }

    public State(String stateId, String stateName, String stateImage) {
        this.stateId = stateId;
        this.stateName = stateName;
        this.stateImage = stateImage;
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
}
