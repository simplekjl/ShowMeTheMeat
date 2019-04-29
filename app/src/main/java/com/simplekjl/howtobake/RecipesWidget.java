package com.simplekjl.howtobake;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.simplekjl.howtobake.models.Ingredient;
import com.simplekjl.howtobake.models.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class RecipesWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, Recipe recipe,
                                int appWidgetId) {

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipes_widget);
        views.setTextViewText(R.id.title, recipe.getName());

        // remove any views from the container
        views.removeAllViews(R.id.widget_ingredients_container);

        // Add a new view to the container for each ingredient found in ingredientList
        for (Ingredient ingredient : recipe.getIngredientsList()) {
            RemoteViews ingredientsView = new RemoteViews(context.getPackageName(), R.layout.ingredient_card_item);
            ingredientsView.setTextViewText(R.id.ingredient_name, ingredient.getIngredient());
            views.addView(R.id.widget_ingredients_container, ingredientsView);
        }

        // Set click listener on container to open main activity.
        views.setOnClickPendingIntent(R.id.widget_ingredients_container, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void onUpdate(Context context, AppWidgetManager appWidgetManager, Recipe recipe, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipe, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

