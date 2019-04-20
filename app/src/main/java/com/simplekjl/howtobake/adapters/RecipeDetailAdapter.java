package com.simplekjl.howtobake.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simplekjl.howtobake.R;
import com.simplekjl.howtobake.models.Ingredient;
import com.simplekjl.howtobake.models.Step;

import java.util.List;


public class RecipeDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int INGREDIENT = 1;
    private static final int STEP = 2;

    private List<Step> mStepList;
    private List<Ingredient> mIngredientList;
    private int mExpandedPosition = -1;

    public RecipeDetailAdapter(List<Step> steps,List<Ingredient> ingredients) {
        this.mStepList = steps;
        this.mIngredientList = ingredients;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context mContext = viewGroup.getContext();
        int itemLayout = R.layout.ingredient_card_item;
        int stepLayout1 = R.layout.ingredient_card_item;
        switch (viewType){
            case INGREDIENT :
                View ingredientsView = LayoutInflater.from(mContext).inflate(itemLayout,viewGroup,false);
                return new IngredientsViewHolder(ingredientsView);
            case STEP :

                View stepView = LayoutInflater.from(mContext).inflate(stepLayout1,viewGroup,false);
                return new StepsViewHolder(stepView);

                default:
                    View view = LayoutInflater.from(mContext).inflate(stepLayout1,viewGroup,false);
                    return new StepsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        switch (getItemViewType(position)){
            case INGREDIENT:
                final int pos = position;
                IngredientsViewHolder holder = (IngredientsViewHolder) viewHolder;
                final boolean isExpanded = position==mExpandedPosition;
                holder.ingredientsList.setVisibility(isExpanded?View.VISIBLE:View.GONE);
                holder.itemView.setActivated(isExpanded);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mExpandedPosition = isExpanded ? -1:pos;
                        notifyItemChanged(pos);
                    }
                });
                break;
            case STEP:
                StepsViewHolder stepsViewHolder = (StepsViewHolder) viewHolder;
                break;
        }
    }


    @Override
    public int getItemCount() {
        return mStepList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return INGREDIENT;
        }else{
         return STEP;
        }
    }


    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        private TextView ingredientsList;

        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientsList = itemView.findViewById(R.id.ingredients_list);
        }

    }

    public class StepsViewHolder extends RecyclerView.ViewHolder {
        public StepsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
