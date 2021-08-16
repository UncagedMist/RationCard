package tbc.uncagedmist.rationcard.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.Icon;

import tbc.uncagedmist.rationcard.R;
import tbc.uncagedmist.rationcard.StateActivity;

public class SettingFragment extends Fragment {

    CardView cardAbout, cardPrivacy, cardFeedback, cardRate, cardMore, cardShare, cardExit;
    TextView txtVersion;

    View myFragment;

    ReviewManager manager;
    ReviewInfo reviewInfo;

    Context context;

    private InterstitialAd mInterstitialAd;

    @Override
    public void onAttach(Activity activity) {
        context = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_setting, container, false);

        manager = ReviewManagerFactory.create((Activity)context);

        cardAbout = myFragment.findViewById(R.id.cardAbout);
        cardPrivacy = myFragment.findViewById(R.id.cardPrivacy);
        cardFeedback = myFragment.findViewById(R.id.cardFeedback);
        cardRate = myFragment.findViewById(R.id.cardRate);
        cardMore = myFragment.findViewById(R.id.cardMore);
        cardShare = myFragment.findViewById(R.id.cardShare);
        cardExit = myFragment.findViewById(R.id.cardExit);

        txtVersion = myFragment.findViewById(R.id.txtVersion);

        onClickImplementation();

        return myFragment;
    }

    private void onClickImplementation() {
        cardAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mInterstitialAd != null) {
                    mInterstitialAd.show((Activity) context);
                }
                else {
                    AboutFragment aboutFragment = new AboutFragment();
                    FragmentTransaction transaction =
                            ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame,aboutFragment).commit();
                }
            }
        });

        cardPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mInterstitialAd != null) {
                    mInterstitialAd.show((Activity) context);
                }
                else {
                    PrivacyFragment privacyFragment = new PrivacyFragment();
                    FragmentTransaction transaction =
                            ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame,privacyFragment).commit();
                }
            }
        });

        cardFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedback();
            }
        });

        cardRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=tbc.uncagedmist.rationcard")));
            }
        });

        cardMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:UncagedMist")));
            }
        });

        cardShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String message = "Never Miss A Thing About Ration Card. Install One Ration Card App and Stay Updated! \n https://play.google.com/store/apps/details?id=tbc.uncagedmist.rationcard";
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, "Share One Ration Card App Using"));
            }
        });

        cardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit();
            }
        });
    }

    private void loadFullscreen() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(
                context,
                context.getString(R.string.ADMOB_FULL),
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Log.d("TAG", "The ad was dismissed.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                Log.d("TAG", "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                mInterstitialAd = null;
                                Log.d("TAG", "The ad was shown.");
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });
    }

    private void feedback() {
        Task<ReviewInfo> request = manager.requestReviewFlow();

        request.addOnCompleteListener(task -> {
            if (task.isSuccessful())    {
                reviewInfo = task.getResult();

                Task<Void> flow = manager.launchReviewFlow((Activity)context,reviewInfo);

                flow.addOnSuccessListener(result -> {
                });
            }
            else {
                Toast.makeText(getActivity(), "ERROR...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void exit() {
        new FancyAlertDialog.Builder((Activity)context)
                .setTitle("Good-Bye")
                .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
                .setMessage("Do You Want to Step Out?")
                .setNegativeBtnText("Exit")
                .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("Rate US")
                .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                .setAnimation(Animation.POP)
                .isCancellable(true)
                .setIcon(R.drawable.ic_star_border_black_24dp, Icon.Visible)
                .OnPositiveClicked(() ->
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=tbc.uncagedmist.allgameswallpapers"))))
                .OnNegativeClicked(() -> {
                    getActivity().moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                })
                .build();
    }
}