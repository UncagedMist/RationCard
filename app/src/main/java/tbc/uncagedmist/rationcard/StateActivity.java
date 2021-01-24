package tbc.uncagedmist.rationcard;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

import tbc.uncagedmist.rationcard.Adapter.DrawerAdapter;
import tbc.uncagedmist.rationcard.Fragments.AboutFragment;
import tbc.uncagedmist.rationcard.Fragments.HomeFragment;
import tbc.uncagedmist.rationcard.Fragments.PrivacyFragment;
import tbc.uncagedmist.rationcard.Fragments.SettingFragment;
import tbc.uncagedmist.rationcard.Model.DrawerItem;
import tbc.uncagedmist.rationcard.Model.SimpleItem;
import tbc.uncagedmist.rationcard.Model.SpaceItem;

public class StateActivity extends AppCompatActivity implements  DrawerAdapter.OnItemSelectedListener {

    ReviewManager manager;
    ReviewInfo reviewInfo;

    public static final int POS_CLOSE = 0;
    public static final int POS_HOME = 1;
    public static final int POS_ABOUT = 2;
    public static final int POS_PRIVACY = 3;
    public static final int POS_SETTING = 4;
    public static final int POS_RATE = 5;
    public static final int POS_EXIT = 7;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);

        manager = ReviewManagerFactory.create(StateActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.title);

//        wishFestival();

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_CLOSE),
                createItemFor(POS_HOME).setChecked(true),
                createItemFor(POS_ABOUT),
                createItemFor(POS_PRIVACY),
                createItemFor(POS_SETTING),
                createItemFor(POS_RATE),
                new SpaceItem(260),
                createItemFor(POS_EXIT)
        ));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.drawer_list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_HOME);
    }

    private void wishFestival() {
        new TTFancyGifDialog.Builder(StateActivity.this)
                .setTitle("Happy Makar Sankranti 2021")
                .setMessage("Wishing that the rising sun of Makar Sankranti fills your life with bright and happy moments :)")
                .setPositiveBtnText("Thanks")
                .setPositiveBtnBackground("#22b573")
                .setGifResource(R.drawable.makar)
                .isCancellable(false)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        Toast.makeText(StateActivity.this, "Thanks for Supporting US!", Toast.LENGTH_SHORT).show();
                    }
                }).build();
    }

    @Override
    public void onBackPressed() {
        new TTFancyGifDialog.Builder(StateActivity.this)
                .setTitle("Sarkari Sahayata")
                .setMessage("Support us by downloading our other apps!")
                .setPositiveBtnText("Support")
                .setPositiveBtnBackground("#22b573")
                .setNegativeBtnText("Don't")
                .setNegativeBtnBackground("#c1272d")
                .setGifResource(R.drawable.ic_logo)
                .isCancellable(false)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=tbc.uncagedmist.sarkarisahayata")));
                    }
                })
                .OnNegativeClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                    }
                }).build();
    }

    private DrawerItem createItemFor(int position)  {
        return new SimpleItem(screenIcons[position],screenTitles[position])
                .withIconTint(color(R.color.pink))
                .withTextTint(R.color.black)
                .withSelectedIconTint(R.color.pink)
                .withSelectedTextTint(R.color.pink);
    }

    @ColorInt
    private int color(@ColorRes int res)    {
        return ContextCompat.getColor(this,res);
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.activityScreenTitle);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];

        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i,0);

            if (id != 0)    {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @Override
    public void onItemSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (position == POS_HOME)   {
            HomeFragment homeFragment = new HomeFragment();
            transaction.replace(R.id.container,homeFragment);
        }
        else if (position == POS_ABOUT) {
            AboutFragment aboutFragment = new AboutFragment();
            transaction.replace(R.id.container,aboutFragment);
        }
        else if (position == POS_PRIVACY) {
            PrivacyFragment privacyFragment = new PrivacyFragment();
            transaction.replace(R.id.container,privacyFragment);
        }
        else if (position == POS_SETTING) {
            SettingFragment settingFragment = new SettingFragment();
            transaction.replace(R.id.container,settingFragment);
        }
        else if (position == POS_RATE) {
            rateUS();
        }
        else if (position == POS_EXIT) {
            exitFromApp();
        }

        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void rateUS() {
        Task<ReviewInfo> request = manager.requestReviewFlow();

        request.addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            @Override
            public void onComplete(@NonNull Task<ReviewInfo> task) {
                if (task.isSuccessful())    {
                    reviewInfo = task.getResult();

                    Task<Void> flow = manager.launchReviewFlow(StateActivity.this,reviewInfo);

                    flow.addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void result) {

                        }
                    });
                }
                else {
                    Toast.makeText(StateActivity.this, "ERROR...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void exitFromApp() {
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
}