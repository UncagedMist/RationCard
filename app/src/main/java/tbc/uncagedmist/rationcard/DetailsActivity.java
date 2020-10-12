package tbc.uncagedmist.rationcard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
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
import tbc.uncagedmist.rationcard.Adapter.DetailAdapter;
import tbc.uncagedmist.rationcard.Common.Common;
import tbc.uncagedmist.rationcard.Interface.IDetailsLoadListener;
import tbc.uncagedmist.rationcard.Model.Detail;
import tbc.uncagedmist.rationcard.Utility.CustomLoadDialog;

public class DetailsActivity extends AppCompatActivity implements IDetailsLoadListener,View.OnClickListener {

    AdView aboveDetailBanner, bottomDetailBanner;
    RecyclerView recyclerDetail;

    CollectionReference refDetails;

    TextView txtTitle;

    FloatingActionButton detailShare;

    IDetailsLoadListener iDetailsLoadListener;

    CustomLoadDialog loadDialog;

    NoInternetDialog noInternetDialog;

    private InterstitialAd mInterstitialAd;

    private ResideMenu resideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemAbout;
    private ResideMenuItem itemPrivacy;
    private ResideMenuItem itemSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        loadDialog = new CustomLoadDialog(this);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7920815986886474/3772787579");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        recyclerDetail = findViewById(R.id.recycler_detail);
        aboveDetailBanner = findViewById(R.id.detailAboveBanner);
        bottomDetailBanner = findViewById(R.id.detailBelowBanner);

//        aboveDetailBanner.setAdSize(AdSize.BANNER);
//        aboveDetailBanner.setAdUnitId(getResources().getString(R.string.Banner_Detail));
//
//        bottomDetailBanner.setAdSize(AdSize.BANNER);
//        bottomDetailBanner.setAdUnitId(getResources().getString(R.string.Banner_Detail_Bottom));

        detailShare = findViewById(R.id.detailShare);

        txtTitle = findViewById(R.id.txtTitle);

        txtTitle.setText(Common.CurrentState.getName());

        setUpMenu();

        AdRequest adRequest = new AdRequest.Builder().build();

        aboveDetailBanner.loadAd(adRequest);
        bottomDetailBanner.loadAd(adRequest);

        detailShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String message = "Never Miss A Thing About Ration Card. Install One Ration Card App and Stay Updated! \n https://play.google.com/store/apps/details?id=tbc.uncagedmist.rationcard";
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, "Share One Ration Card App Using"));
            }
        });

        loadInterstitial();
        getAllDetails();

        iDetailsLoadListener = this;

        aboveDetailBanner.setAdListener(new AdListener() {
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

        bottomDetailBanner.setAdListener(new AdListener() {
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
        refDetails.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null)  {
                    return;
                }
                noInternetDialog = new NoInternetDialog.Builder(DetailsActivity.this).build();
                loadInterstitial();
                getAllDetails();
            }
        });
    }

    private void getAllDetails() {
        loadDialog.showDialog();
        refDetails = FirebaseFirestore.getInstance()
                .collection("Sarkari")
                .document(Common.STATE_ID)
                .collection("Services")
                .document(Common.CurrentState.getId())
                .collection("Details");

        refDetails
                .orderBy("name", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Detail> details = new ArrayList<>();
                        if (task.isSuccessful())    {
                            for (QueryDocumentSnapshot detailSnapshot : task.getResult())   {
                                Detail detail = detailSnapshot.toObject(Detail.class);
                                detail.setId(detailSnapshot.getId());
                                details.add(detail);
                            }
                            iDetailsLoadListener.onDetailLoadSuccess(details);
                            loadDialog.hideDialog();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iDetailsLoadListener.onDetailLoadFailed(e.getMessage());
            }
        });
    }

    private void loadInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    @Override
    public void onDetailLoadSuccess(List<Detail> details) {
        recyclerDetail.setHasFixedSize(true);
        recyclerDetail.setLayoutManager(new LinearLayoutManager(this));

        recyclerDetail.setAdapter(new DetailAdapter(this,details));
    }

    @Override
    public void onDetailLoadFailed(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(DetailsActivity.this,AboutActivity.class));

        }else if (view == itemPrivacy){
            startActivity(new Intent(DetailsActivity.this,PrivacyActivity.class));

        }else if (view == itemSettings){
            startActivity(new Intent(DetailsActivity.this,SettingActivity.class));
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
    public void onDestroy() {
        super.onDestroy();
        noInternetDialog.onDestroy();
    }
}