package com.simplekjl.howtobake.database.converters;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.simplekjl.howtobake.models.Ingredient;
import com.simplekjl.howtobake.models.Step;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class RecipeListsConverter {
    static Gson gson = new Gson();

    // Ingredients
    @TypeConverter
    public static List<Ingredient> stringToIngredientList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Ingredient>>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ingredientsListToString(List<Ingredient> list) {
        return gson.toJson(list);
    }

    //Steps
    @TypeConverter
    public static List<Step> stringToStepsList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Step>>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String stepsListToString(List<Step> list) {
        return gson.toJson(list);
    }
}
