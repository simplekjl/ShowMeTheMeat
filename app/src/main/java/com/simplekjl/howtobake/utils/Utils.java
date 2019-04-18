package com.simplekjl.howtobake.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simplekjl.howtobake.models.Recipe;

import java.util.Arrays;
import java.util.List;

public class Utils {

    public static List<Recipe> readRecipesFromString(String recipes) {
        Gson gson = new GsonBuilder().create();
        return Arrays.asList(gson.fromJson(recipes, Recipe[].class));
    }
}
