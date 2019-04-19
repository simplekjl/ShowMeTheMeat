package com.simplekjl.howtobake;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import com.simplekjl.howtobake.adapters.RecipeAdapter;
import com.simplekjl.howtobake.databinding.ActivityMainBinding;
import com.simplekjl.howtobake.models.Recipe;
import com.simplekjl.howtobake.network.ApiClient;
import com.simplekjl.howtobake.network.ServiceEndpoints;
import com.simplekjl.howtobake.utils.OnItemClickListener;
import com.simplekjl.howtobake.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final String RECIPES = "RECIPES";
    ActivityMainBinding mBinding;
    //data variables
    private List<Recipe> mRecipeList;
    private RecipeAdapter mRecipeAdapter;
    private OnItemClickListener onItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        if (mRecipeList != null){
            mRecipeAdapter = new RecipeAdapter(mRecipeList);
            showResults();
        }else{
            getRecipes();
        }


    }

    private void getRecipes() {
        ServiceEndpoints service = ApiClient.getInstance().create(ServiceEndpoints.class);
        Call<List<Recipe>> result = service.getRecipes();
        showLoader();
        result.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if(response.isSuccessful()){
                    if(response.body()!= null){
                        mRecipeList = response.body();
                        mRecipeAdapter = new RecipeAdapter(mRecipeList);
                        showResults();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                showErrorMessage();
                Log.e(TAG, "Recipes Object call failed: " + t.getMessage());
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBinding.rvRecipes.setLayoutManager(layoutManager);
        mBinding.rvRecipes.setAdapter(mRecipeAdapter);
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
    /**
     * Use this method if the app is going to be holding the JSON file from the assets
     *  Example code : mRecipeList = Utils.readRecipesFromString(readJsonFromFileSystem());
     * @return String with all the values coming from the JSON in assets
     */
    public String readJsonFromFileSystem(){
        String json = null;
        try {
            InputStream inputStream = getAssets().open("recipes.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
