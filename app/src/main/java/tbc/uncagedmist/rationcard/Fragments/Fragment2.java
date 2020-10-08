package tbc.uncagedmist.rationcard.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;

import am.appwise.components.ni.NoInternetDialog;
import tbc.uncagedmist.rationcard.R;
import tbc.uncagedmist.rationcard.StateActivity;

public class Fragment2  extends Fragment {

    LottieAnimationView animationView;
    ImageView imgSkip;
    NoInternetDialog noInternetDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment2,container,false);

        noInternetDialog = new NoInternetDialog.Builder(getContext()).build();

        animationView = root.findViewById(R.id.lottieServices);
        imgSkip = root.findViewById(R.id.imgSkip);

        animationView.playAnimation();

        imgSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), StateActivity.class));
            }
        });

        setUpAnimation();

        return root;
    }

    private void setUpAnimation() {
        animationView.animate()
                .setDuration(5000)
                .setStartDelay(1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        noInternetDialog.onDestroy();
    }
}