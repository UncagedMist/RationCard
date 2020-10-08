package tbc.uncagedmist.rationcard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
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
import dmax.dialog.SpotsDialog;
import tbc.uncagedmist.rationcard.Adapter.StateAdapter;
import tbc.uncagedmist.rationcard.Common.Common;
import tbc.uncagedmist.rationcard.Interface.IStateLoadListener;
import tbc.uncagedmist.rationcard.Model.State;

public class StateActivity extends AppCompatActivity implements IStateLoadListener {

    AdView aboveStateBanner, bottomStateBanner;
    RecyclerView recyclerState;

    FloatingActionButton stateShare;
    CollectionReference refAllStates;

    IStateLoadListener iStateLoadListener;

    AlertDialog alertDialog;

    NoInternetDialog noInternetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Requesting Data...")
                .setCancelable(false)
                .build();

        recyclerState = findViewById(R.id.recyclerState);

        aboveStateBanner = findViewById(R.id.aboveStateBanner);
        bottomStateBanner = findViewById(R.id.bottomStateBanner);

        stateShare = findViewById(R.id.stateShare);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView txtTitle = toolbar.findViewById(R.id.tool_title);

        txtTitle.setText(R.string.title);

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        refAllStates.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null)  {
                    return;
                }
                noInternetDialog = new NoInternetDialog.Builder(StateActivity.this).build();
                getAllStateList();
            }
        });
    }

    private void getAllStateList() {
        alertDialog.show();

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
                            alertDialog.dismiss();
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
        recyclerState.setLayoutManager(new GridLayoutManager(this,2));

        recyclerState.setAdapter(new StateAdapter(this,allStateList));
    }

    @Override
    public void onAllStateLoadFailed(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        new TTFancyGifDialog.Builder(StateActivity.this)
                .setTitle("Good-Bye")
                .setMessage("Do You Want to Step Out?")
                .setPositiveBtnText("Rate US")
                .setPositiveBtnBackground("#22b573")
                .setNegativeBtnText("Exit")
                .setNegativeBtnBackground("#c1272d")
                .setGifResource(R.drawable.gif3)
                .isCancellable(false)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=tbc.uncagedmist.rationcard")));
                    }
                })
                .OnNegativeClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                }).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_about)  {
            startActivity(new Intent(StateActivity.this,AboutActivity.class));
        }
        else if (id == R.id.action_privacy) {
            startActivity(new Intent(StateActivity.this,PrivacyActivity.class));
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        noInternetDialog.onDestroy();
    }
}