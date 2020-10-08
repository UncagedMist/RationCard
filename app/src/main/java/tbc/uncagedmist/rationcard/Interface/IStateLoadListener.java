package tbc.uncagedmist.rationcard.Interface;

import java.util.List;

import tbc.uncagedmist.rationcard.Model.State;

public interface IStateLoadListener {
    void onAllPStateLoadSuccess(List<State> allStateList);
    void onAllStateLoadFailed(String message);
}
