package tbc.uncagedmist.rationcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import am.appwise.components.ni.NoInternetDialog;
import tbc.uncagedmist.rationcard.Adapter.ScreenSliderPagerAdapter;

public class SplashActivity extends AppCompatActivity {

    LottieAnimationView animationView;

    ScreenSliderPagerAdapter pagerAdapter;
    ViewPager viewPager;

    Animation anim;

    NoInternetDialog noInternetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        noInternetDialog = new NoInternetDialog.Builder(SplashActivity.this).build();

        animationView = findViewById(R.id.lottie);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSliderPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);

        setAdapter();
        setupAnimation();
    }

    private void setAdapter() {
        anim = AnimationUtils.loadAnimation(this,R.anim.o_b_anim);
        viewPager.startAnimation(anim);
    }

    private void setupAnimation() {
        animationView.animate()
                .translationY(1600)
                .setDuration(1000)
                .setStartDelay(4000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        noInternetDialog.onDestroy();
    }
}