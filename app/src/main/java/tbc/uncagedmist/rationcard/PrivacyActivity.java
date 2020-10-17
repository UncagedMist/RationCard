package tbc.uncagedmist.rationcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import am.appwise.components.ni.NoInternetDialog;
import tbc.uncagedmist.rationcard.Common.Common;
import tbc.uncagedmist.rationcard.Utility.CustomLoadDialog;
import tbc.uncagedmist.rationcard.Utility.CustomProgressDialog;

public class PrivacyActivity extends AppCompatActivity implements View.OnClickListener {

    WebView webView;

    AdView privacyAboveBanner,privacyBottomBanner;

    FloatingActionButton privacyShare;

    NoInternetDialog noInternetDialog;

    CustomLoadDialog loadDialog;
    CustomProgressDialog progressDialog;

    private ResideMenu resideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemAbout;
    private ResideMenuItem itemPrivacy;
    private ResideMenuItem itemSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        loadDialog = new CustomLoadDialog(this);
        progressDialog = new CustomProgressDialog(this);

        loadDialog.showDialog();

        noInternetDialog = new NoInternetDialog.Builder(PrivacyActivity.this).build();

        webView = findViewById(R.id.webPrivacy);
        privacyAboveBanner = findViewById(R.id.privacyAboveBanner);
        privacyBottomBanner = findViewById(R.id.privacyBottomBanner);

        privacyShare = findViewById(R.id.privacyShare);

        TextView txtTitle = findViewById(R.id.txtTitle);

        txtTitle.setText("Privacy Policy");

//        privacyAboveBanner.setAdSize(AdSize.BANNER);
//        privacyAboveBanner.setAdUnitId(getResources().getString(R.string.Banner_Privacy));
//
//        privacyBottomBanner.setAdSize(AdSize.BANNER);
//        privacyBottomBanner.setAdUnitId(getResources().getString(R.string.Banner_Privacy_Bottom));


        setUpMenu();

        AdRequest adRequest = new AdRequest.Builder().build();

        privacyAboveBanner.loadAd(adRequest);
        privacyBottomBanner.loadAd(adRequest);

        webView.setWebViewClient(new MyWebViewClient());

        String url = Common.PRIVACY_URL.trim();

        privacyShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String message = "Never Miss A Thing About Ration Card. Install One Ration Card App and Stay Updated! \n https://play.google.com/store/apps/details?id=tbc.uncagedmist.rationcard";
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, "Share One Ration Card App Using"));
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webView.loadUrl(url);

        privacyAboveBanner.setAdListener(new AdListener() {
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

        privacyBottomBanner.setAdListener(new AdListener() {
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
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loadDialog.hideDialog();
            progressDialog.showProgressDialog();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(progressDialog!=null){
                progressDialog.hideProgressDialog();
            }
        }
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
        itemPrivacy = new ResideMenuItem(this, R.drawable.icon_profile, "Privacy");
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
            startActivity(new Intent(PrivacyActivity.this,AboutActivity.class));

        }else if (view == itemPrivacy){

        }else if (view == itemSettings){
            startActivity(new Intent(PrivacyActivity.this,SettingActivity.class));
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