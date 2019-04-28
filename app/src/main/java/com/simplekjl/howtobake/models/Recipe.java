package com.simplekjl.howtobake.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.simplekjl.howtobake.database.converters.RecipeListsConverter;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "recipe")
public class Recipe implements Parcelable {

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @TypeConverters(RecipeListsConverter.class)
    @SerializedName("ingredients")
    private List<Ingredient> ingredientsList;
    @TypeConverters(RecipeListsConverter.class)
    @SerializedName("steps")
    private List<Step> stepsList;
    @SerializedName("servings")
    private int servings;
    @SerializedName("image")
    private String image;

    public Recipe(int id, String name, List<Ingredient> ingredientsList, List<Step> stepsList, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredientsList = ingredientsList;
        this.stepsList = stepsList;
        this.servings = servings;
        this.image = image;
    }

    private Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredientsList = in.createTypedArrayList(Ingredient.CREATOR);
        stepsList = in.createTypedArrayList(Step.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(ingredientsList);
        dest.writeTypedList(stepsList);
        dest.writeInt(servings);
        dest.writeString(image);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredientsList() {
        if (ingredientsList != null){
            return ingredientsList;
        }else{
            return new ArrayList<>();
        }
    }

    public void setIngredientsList(List<Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Step> getStepsList() {
        return stepsList;
    }

    public void setStepsList(List<Step> stepsList) {
        this.stepsList = stepsList;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
