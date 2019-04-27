package com.simplekjl.howtobake.database;

import androidx.room.Query;

import com.simplekjl.howtobake.models.Recipe;

import java.util.List;

public interface RecipeDao {

    @Query("SELECT * FROM recipe")
    List<Recipe> getRecipes();

    @Query("SELECT * FROM recipe WHERE id == :recipeID")
    Recipe getRecipeById(int recipeId);
}
