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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tbc.uncagedmist.rationcard.Common.Common;
import tbc.uncagedmist.rationcard.DetailsActivity;
import tbc.uncagedmist.rationcard.Interface.IRecyclerItemSelectListener;
import tbc.uncagedmist.rationcard.Model.State;
import tbc.uncagedmist.rationcard.R;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.StateViewHolder> {

    Context context;
    ArrayList<State> states;

    private InterstitialAd mInterstitialAd;

    public StateAdapter(Context context, ArrayList<State> states) {
        this.context = context;
        this.states = states;
    }

    @NonNull
    @Override
    public StateAdapter.StateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.layout_states,parent,false);

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

        return new StateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateAdapter.StateViewHolder holder, final int position) {

        holder.progressBar.setVisibility(View.VISIBLE);

        Picasso.get()
                .load(states.get(position).getStateImage())
                .into(holder.stateImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.stateName.setText(states.get(position).getStateName());
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        holder.cardStates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show((Activity) context);
                }
                else {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    Common.CurrentStateId = states.get(position).getStateId();
                    Common.CurrentStateName = states.get(position).getStateName();
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class StateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView stateImage;
        TextView stateName;
        CardView cardStates;
        ProgressBar progressBar;

        IRecyclerItemSelectListener iRecyclerItemSelectListener;

        public void setiRecyclerItemSelectListener(IRecyclerItemSelectListener iRecyclerItemSelectListener) {
            this.iRecyclerItemSelectListener = iRecyclerItemSelectListener;
        }

        public StateViewHolder(@NonNull View itemView) {
            super(itemView);

            stateImage = itemView.findViewById(R.id.state_image);
            stateName = itemView.findViewById(R.id.state_name);
            cardStates = itemView.findViewById(R.id.card_states);
            progressBar = itemView.findViewById(R.id.progress_bar);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iRecyclerItemSelectListener.onItemSelected(view,getAdapterPosition());
        }
    }
}
