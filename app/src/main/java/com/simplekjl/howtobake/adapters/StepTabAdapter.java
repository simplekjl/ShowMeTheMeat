package com.simplekjl.howtobake.adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.simplekjl.howtobake.fragments.StepDetailFragment;

import java.util.List;

public class StepTabAdapter extends FragmentStatePagerAdapter {
    private final List<StepDetailFragment> tabs;
    private final String recipeIntro;
    private final String pageTitle;

    public StepTabAdapter(FragmentManager fm, List<StepDetailFragment> tabs, String recipeIntro, String pageTitle) {
        super(fm);
        this.tabs = tabs;
        this.recipeIntro = recipeIntro;
        this.pageTitle = pageTitle;
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
            return recipeIntro;
        }else
            return pageTitle + position;
    }
}
