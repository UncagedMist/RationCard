package tbc.uncagedmist.rationcard.Fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tbc.uncagedmist.rationcard.R;


public class SettingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//                builder.setToolbarColor(Color.parseColor("#008000"));
//
//                openCustomTabs(ResultActivity.this,builder.build(), Uri.parse(Common.WIN_URL));
//            }
//        });
    }

//    private static void openCustomTabs(Activity activity, CustomTabsIntent customTabsIntent, Uri uri)    {
//        String packageName = "com.android.chrome";
//
//        try {
//
//            customTabsIntent.intent.setPackage(packageName);
//            customTabsIntent.launchUrl(activity,uri);
//        }
//        catch(ActivityNotFoundException ex) {
//            activity.startActivity(new Intent(Intent.ACTION_VIEW,uri));
//        }
//    }
}