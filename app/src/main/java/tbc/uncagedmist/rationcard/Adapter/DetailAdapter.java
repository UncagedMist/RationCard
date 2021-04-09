package tbc.uncagedmist.rationcard.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tbc.uncagedmist.rationcard.Common.Common;
import tbc.uncagedmist.rationcard.Interface.IRecyclerItemSelectListener;
import tbc.uncagedmist.rationcard.Model.Product;
import tbc.uncagedmist.rationcard.R;
import tbc.uncagedmist.rationcard.ResultActivity;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {

    Context context;
    ArrayList<Product> products;

    private InterstitialAd mInterstitialAd;

    public DetailAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public DetailAdapter.DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.layout_details,parent,false);

        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-5860770870597755/3989063274");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.DetailViewHolder holder, final int position) {

        Picasso.get()
                .load(products.get(position).getProductImage())
                .into(holder.detailImage);

        holder.detailName.setText(products.get(position).getProductName());

        holder.cardDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                else {
                    Intent intent = new Intent(context, ResultActivity.class);
                    Common.CurrentProductUrl = products.get(position).getProductUrl();
                    Common.CurrentProductName = products.get(position).getProductName();
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
            }
        });

        adMethod();
    }

    private void adMethod() {
        mInterstitialAd.setAdListener(new AdListener() {
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
                // Code to be executed when the ad is displayed.
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
                // Code to be executed when the interstitial ad is closed.
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView detailImage;
        TextView detailName;
        CardView cardDetails;

        IRecyclerItemSelectListener iRecyclerItemSelectListener;

        public void setiRecyclerItemSelectListener(IRecyclerItemSelectListener iRecyclerItemSelectListener) {
            this.iRecyclerItemSelectListener = iRecyclerItemSelectListener;
        }

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);

            detailImage = itemView.findViewById(R.id.avatar_image);
            detailName = itemView.findViewById(R.id.txtTitle);
            cardDetails = itemView.findViewById(R.id.card_details);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iRecyclerItemSelectListener.onItemSelected(view,getAdapterPosition());
        }
    }
}
