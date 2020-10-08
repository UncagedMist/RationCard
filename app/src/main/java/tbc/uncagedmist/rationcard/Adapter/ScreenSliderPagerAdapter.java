package tbc.uncagedmist.rationcard.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import tbc.uncagedmist.rationcard.Fragments.Fragment1;
import tbc.uncagedmist.rationcard.Fragments.Fragment2;
import tbc.uncagedmist.rationcard.Fragments.Fragment3;

public class ScreenSliderPagerAdapter extends FragmentStatePagerAdapter {

    public static final int NUM_PAGES = 3;

    public ScreenSliderPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)   {
            case 0:
                Fragment1 tab1 = new Fragment1();
                return tab1;
            case 1:
                Fragment2 tab2 = new Fragment2();
                return tab2;
            case 2:
                Fragment3 tab3 = new Fragment3();
                return tab3;
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}