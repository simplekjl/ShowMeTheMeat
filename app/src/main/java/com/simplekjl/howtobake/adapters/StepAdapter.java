package com.simplekjl.howtobake.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simplekjl.howtobake.R;
import com.simplekjl.howtobake.models.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private Context mContext;
    private List<Step> mList;

    public StepAdapter(List<Step> mStepsList) {
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mStepTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mStepTitle = itemView.findViewById(R.id.step_title);
        }

        public void seItem(Step step) {
            mStepTitle.setText(step.getShortDescription());
        }
    }
}
