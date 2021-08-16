package tbc.uncagedmist.rationcard;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.Icon;

import tbc.uncagedmist.rationcard.Fragments.HomeFragment;
import tbc.uncagedmist.rationcard.Fragments.SettingFragment;
import tbc.uncagedmist.rationcard.Helper.CurvedBottomNavigationView;

public class StateActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 31;

    CurvedBottomNavigationView curvedBottomNavigationView;
    FloatingActionButton fab, stateShare;

    FrameLayout adContainerView;
    AdView adView;

    Toolbar toolbar;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case PERMISSION_REQUEST_CODE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)    {
                    Toast.makeText(this, "PERMISSION GRANTED..", Toast.LENGTH_SHORT).show();
                }
                else    {
                    Toast.makeText(this, "PERMISSION DENIED...", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        setContentView(R.layout.activity_state);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title);
        setSupportActionBar(toolbar);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)   {
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, PERMISSION_REQUEST_CODE);
        }

        fab = findViewById(R.id.fab);
        stateShare = findViewById(R.id.stateShare);
        curvedBottomNavigationView = findViewById(R.id.customBottomBar);

        adContainerView = findViewById(R.id.ad_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.

        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.ADMOB_BANNER));
        adContainerView.addView(adView);

        loadBanner();

        HomeFragment homeFragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction().add(R.id.main_frame,homeFragment).commit();

        curvedBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    Fragment fragment;

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        if (item.getItemId() == R.id.action_home) {
                            toolbar.setBackgroundColor(getColor(R.color.color1));
                            getSupportActionBar().setTitle(R.string.app_name);
                            fragment = new HomeFragment();
                            fab.setImageResource(R.drawable.ic_baseline_home_24);
                        }
                        else if (item.getItemId() == R.id.action_setting) {
                            toolbar.setBackgroundColor(getColor(R.color.color2));
                            getSupportActionBar().setTitle("Settings");
                            fragment = new SettingFragment();
                            fab.setImageResource(R.drawable.ic_baseline_settings_suggest_24);
                        }
                        return loadFragment(fragment);
                    }
                });

        loadFragment(HomeFragment.getInstance());
        fab.setImageResource(R.drawable.ic_baseline_home_24);

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
    }

    private void loadBanner() {
        AdRequest adRequest =
                new AdRequest.Builder().build();

        AdSize adSize = getAdSize();
        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize);


        // Step 5 - Start loading the ad in the background.
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    @Override
    public void onBackPressed() {
        new FancyAlertDialog.Builder(StateActivity.this)
                .setTitle("Games Wallpaper App")
                .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
                .setMessage("Customize your Phone's Look with our new Wallpaper App.Support us by downloading our other apps!")
                .setNegativeBtnText("Don't")
                .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("Support")
                .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                .setAnimation(Animation.POP)
                .isCancellable(false)
                .setIcon(R.drawable.ic_star_border_black_24dp, Icon.Visible)
                .OnPositiveClicked(() ->
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=tbc.uncagedmist.mobilewallpapers"))))
                .OnNegativeClicked(() -> {
                })
                .build();
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}