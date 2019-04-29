package com.simplekjl.howtobake.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.simplekjl.howtobake.RecipesWidget;
import com.simplekjl.howtobake.database.AppDatabase;
import com.simplekjl.howtobake.models.Recipe;
import com.simplekjl.howtobake.utils.AppExecutors;


public class UpdateWidgetService extends IntentService {

    private static final String ACTION_UPDATE_WIDGET = "com.simplekjl.howtobake.action.update_baking_widget";
    private static final String RECIPE_ID = "recipe_id";


    public UpdateWidgetService() {
        super("HowToCook");
    }


    /**
     * Service that updates the widget
     *
     * @param context
     */
    public static void startActionUpdateWidget(Context context, int recipeID) {
        Intent intent = new Intent(context, UpdateWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        intent.putExtra(RECIPE_ID, recipeID);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            switch (action) {
                case ACTION_UPDATE_WIDGET:
                    onActionOpenRecipe(intent.getIntExtra(RECIPE_ID, 0));
            }
        }
    }

    private void onActionOpenRecipe(final int intExtra) {
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        final int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipesWidget.class));
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Recipe recipe = AppDatabase.getInstance(getApplicationContext())
                        .recipeDao().getRecipeById(intExtra);
                RecipesWidget.onUpdate(getApplicationContext(), appWidgetManager, recipe, appWidgetIds);
            }
        });
    }


}
