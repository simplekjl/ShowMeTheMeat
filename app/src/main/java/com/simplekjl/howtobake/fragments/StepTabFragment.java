package com.simplekjl.howtobake.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.simplekjl.howtobake.R;
import com.simplekjl.howtobake.adapters.StepTabAdapter;
import com.simplekjl.howtobake.databinding.FragmentRecipeDetailBinding;

import java.util.ArrayList;
import java.util.List;


public class StepTabFragment extends Fragment {


    private int mTabPosition;
    private FragmentRecipeDetailBinding mBinding;
    private int tabPosition;
    private TabLayout stepTabLayout;
    private ViewPager stepViewPager;
    private Button prevStep;
    private Button nextStep;
    private String recipeIntro;
    private String pageTitle;

    private OnTabListener mListener;

    public StepTabFragment() {
        // Required empty public constructor
    }


    public static StepTabFragment newInstance(String param1, String param2) {
        StepTabFragment fragment = new StepTabFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.setContentView((Activity) inflater.getContext(), R.layout.fragment_step_tab);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //TODO get the recipe from DB or somewhere else
        //TODO populate the fragments in the adapter tan
        List<StepDetailFragment> tabs = new ArrayList<>();
         tabs = getTabFragments();
        StepTabAdapter adapter = new StepTabAdapter(getFragmentManager(),tabs,"Intro","Step:");

    }

    private List<StepDetailFragment> getTabFragments() {
        //TODO get the real number os steps need it
        List<StepDetailFragment> tabs = new ArrayList<>();
        int tabsNumber = 12;
        for (int i = 0; i < tabsNumber; i++) {
            StepDetailFragment fragment = new StepDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(StepDetailFragment.POSITION_KEY,i);
            tabs.add(fragment);
        }
        return tabs;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Integer position) {
        if (mListener != null) {
            mListener.onTabClicked(position);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTabListener) {
            mListener = (OnTabListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void changeTab(Integer position) {
        stepViewPager.setCurrentItem(position);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTabListener {
        void onTabClicked(Integer position);
    }
}
