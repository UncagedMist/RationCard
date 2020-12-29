package tbc.uncagedmist.rationcard.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import am.appwise.components.ni.NoInternetDialog;
import tbc.uncagedmist.rationcard.Adapter.StateAdapter;
import tbc.uncagedmist.rationcard.Common.Common;
import tbc.uncagedmist.rationcard.Interface.IStateLoadListener;
import tbc.uncagedmist.rationcard.Model.State;
import tbc.uncagedmist.rationcard.R;
import tbc.uncagedmist.rationcard.StateActivity;
import tbc.uncagedmist.rationcard.Utility.CustomLoadDialog;

public class HomeFragment extends Fragment implements IStateLoadListener {

    AdView aboveStateBanner, bottomStateBanner;
    RecyclerView recyclerState;

    FloatingActionButton stateShare;
    CollectionReference refAllStates;

    IStateLoadListener iStateLoadListener;

    CustomLoadDialog loadDialog;

    NoInternetDialog noInternetDialog;

    View myFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_home, container, false);

        loadDialog = new CustomLoadDialog(getContext());
        noInternetDialog = new NoInternetDialog.Builder(getContext()).build();

        recyclerState = myFragment.findViewById(R.id.recyclerState);

        stateShare = myFragment.findViewById(R.id.stateShare);

        aboveStateBanner = myFragment.findViewById(R.id.aboveStateBanner);
        bottomStateBanner = myFragment.findViewById(R.id.bottomStateBanner);

        AdRequest adRequest = new AdRequest.Builder().build();

        aboveStateBanner.loadAd(adRequest);
        bottomStateBanner.loadAd(adRequest);

        getAllStateList();

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

        iStateLoadListener = this;

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

//    @Override
//    public void onStart() {
//        super.onStart();
//        refAllStates.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (error != null)  {
//                    return;
//                }
//                noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
//                getAllStateList();
//            }
//        });
//    }

    private void getAllStateList() {
        loadDialog.showDialog();

        refAllStates = FirebaseFirestore.getInstance()
                .collection("Sarkari")
                .document(Common.STATE_ID)
                .collection("Services");

        refAllStates
                .orderBy("name", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<State> stateList = new ArrayList<>();
                        if (task.isSuccessful())    {
                            for (QueryDocumentSnapshot stateSnapshot : task.getResult())  {
                                State state = stateSnapshot.toObject(State.class);
                                state.setId(stateSnapshot.getId());
                                stateList.add(state);
                            }
                            iStateLoadListener.onAllPStateLoadSuccess(stateList);
                            loadDialog.hideDialog();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iStateLoadListener.onAllStateLoadFailed(e.getMessage());
            }
        });
    }

    @Override
    public void onAllPStateLoadSuccess(List<State> allStateList) {
        recyclerState.setHasFixedSize(true);
        recyclerState.setLayoutManager(new GridLayoutManager(getContext(),2));

        recyclerState.setAdapter(new StateAdapter(getContext(),allStateList));
    }

    @Override
    public void onAllStateLoadFailed(String message) {
        Toast.makeText(getContext(), ""+message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        noInternetDialog.onDestroy();
    }
}