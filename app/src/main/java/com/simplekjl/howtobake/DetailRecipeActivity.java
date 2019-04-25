package com.simplekjl.howtobake;

import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.simplekjl.howtobake.fragments.RecipeDetailFragment;
import com.simplekjl.howtobake.fragments.StepDetailFragment;
import com.simplekjl.howtobake.fragments.StepTabFragment;
import com.simplekjl.howtobake.models.Recipe;

public class DetailRecipeActivity extends AppCompatActivity implements RecipeDetailFragment.OnFragmentInteractionListener{


    private Recipe mRecipe;
    private boolean isTablet= false;
    //Fragments
    private RecipeDetailFragment mRecipeDetailFragment;
    private StepTabFragment mStepTabFragment;

    private static final String TAG = DetailRecipeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);
        if (getIntent().getExtras() != null) {
            mRecipe = getIntent().getExtras().getParcelable("recipe");
            setTitle(mRecipe.getName());
            RecipeDetailFragment.mRecipe = mRecipe;
            Log.d(TAG, "onCreate: "+ mRecipe);
        }
        if(findViewById(R.id.step_tabs_container)!= null){
            RecipeDetailFragment.isTablet = true;
            setupTwoPanelView();
        }

    }

    private void setupTwoPanelView() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.step_list_container, new RecipeDetailFragment())
                .commit();

        fragmentManager.beginTransaction()
                .replace(R.id.step_tabs_container, new StepTabFragment())
                .commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
