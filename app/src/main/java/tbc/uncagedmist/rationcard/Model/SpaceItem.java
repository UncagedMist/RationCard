package tbc.uncagedmist.rationcard.Model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import tbc.uncagedmist.rationcard.Adapter.DrawerAdapter;

public class SpaceItem extends DrawerItem<SpaceItem.SpaceViewHolder> {

    private int spaceDP;

    public SpaceItem(int spaceDP) {
        this.spaceDP = spaceDP;
    }

    @Override
    public SpaceViewHolder createViewHolder(ViewGroup parent) {
        Context c = parent.getContext();
        View view = new View(c);
        int height = (int) (c.getResources().getDisplayMetrics().density * spaceDP);
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                height));
        return new SpaceViewHolder(view);
    }

    @Override
    public void bindViewHolder(SpaceViewHolder holder) {
    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    public static class SpaceViewHolder extends DrawerAdapter.DrawerViewHolder {

        public SpaceViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
