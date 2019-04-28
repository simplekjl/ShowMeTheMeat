package com.simplekjl.howtobake.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.simplekjl.howtobake.models.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe")
    List<Recipe> getRecipes();

    @Query("SELECT * FROM recipe WHERE id == :recipeId")
    Recipe getRecipeById(int recipeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(Recipe recipe);
}
