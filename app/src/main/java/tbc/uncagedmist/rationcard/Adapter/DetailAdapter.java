package tbc.uncagedmist.rationcard.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.squareup.picasso.Callback;
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

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(
                context,
                context.getString(R.string.SAMPLE_Interstitial_ID),
                adRequest, new InterstitialAdLoadCallback() {
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

        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.DetailViewHolder holder, final int position) {

        holder.progressBar.setVisibility(View.VISIBLE);

        Picasso.get()
                .load(products.get(position).getProductImage())
                .into(holder.detailImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.detailName.setText(products.get(position).getProductName());
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

        holder.cardDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show((Activity) context);
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

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class DetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView detailImage;
        TextView detailName;
        CardView cardDetails;
        ProgressBar progressBar;

        IRecyclerItemSelectListener iRecyclerItemSelectListener;

        public void setiRecyclerItemSelectListener(IRecyclerItemSelectListener iRecyclerItemSelectListener) {
            this.iRecyclerItemSelectListener = iRecyclerItemSelectListener;
        }

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);

            detailImage = itemView.findViewById(R.id.avatar_image);
            detailName = itemView.findViewById(R.id.txtTitle);
            cardDetails = itemView.findViewById(R.id.card_details);
            progressBar = itemView.findViewById(R.id.progress_bar);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iRecyclerItemSelectListener.onItemSelected(view,getAdapterPosition());
        }
    }
}
