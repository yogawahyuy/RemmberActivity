package org.d3ifcool.rememberactivities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {

    int mNoTabs;

    public PageAdapter(FragmentManager fm, int mumberNoTabs) {
        super(fm);
        this.mNoTabs = mumberNoTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
            TentangSilfi frag1 = new TentangSilfi();
            return frag1;
            case 1 :
                TentangYoga frag2 = new TentangYoga();
                return  frag2;
            case 2 :
                TentangSyifa frag3 = new TentangSyifa();
                return  frag3;
                default:
                    return null;

        }

    }

    @Override
    public int getCount() {
        return mNoTabs;
    }
}
