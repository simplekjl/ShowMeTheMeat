package com.simplekjl.howtobake.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.simplekjl.howtobake.R;
import com.simplekjl.howtobake.models.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private Context mContext;
    private List<Ingredient> mList;

    public void setIngredients(List<Ingredient> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        int itemLayout = R.layout.ingredient_card_item;
        View view = LayoutInflater.from(mContext).inflate(itemLayout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.setItem(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView quantity;
        TextView measure;
        TextView name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quantity = itemView.findViewById(R.id.quantity_value);
            measure = itemView.findViewById(R.id.measure);
            name = itemView.findViewById(R.id.ingredient_name);
        }

        public void setItem(Ingredient ingredient) {
            quantity.setText(ingredient.getQuantity());
            measure.setText(ingredient.getMeasure());
            name.setText(ingredient.getIngredient());
        }
    }
}
