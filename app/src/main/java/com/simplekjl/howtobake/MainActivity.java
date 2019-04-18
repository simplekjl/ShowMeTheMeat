package com.simplekjl.howtobake;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.simplekjl.howtobake.databinding.ActivityMainBinding;
import com.simplekjl.howtobake.models.Recipe;
import com.simplekjl.howtobake.utils.OnItemClickListener;
import com.simplekjl.howtobake.utils.Utils;

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
        mRecipeList = Utils.readRecipesFromString(readJsonFromFileSystem());
        Log.d(TAG, "onCreate: "+mRecipeList);
    }

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
