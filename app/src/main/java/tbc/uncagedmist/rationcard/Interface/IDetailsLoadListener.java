package tbc.uncagedmist.rationcard.Interface;

import java.util.List;

import tbc.uncagedmist.rationcard.Model.Detail;

public interface IDetailsLoadListener {

    void onDetailLoadSuccess(List<Detail> details);
    void onDetailLoadFailed(String message);
}
