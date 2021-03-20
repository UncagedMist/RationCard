package tbc.uncagedmist.rationcard.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import am.appwise.components.ni.NoInternetDialog;
import tbc.uncagedmist.rationcard.Adapter.StateAdapter;
import tbc.uncagedmist.rationcard.Database.MyDatabase;
import tbc.uncagedmist.rationcard.Model.State;
import tbc.uncagedmist.rationcard.R;

public class HomeFragment extends Fragment  {

    AdView aboveStateBanner, bottomStateBanner;

    RecyclerView recyclerView;
    ArrayList<State> stateArrayList = new ArrayList<>();

    FloatingActionButton stateShare;

    NoInternetDialog noInternetDialog;

    View myFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        noInternetDialog = new NoInternetDialog.Builder(getContext()).build();

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        Toolbar toolbar = myFragment.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("One Nation One Ration");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = myFragment.findViewById(R.id.recyclerState);

        stateShare = myFragment.findViewById(R.id.stateShare);

        aboveStateBanner = myFragment.findViewById(R.id.aboveStateBanner);
        bottomStateBanner = myFragment.findViewById(R.id.bottomStateBanner);

        AdRequest adRequest = new AdRequest.Builder().build();

        aboveStateBanner.loadAd(adRequest);
        bottomStateBanner.loadAd(adRequest);

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

        StateAdapter adapter = new StateAdapter(getContext(),stateArrayList);
        recyclerView.setAdapter(adapter);

        stateShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String message = "Never Miss A Thing About Ration Card. Install One Ration Card App and Stay Updated! \n https://play.google.com/store/apps/details?id=tbc.uncagedmist.rationcard";
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, "Share One Ration Card App Using"));
            }
        });


        aboveStateBanner.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        bottomStateBanner.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

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
                    if (stateName.getStateName().toLowerCase().contains(newText))  {
                        Cursor cursor = new MyDatabase(getContext()).getStateByNames(newText);

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
//                ArrayAdapter<State> adapter = new ArrayAdapter<State>(getContext(),
//                        android.R.layout.simple_list_item_1,stateList);

                StateAdapter adapter = new StateAdapter(getContext(),stateList);
                recyclerView.setAdapter(adapter);

                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        noInternetDialog.onDestroy();
    }
}