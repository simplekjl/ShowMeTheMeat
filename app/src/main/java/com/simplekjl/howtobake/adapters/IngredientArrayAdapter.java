package com.simplekjl.howtobake.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.simplekjl.howtobake.models.Ingredient;

import java.util.List;

public class IngredientArrayAdapter extends ArrayAdapter<Ingredient> {
    private final Context mContext;
    private final List<Ingredient> mList;

    public IngredientArrayAdapter(@NonNull Context context, int resource, @NonNull List<Ingredient> objects) {
        super(context, resource, objects);
        mContext = context;
        mList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //LayoutInflater inflater =
        return super.getView(position, convertView, parent);
    }
}
