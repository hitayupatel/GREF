package com.example.hitayu.gref11;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Hitayu on 29-08-2017.
 */

public class SectionAdapter extends FragmentPagerAdapter {

    private Context mContext;
    public SectionAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new TCFragment();
        } else if (position == 1) {
            return new RCFragment();
        } else {
            return new SEFragment();
        }
    }



    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.category_tc);
        } else if (position == 1) {
            return mContext.getString(R.string.category_rc);
        } else {
            return mContext.getString(R.string.category_se);

        }
    }
}
