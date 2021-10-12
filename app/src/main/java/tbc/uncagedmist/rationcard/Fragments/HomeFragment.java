package tbc.uncagedmist.rationcard.Fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tbc.uncagedmist.rationcard.Adapter.StateAdapter;
import tbc.uncagedmist.rationcard.Database.MyDatabase;
import tbc.uncagedmist.rationcard.Model.State;
import tbc.uncagedmist.rationcard.R;

public class HomeFragment extends Fragment  {

    RecyclerView recyclerView;
    ArrayList<State> stateArrayList = new ArrayList<>();

    Context context;

    private static HomeFragment INSTANCE = null;

    public static HomeFragment getInstance()    {

        if (INSTANCE == null)   {
            INSTANCE = new HomeFragment();
        }
        return INSTANCE;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View myFragment = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        recyclerView = myFragment.findViewById(R.id.recyclerState);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        Cursor cursor = new MyDatabase(getContext()).getAllStateData();

        while (cursor.moveToNext()) {
            State state = new State(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            stateArrayList.add(state);
        }

        StateAdapter adapter = new StateAdapter(context, stateArrayList);

        recyclerView.setAdapter(adapter);

        return myFragment;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setQueryHint("Enter State Name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<State> stateList = new ArrayList<>();

                for (State stateName : stateArrayList)   {
                    if (stateName.getStateName().toLowerCase().contains(newText.toLowerCase()))  {
                        Cursor cursor = new MyDatabase(
                                context).getStateByNames(newText.toLowerCase());

                        while (cursor.moveToNext()) {
                            State state = new State(
                                    cursor.getString(0),
                                    cursor.getString(1),
                                    cursor.getString(2)
                            );
                            stateList.add(state);
                        }
                    }

                }

                StateAdapter adapter = new StateAdapter(context, stateList);
                recyclerView.setAdapter(adapter);

                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
}