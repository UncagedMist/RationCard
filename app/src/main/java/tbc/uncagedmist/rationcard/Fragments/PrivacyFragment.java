package tbc.uncagedmist.rationcard.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import tbc.uncagedmist.rationcard.Common.Common;
import tbc.uncagedmist.rationcard.R;
import tbc.uncagedmist.rationcard.Utility.CustomLoadDialog;
import tbc.uncagedmist.rationcard.Utility.CustomProgressDialog;

public class PrivacyFragment extends Fragment {

    View myFragment;

    WebView webView;

    FloatingActionButton privacyShare;

    CustomLoadDialog loadDialog;
    CustomProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragment =  inflater.inflate(R.layout.fragment_privacy, container, false);

        loadDialog = new CustomLoadDialog(getContext());
        progressDialog = new CustomProgressDialog(getContext());

        loadDialog.showDialog();

        webView = myFragment.findViewById(R.id.webPrivacy);

        privacyShare = myFragment.findViewById(R.id.privacyShare);

        AdRequest adRequest = new AdRequest.Builder().build();

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

        return myFragment;
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
}