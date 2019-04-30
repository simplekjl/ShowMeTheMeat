package com.simplekjl.howtobake;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.simplekjl.howtobake.adapters.RecipeAdapter;
import com.simplekjl.howtobake.database.AppDatabase;
import com.simplekjl.howtobake.databinding.ActivityMainBinding;
import com.simplekjl.howtobake.models.Recipe;
import com.simplekjl.howtobake.network.ApiClient;
import com.simplekjl.howtobake.network.ServiceEndpoints;
import com.simplekjl.howtobake.utils.AppExecutors;
import com.simplekjl.howtobake.utils.OnItemClickListener;
import com.simplekjl.howtobake.widget.UpdateWidgetService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/*
 * check links https://stackoverflow.com/questions/10347846/how-to-make-a-gridlayout-fit-screen-size/34381245
 *
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final String RECIPES = "RECIPES";
    ActivityMainBinding mBinding;
    //data variables
    private List<Recipe> mRecipeList;
    private RecipeAdapter mRecipeAdapter;
    private Context mContext;
    // database
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mDb = AppDatabase.getInstance(this);
        mContext = this;
        if (mRecipeList != null) {
            showResults();
        } else if (isOnline()) {
            getRecipes();
        } else {
            getRecipesFromDb();
        }
    }

    private void storeRecipes() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                for (Recipe item : mRecipeList)
                    mDb.recipeDao().insertRecipe(item);
            }
        });
    }

    private void getRecipes() {
        ServiceEndpoints service = ApiClient.getInstance().create(ServiceEndpoints.class);
        Call<List<Recipe>> result = service.getRecipes();
        showLoader();
        result.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mRecipeList = response.body();
                        storeRecipes();
                        showResults();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                showErrorMessage();
            }
        });
    }

    private void getRecipesFromDb() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mRecipeList = mDb.recipeDao().getRecipes();
                if (mRecipeList != null) {
                    showResults();
                } else {
                    showErrorMessage();
                    Log.d(TAG, "No items in DB");
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mRecipeList != null) {
            outState.putParcelableArrayList(MainActivity.RECIPES, (ArrayList<? extends Parcelable>) mRecipeList);
        }
        super.onSaveInstanceState(outState);
    }

    void showLoader() {
        mBinding.progressBar.setVisibility(View.VISIBLE);
        mBinding.rvRecipes.setVisibility(View.INVISIBLE);
        mBinding.errorMessage.setVisibility(View.INVISIBLE);
    }

    private void showResults() {
        mBinding.progressBar.setVisibility(View.INVISIBLE);
        mBinding.rvRecipes.setVisibility(View.VISIBLE);
        mBinding.errorMessage.setVisibility(View.INVISIBLE);
        //setup recyclerView
        int columns = numberOfColums();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, columns);
        mBinding.rvRecipes.setLayoutManager(gridLayoutManager);
        OnItemClickListener onItemClickListener = new OnItemClickListener<Recipe>() {
            @Override
            public void onItemClick(Recipe recipe) {
                Intent intent = new Intent(mContext, DetailRecipeActivity.class);
                intent.putExtra(DetailRecipeActivity.RECIPE_KEY,recipe);
                updateWidget(recipe.getId());
                startActivity(intent);
            }
        };
        mRecipeAdapter = new RecipeAdapter(mRecipeList, onItemClickListener);
        mBinding.rvRecipes.setAdapter(mRecipeAdapter);
    }

    void updateWidget(int id){
        //update widget
        UpdateWidgetService.startActionUpdateWidget(mContext,id);
    }
    //region Error Message
    void showErrorMessage() {
        mBinding.progressBar.setVisibility(View.INVISIBLE);
        mBinding.rvRecipes.setVisibility(View.INVISIBLE);
        mBinding.errorMessage.setVisibility(View.VISIBLE);
    }
    //endRegion error message

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    int numberOfColums() {
        Configuration configuration = getResources().getConfiguration();
        if (configuration.isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_SMALL) ||
                getResources()
                        .getConfiguration()
                        .isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_NORMAL)) {
            return 1;
        } else if (configuration.isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE)) {
            return 2;
        } else if (configuration.isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_XLARGE)) {
            return 3;
        } else {
            //default value
            return 1;
        }
    }

}
