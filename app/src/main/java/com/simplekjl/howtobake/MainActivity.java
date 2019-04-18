package com.simplekjl.howtobake;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    ActivityMainBinding mBinding;
    //data variables
    private List<Recipe> mRecipeList;
    private OnItemClickListener onItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        if (mRecipeList != null){

        }
        ServiceEndpoints service = ApiClient.getInstance().create(ServiceEndpoints.class);
        Call<List<Recipe>> result = service.getRecipes();
        result.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });

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
