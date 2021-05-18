package tbc.uncagedmist.rationcard.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tbc.uncagedmist.rationcard.R;

public class SettingFragment extends Fragment {

    View myFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragment = inflater.inflate(R.layout.fragment_setting, container, false);

        return myFragment;
    }
}