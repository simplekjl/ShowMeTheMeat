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
import com.simplekjl.howtobake.models.Step;
import com.simplekjl.howtobake.utils.OnItemClickListener;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private Context mContext;
    private List<Step> mList;
    private OnItemClickListener onItemClickListener;

    public StepAdapter(List<Step> mStepsList, OnItemClickListener listener) {
        onItemClickListener = listener;
        mList = mStepsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        int itemLayout = R.layout.step_card_item;
        View view = LayoutInflater.from(mContext).inflate(itemLayout, viewGroup, false);

        return new StepAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.seItem(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mStepTitle;
        private CardView mCardView;

         ViewHolder(@NonNull View itemView) {
            super(itemView);
            mStepTitle = itemView.findViewById(R.id.step_title);
            mCardView = itemView.findViewById(R.id.step_card);
        }
        void seItem(Step step) {
            mStepTitle.setText(step.getShortDescription());
            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
