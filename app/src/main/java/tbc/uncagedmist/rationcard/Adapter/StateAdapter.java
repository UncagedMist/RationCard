package tbc.uncagedmist.rationcard.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import tbc.uncagedmist.rationcard.Common.Common;
import tbc.uncagedmist.rationcard.DetailsActivity;
import tbc.uncagedmist.rationcard.Interface.IRecyclerItemSelectListener;
import tbc.uncagedmist.rationcard.Interface.IStateLoadListener;
import tbc.uncagedmist.rationcard.Model.State;
import tbc.uncagedmist.rationcard.R;
import tbc.uncagedmist.rationcard.StateActivity;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.StateViewHolder> {

    Context context;
    List<State> stateList;
    List<CardView> cardViewList;

    private InterstitialAd mInterstitialAd;

    public StateAdapter(Context context, List<State> stateList) {
        this.context = context;
        this.stateList = stateList;
        cardViewList = new ArrayList<>();

    }

    @NonNull
    @Override
    public StateAdapter.StateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.layout_states,parent,false);

        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-5860770870597755/8496470950");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        return new StateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateAdapter.StateViewHolder holder, final int position) {
        Picasso.get()
                .load(stateList.get(position).getImage())
                .into(holder.stateImage);

        holder.stateName.setText(stateList.get(position).getName());

        holder.cardStates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                else {
                    Log.d("Ad Error", "Ad Not Loaded ");
                    Intent intent = new Intent(context, DetailsActivity.class);
                    Common.CurrentState = stateList.get(position);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }

    public class StateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView stateImage;
        TextView stateName;
        CardView cardStates;

        IRecyclerItemSelectListener iRecyclerItemSelectListener;

        public void setiRecyclerItemSelectListener(IRecyclerItemSelectListener iRecyclerItemSelectListener) {
            this.iRecyclerItemSelectListener = iRecyclerItemSelectListener;
        }

        public StateViewHolder(@NonNull View itemView) {
            super(itemView);

            stateImage = itemView.findViewById(R.id.state_image);
            stateName = itemView.findViewById(R.id.state_name);
            cardStates = itemView.findViewById(R.id.card_states);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iRecyclerItemSelectListener.onItemSelected(view,getAdapterPosition());
        }
    }
}
