package com.simplekjl.howtobake;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.simplekjl.howtobake.fragments.RecipeDetailFragment;
import com.simplekjl.howtobake.fragments.StepTabFragment;
import com.simplekjl.howtobake.models.Recipe;
import com.simplekjl.howtobake.utils.OnItemClickListener;

public class DetailRecipeActivity extends AppCompatActivity implements
        RecipeDetailFragment.UpdateTabListener {

    private static final String TAG = DetailRecipeActivity.class.getName();
    public static String RECIPE_KEY = "recipe";
    // values
    private Recipe mRecipe;

    //Fragments
    private RecipeDetailFragment mRecipeDetailFragment = new RecipeDetailFragment();
    private StepTabFragment mStepTabFragment = new StepTabFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);
        if (getIntent().getExtras() != null) {
            mRecipe = getIntent().getExtras().getParcelable(RECIPE_KEY);
            setTitle(mRecipe.getName());
            RecipeDetailFragment.mRecipe = mRecipe;
            Log.d(TAG, "onCreate: " + mRecipe);
        }
        if (findViewById(R.id.step_tabs_container) != null) {
            RecipeDetailFragment.isTablet = true;
            Bundle bundle = new Bundle();
            bundle.putParcelable(StepTabFragment.STEP_LIST_KEY,mRecipe);
            mStepTabFragment.setArguments(bundle);
            setupTwoPanelView();
        }

    }

    private void setupTwoPanelView() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.step_list_container, mRecipeDetailFragment)
                .commit();

        fragmentManager.beginTransaction()
                .replace(R.id.step_tabs_container, mStepTabFragment)
                .commit();
    }


    @Override
    public boolean onSupportNavigateUp() {
        if (!RecipeDetailFragment.isTablet) {
            return Navigation.findNavController(this, R.id.recipe_navigation).navigateUp();
        } else {
            return super.onSupportNavigateUp();
        }
    }

    @Override
    public void changeTab(int position) {
        mStepTabFragment.changeTab(position);
    }
}
