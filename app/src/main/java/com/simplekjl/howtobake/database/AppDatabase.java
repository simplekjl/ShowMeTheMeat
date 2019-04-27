package com.simplekjl.howtobake.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.simplekjl.howtobake.models.Recipe;

@Database(entities = {Recipe.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "recipes";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            Log.d(LOG_TAG, "Creating new Database instance");
            sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                    AppDatabase.DATABASE_NAME).build();
        }
        Log.d(LOG_TAG, "Getting database Instance");
        return sInstance;
    }

    public abstract RecipeDao recipeDao();
}
