package tbc.uncagedmist.rationcard.Model;

import android.view.ViewGroup;

import tbc.uncagedmist.rationcard.Adapter.DrawerAdapter;

public abstract class DrawerItem<T extends DrawerAdapter.DrawerViewHolder> {

    protected boolean isChecked;

    public abstract T createViewHolder(ViewGroup parent);

    public abstract void bindViewHolder(T holder);

    public DrawerItem<T> setChecked(boolean isChecked) {
        this.isChecked = isChecked;
        return this;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public boolean isSelectable() {
        return true;
    }
}

