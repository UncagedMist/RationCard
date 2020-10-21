package tbc.uncagedmist.rationcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.shashank.sony.fancyaboutpagelib.FancyAboutPage;

import am.appwise.components.ni.NoInternetDialog;

public class AboutActivity extends AppCompatActivity implements RewardedVideoAdListener {

    FancyAboutPage aboutPage;
    String version;
    NoInternetDialog noInternetDialog;

    private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        noInternetDialog = new NoInternetDialog.Builder(AboutActivity.this).build();

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        aboutPage = findViewById(R.id.aboutPage);
        aboutPage.setCover(R.drawable.coverimg);
        aboutPage.setName("Kundan Kumar");
        aboutPage.setDescription("Android Developer | Android App, Game and Software Developer.");
        aboutPage.setAppIcon(R.mipmap.ic_logo);

        loadRewardedVideoAd();

        aboutPage.setAppName(getString(R.string.title));
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        aboutPage.setVersionNameAsAppSubTitle(version);
        aboutPage.setAppDescription("" +
                "One Ration Card is an Android app Designed to Help People .\n\n" +
                "This app Provides Information on Ration Card Services offered by Central and State Government with beautiful UI. " +
                "People can opt for any available government services and Get benefited." +
                "It also offers to apply for available Services and keep the track of your Applied application.\n\n"+
                "A fresh new take on Material layouts. " +
                "It offers a beautiful UI and daily basis reminder notification to never miss to get any Government Updates.");

        aboutPage.addEmailLink("Kundan_kk52@outlook.com");
        aboutPage.addFacebookLink("https://www.facebook.com/TechByteCare/");
        aboutPage.addTwitterLink("https://twitter.com/uncagedmist");
        aboutPage.addLinkedinLink("https://www.linkedin.com/in/uncagedmist/");
        aboutPage.addGitHubLink("https://github.com/UncagedMist");
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-5860770870597755/8012452890",
                new AdRequest.Builder().build());
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    @Override
    public void onRewardedVideoAdOpened() {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    @Override
    public void onRewardedVideoStarted() {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    @Override
    public void onRewardedVideoCompleted() {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }

    }

    @Override
    protected void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
        noInternetDialog.onDestroy();
    }
}