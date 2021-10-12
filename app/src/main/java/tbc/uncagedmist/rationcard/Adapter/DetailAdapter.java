package tbc.uncagedmist.rationcard.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tbc.uncagedmist.rationcard.Ads.GoogleAds;
import tbc.uncagedmist.rationcard.Common.Common;
import tbc.uncagedmist.rationcard.Common.MyApplicationClass;
import tbc.uncagedmist.rationcard.Fragments.ResultFragment;
import tbc.uncagedmist.rationcard.Interface.IRecyclerItemSelectListener;
import tbc.uncagedmist.rationcard.Model.Product;
import tbc.uncagedmist.rationcard.R;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {

    Context context;
    ArrayList<Product> products;

    public DetailAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public DetailAdapter.DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.layout_details,parent,false);

        if (MyApplicationClass.getInstance().isShowAds())   {
            GoogleAds.loadGoogleFullscreen(context);
        }

        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.DetailViewHolder holder, @SuppressLint("RecyclerView") final int position) {

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
                if (GoogleAds.mInterstitialAd != null)  {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {

                    ResultFragment resultFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context)
                            .getSupportFragmentManager().beginTransaction();

                    Common.CurrentProductUrl = products.get(position).getProductUrl();
                    Common.CurrentProductName = products.get(position).getProductName();

                    transaction.replace(R.id.main_frame,resultFragment).commit();
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
