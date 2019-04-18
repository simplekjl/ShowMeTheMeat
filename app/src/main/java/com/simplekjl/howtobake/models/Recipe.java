package com.simplekjl.howtobake.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Recipe implements Parcelable {
    private int id;
    private String name;
    private List<Ingredient> ingredientsList;
    private List<Step> stepsList;
    private int servings;
    private String image;

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredientsList = in.createTypedArrayList(Ingredient.CREATOR);
        stepsList = in.createTypedArrayList(Step.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredientsList() {
        return ingredientsList;
    }

    public List<Step> getStepsList() {
        return stepsList;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }
}
