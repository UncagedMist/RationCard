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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import tbc.uncagedmist.rationcard.Common.Common;
import tbc.uncagedmist.rationcard.Interface.IRecyclerItemSelectListener;
import tbc.uncagedmist.rationcard.Model.Detail;
import tbc.uncagedmist.rationcard.R;
import tbc.uncagedmist.rationcard.ResultActivity;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {

    Context context;
    List<Detail> detailList;
    List<CardView> cardViewList;

    public DetailAdapter(Context context, List<Detail> detailList) {
        this.context = context;
        this.detailList = detailList;
        cardViewList = new ArrayList<>();
    }

    @NonNull
    @Override
    public DetailAdapter.DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.layout_details,parent,false);

        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.DetailViewHolder holder, final int position) {
        Picasso.get()
                .load(detailList.get(position).getImage())
                .into(holder.detailImage);

        holder.detailName.setText(detailList.get(position).getName());

        holder.cardDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ResultActivity.class);
                Common.CurrentDetail = detailList.get(position);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return detailList.size();
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
