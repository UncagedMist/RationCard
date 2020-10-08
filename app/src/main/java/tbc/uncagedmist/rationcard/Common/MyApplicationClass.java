package tbc.uncagedmist.rationcard.Common;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import tbc.uncagedmist.rationcard.Utility.AppOpenManager;

public class MyApplicationClass extends Application {

    private static Context context;

    private static AppOpenManager appOpenManager;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        MobileAds.initialize(this, "ca-app-pub-7920815986886474~5642992812");

        MobileAds.initialize(
                this,
                new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {}
                });

        appOpenManager = new AppOpenManager(this);
    }
}
