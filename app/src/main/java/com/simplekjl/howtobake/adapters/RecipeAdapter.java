package com.simplekjl.howtobake.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.simplekjl.howtobake.R;
import com.simplekjl.howtobake.models.Recipe;
import com.simplekjl.howtobake.utils.BakingImagesAssets;
import com.simplekjl.howtobake.utils.OnItemClickListener;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> mList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public RecipeAdapter(List<Recipe> mRecipeList, OnItemClickListener listener) {
        mList = mRecipeList;
        onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        int itemLayout = R.layout.recipe_item;
        View view = LayoutInflater.from(mContext).inflate(itemLayout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setItem(mList.get(i), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView mRecipeCard;
        private TextView mTitle;
        private TextView mServings;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            bindViews(itemView);
        }

        private void bindViews(View itemView) {
            mRecipeCard = (CardView) itemView.findViewById(R.id.recipeCardView);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mServings = (TextView) itemView.findViewById(R.id.servings);
        }

        void setItem(final Recipe recipe, final OnItemClickListener onItemClickListener) {
            mTitle.setText(recipe.getName());
            mRecipeCard.setBackground(mContext.getDrawable(BakingImagesAssets.getBackgrounds().get(recipe.getId())));
            mServings.setText(String.valueOf(recipe.getId()));
            mRecipeCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(recipe);
                }
            });
        }
    }
}
