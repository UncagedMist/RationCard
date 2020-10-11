package tbc.uncagedmist.rationcard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
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
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.ArrayList;
import java.util.List;

import am.appwise.components.ni.NoInternetDialog;
import tbc.uncagedmist.rationcard.Adapter.StateAdapter;
import tbc.uncagedmist.rationcard.Common.Common;
import tbc.uncagedmist.rationcard.Interface.IStateLoadListener;
import tbc.uncagedmist.rationcard.Model.State;
import tbc.uncagedmist.rationcard.Utility.CustomLoadDialog;

public class StateActivity extends AppCompatActivity implements IStateLoadListener,View.OnClickListener {

    AdView aboveStateBanner, bottomStateBanner;
    RecyclerView recyclerState;

    FloatingActionButton stateShare;
    CollectionReference refAllStates;

    IStateLoadListener iStateLoadListener;

    CustomLoadDialog loadDialog;

    NoInternetDialog noInternetDialog;

    private ResideMenu resideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemAbout;
    private ResideMenuItem itemPrivacy;
    private ResideMenuItem itemSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);

        loadDialog = new CustomLoadDialog(this);

        recyclerState = findViewById(R.id.recyclerState);

        stateShare = findViewById(R.id.stateShare);

        aboveStateBanner = findViewById(R.id.aboveStateBanner);
        bottomStateBanner = findViewById(R.id.bottomStateBanner);


        TextView txtTitle = findViewById(R.id.txtTitle);

        txtTitle.setText(R.string.title);

        setUpMenu();

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

    private void setUpMenu() {

        resideMenu = new ResideMenu(this);
//        resideMenu.setUse3D(true);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);

        resideMenu.setScaleValue(0.6f);

        itemHome     = new ResideMenuItem(this, R.drawable.icon_home,     "Home");
        itemAbout  = new ResideMenuItem(this, R.drawable.icon_profile,  "About");
        itemPrivacy = new ResideMenuItem(this, R.drawable.privacy, "Privacy");
        itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, "Settings");

        itemHome.setOnClickListener(this);
        itemAbout.setOnClickListener(this);
        itemPrivacy.setOnClickListener(this);
        itemSettings.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemAbout, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemPrivacy, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == itemHome){

        }else if (view == itemAbout){
            startActivity(new Intent(StateActivity.this,AboutActivity.class));

        }else if (view == itemPrivacy){
            startActivity(new Intent(StateActivity.this,PrivacyActivity.class));

        }else if (view == itemSettings){
            startActivity(new Intent(StateActivity.this,SettingActivity.class));
        }

        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
        }

        @Override
        public void closeMenu() {
        }
    };

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
    public void onDestroy() {
        super.onDestroy();
        noInternetDialog.onDestroy();
    }
}