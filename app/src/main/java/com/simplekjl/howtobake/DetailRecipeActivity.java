package com.simplekjl.howtobake;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.simplekjl.howtobake.fragments.RecipeDetailFragment;
import com.simplekjl.howtobake.models.Ingredient;
import com.simplekjl.howtobake.models.Recipe;

import java.util.List;

public class DetailRecipeActivity extends AppCompatActivity implements RecipeDetailFragment.OnFragmentInteractionListener{


    private Recipe mRecipe;
    private static final String TAG = DetailRecipeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);
        if (getIntent().getExtras() != null) {
            mRecipe = getIntent().getExtras().getParcelable("recipe");
            Log.d(TAG, "onCreate: "+ mRecipe);
        }

        // In two-pane mode, add initial BodyPartFragments to the screen
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Creating a new head fragment
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setIngredientsList(mRecipe.getIngredientsList());
        recipeDetailFragment.setStepsList(mRecipe.getStepsList());
        // Add the fragment to its container using a transaction
        fragmentManager.beginTransaction()
                .add(R.id.detail_fragment, recipeDetailFragment)
                .commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
