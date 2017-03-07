package xyz.georgihristov.myadds;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by gohv on 02.03.17.
 */

public class TabsAdapter extends FragmentPagerAdapter{

    final int PAGE_COUNT = 2;

    private String tabTitles[] =  { "My Ads", "Place Ad"};
    private Context context;

    public TabsAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MyAdsFragment();
            case 1:
                return new PlaceAdFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }


}
