package com.simplekjl.howtobake.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Recipes implements Parcelable {
    private int id;
    private String name;
    private List<Ingredient> ingredientsList;
    private List<Step> stepsList;
    private int servings;
    private String image;

    protected Recipes(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredientsList = in.createTypedArrayList(Ingredient.CREATOR);
        stepsList = in.createTypedArrayList(Step.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<Recipes> CREATOR = new Creator<Recipes>() {
        @Override
        public Recipes createFromParcel(Parcel in) {
            return new Recipes(in);
        }

        @Override
        public Recipes[] newArray(int size) {
            return new Recipes[size];
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
}
