package com.simplekjl.howtobake.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.simplekjl.howtobake.R;
import com.simplekjl.howtobake.adapters.StepTabAdapter;
import com.simplekjl.howtobake.database.AppDatabase;
import com.simplekjl.howtobake.databinding.FragmentStepTabBinding;
import com.simplekjl.howtobake.models.Recipe;
import com.simplekjl.howtobake.utils.AppExecutors;

import java.util.ArrayList;
import java.util.List;


public class StepTabFragment extends Fragment {

    public static final String STEP_LIST_KEY = "step_list";
    public static final String POSITION = "position";
    public static Recipe mRecipe;
    private FragmentStepTabBinding mBinding;
    private int tabPosition;
    private AppDatabase mDb;
    private List<StepDetailFragment> tabs;


    public StepTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDb = AppDatabase.getInstance(getActivity());
        if (savedInstanceState != null) {

            mRecipe = savedInstanceState.getParcelable(STEP_LIST_KEY);
            tabPosition = savedInstanceState.getInt(POSITION);
        } else if ( !RecipeDetailFragment.isTwoPanel) {
            //safe arguments library
            if (StepTabFragmentArgs.fromBundle(getArguments()) != null) {
                mRecipe = StepTabFragmentArgs.fromBundle(getArguments()).getRecipe();
                tabPosition = StepTabFragmentArgs.fromBundle(getArguments()).getTabPosition();
            }

        } else if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(STEP_LIST_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_tab, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        int numTabs = 0;

        if (mRecipe != null) {
            Log.d("ERRORES", "RECIPE : " + mRecipe.toString());
            numTabs = mRecipe.getStepsList().size();
        }
        // adapter top
        tabs = getTabFragments(numTabs);
        StepTabAdapter adapter = new StepTabAdapter(getFragmentManager(), tabs, getContext());
        //setting  viewPaget adapter
        mBinding.stepViewPager.setAdapter(adapter);
        mBinding.stepTab.setupWithViewPager(mBinding.stepViewPager);

        mBinding.stepViewPager.setCurrentItem(tabPosition);

        //Button listeners
        mBinding.controls.buttonPrevStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.stepViewPager.setCurrentItem(mBinding.stepViewPager.getCurrentItem() - 1, true);
            }
        });
        mBinding.controls.buttonNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.stepViewPager.setCurrentItem(mBinding.stepViewPager.getCurrentItem() + 1, true);
            }
        });
    }


    private void getRecipesFromDb() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //from here extract the list
                mRecipe = mDb.recipeDao().getRecipeById(tabPosition);
                tabs = getTabFragments(mRecipe.getStepsList().size());
            }
        });
    }

    private List<StepDetailFragment> getTabFragments(int stepsNumber) {
        List<StepDetailFragment> tabs = new ArrayList<>();
        for (int position = 0; position < stepsNumber; position++) {
            StepDetailFragment fragment = new StepDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(StepDetailFragment.POSITION_KEY, position);
            bundle.putParcelable(StepDetailFragment.STEP_KEY, mRecipe.getStepsList().get(position));
            fragment.setArguments(bundle);
            tabs.add(fragment);
        }
        return tabs;
    }

    @Override
    public void onPause() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STEP_LIST_KEY, mRecipe);
        bundle.putInt(POSITION, tabPosition);
        onSaveInstanceState(bundle);
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    public void changeTab(Integer position) {
        mBinding.stepViewPager.setCurrentItem(position);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (mRecipe != null) {
            outState.putParcelable(STEP_LIST_KEY, mRecipe);
            if (mRecipe.getStepsList().get(tabPosition) != null) {
                outState.putInt(POSITION, tabPosition);
            }
        }
        super.onSaveInstanceState(outState);
    }
}
