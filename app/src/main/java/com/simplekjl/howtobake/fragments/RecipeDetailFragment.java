package com.simplekjl.howtobake.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simplekjl.howtobake.R;
import com.simplekjl.howtobake.adapters.IngredientAdapter;
import com.simplekjl.howtobake.adapters.StepAdapter;
import com.simplekjl.howtobake.databinding.FragmentRecipeDetailBinding;
import com.simplekjl.howtobake.models.Recipe;
import com.simplekjl.howtobake.utils.StepClickListener;

public class RecipeDetailFragment extends Fragment implements StepClickListener {

    public static boolean isTablet = false;
    public static Recipe mRecipe;
    //adapters
    private IngredientAdapter mIngredientAdapter;
    private StepAdapter mStepAdapter;
    private FragmentRecipeDetailBinding mBinding;

    private UpdateTabListener updateTabListener;

    public RecipeDetailFragment() {
        // nothing to do here
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_recipe_detail, container, false);
        setupView();

        return mBinding.getRoot();
    }

    private void setupView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        // Ingredient list
        mBinding.rvIngredients.setLayoutManager(linearLayoutManager);
        mIngredientAdapter = new IngredientAdapter();
        mIngredientAdapter.setIngredients(mRecipe.getIngredientsList());
        mBinding.rvIngredients.setAdapter(mIngredientAdapter);
        mBinding.rvIngredients.setNestedScrollingEnabled(false);
        // Steps List
        mStepAdapter = new StepAdapter(mRecipe.getStepsList(), this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        mBinding.rvSteps.setLayoutManager(linearLayoutManager2);
        mBinding.rvSteps.setAdapter(mStepAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Set the updateTabListener
        if (context instanceof UpdateTabListener) {
            updateTabListener = (UpdateTabListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement UpdateTabListener");
        }
    }

    @Override
    public void onItemClicked(int position) {
        if (!isTablet) {
            Navigation.findNavController(getView())
                    .navigate(RecipeDetailFragmentDirections
                            .actionRecipeDetailFragmentToStepTabFragment(mRecipe)
                            .setTabPosition(position));

        } else {
            updateTabListener.changeTab(position);
        }
    }

    // Listener to pass data between fragments.
    public interface UpdateTabListener {
        void changeTab(int i);
    }
}
