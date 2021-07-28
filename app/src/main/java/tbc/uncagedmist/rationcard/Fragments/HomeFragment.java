package tbc.uncagedmist.rationcard.Fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.appcompat.widget.SearchView;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.ads.nativetemplates.rvadapter.AdmobNativeAdAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import tbc.uncagedmist.rationcard.Adapter.StateAdapter;
import tbc.uncagedmist.rationcard.Database.MyDatabase;
import tbc.uncagedmist.rationcard.Model.State;
import tbc.uncagedmist.rationcard.R;

public class HomeFragment extends Fragment  {

    RecyclerView recyclerView;
    ArrayList<State> stateArrayList = new ArrayList<>();

    FloatingActionButton stateShare;

    private static HomeFragment INSTANCE = null;

    public static HomeFragment getInstance()    {

        if (INSTANCE == null)   {
            INSTANCE = new HomeFragment();
        }
        return INSTANCE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myFragment = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        recyclerView = myFragment.findViewById(R.id.recyclerState);

        stateShare = myFragment.findViewById(R.id.stateShare);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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

        AdmobNativeAdAdapter admobNativeAdAdapter =
                AdmobNativeAdAdapter.Builder.with(
                        getString(R.string.SAMPLE_NATIVE),
                        adapter,
                        "small")
                        .adItemInterval(3)
                        .build();

        recyclerView.setAdapter(admobNativeAdAdapter);

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

        return myFragment;
    }

    private static void openCustomTabs(Activity activity, CustomTabsIntent customTabsIntent, Uri uri)    {
        String packageName = "com.android.chrome";

        try {

            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity,uri);
        }
        catch(ActivityNotFoundException ex) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,uri));
        }
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
                        Cursor cursor = new MyDatabase(getContext()).getStateByNames(newText.toLowerCase());

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

                StateAdapter adapter = new StateAdapter(getContext(),stateList);
                recyclerView.setAdapter(adapter);

                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
}