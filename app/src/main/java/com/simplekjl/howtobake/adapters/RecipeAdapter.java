package com.simplekjl.howtobake.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.simplekjl.howtobake.R;
import com.simplekjl.howtobake.models.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> mList;
    private Context mContext;
    public RecipeAdapter(List<Recipe> mRecipeList) {
        mList = mRecipeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        int itemLayout = R.layout.recipe_item;
        View view = LayoutInflater.from(mContext).inflate(itemLayout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setItem(mList.get(i));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView mRecipeCard;
        private TextView mTitle;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            bindViews(itemView);
        }

        private void bindViews(View itemView) {
            mRecipeCard = (CardView) itemView.findViewById(R.id.recipeCardView);
            mTitle = (TextView) itemView.findViewById(R.id.title);

        }

        void setItem(Recipe recipe) {
            mTitle.setText(recipe.getName());
        }
    }
}
