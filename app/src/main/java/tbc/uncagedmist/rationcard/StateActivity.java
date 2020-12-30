package tbc.uncagedmist.rationcard;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        wishNewYear();

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

        getSupportActionBar().setTitle(R.string.title);

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

    private void wishNewYear() {
        new TTFancyGifDialog.Builder(StateActivity.this)
                .setTitle("Happy New Year 2021")
                .setMessage("May every day of the new year inspire you to grow!")
                .setPositiveBtnText("Thanks")
                .setPositiveBtnBackground("#22b573")
                .setGifResource(R.drawable.happy)
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
        new TTFancyGifDialog.Builder(StateActivity.this)
                .setTitle("Feedback")
                .setMessage("Your Feedback is important to us.")
                .setPositiveBtnText("Feedback")
                .setPositiveBtnBackground("#22b573")
                .setNegativeBtnText("Exit")
                .setNegativeBtnBackground("#c1272d")
                .setGifResource(R.drawable.feed)
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