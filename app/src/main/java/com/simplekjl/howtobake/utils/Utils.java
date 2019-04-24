package com.simplekjl.howtobake.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simplekjl.howtobake.models.Recipe;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public static List<Recipe> readRecipesFromString(String recipes) {
        Gson gson = new GsonBuilder().create();
        return Arrays.asList(gson.fromJson(recipes, Recipe[].class));
    }
    /**
     * Use this method if the app is going to be holding the JSON file from the assets
     *  Example code : mRecipeList = Utils.readRecipesFromString(readJsonFromFileSystem());
     *  read the input stream InputStream inputStream = getAssets().open("recipes.json");
     * @return String with all the values coming from the JSON in assets
     */
    public String readJsonFromFileSystem(InputStream inputStream){
        String json = null;
        try {
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
