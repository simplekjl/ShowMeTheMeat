package com.simplekjl.howtobake.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.simplekjl.howtobake.R;
import com.simplekjl.howtobake.fragments.StepDetailFragment;

import java.util.List;

public class StepTabAdapter extends FragmentStatePagerAdapter {
    private final List<StepDetailFragment> tabs;
    private Context mContext;

    public StepTabAdapter(FragmentManager fm, List<StepDetailFragment> tabs, Context context) {
        super(fm);
        this.tabs = tabs;
        mContext= context;
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return mContext.getString(R.string.intro_title);
        }else
            return mContext.getString(R.string.step_title).concat(String.valueOf(position));
    }
}
