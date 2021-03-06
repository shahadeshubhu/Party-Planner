package com.sjsu.partyplanner.Activities.Parties;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Models.Subtask;
import com.sjsu.partyplanner.R;

import java.util.ArrayList;

public class SubtaskAdapter extends RecyclerView.Adapter<SubtaskAdapter.ViewHolder> {

    private final ArrayList<Subtask> mSubtasks;
    private final OnSubtaskListener mOnSubtaskListener;

    // Constructor
    public SubtaskAdapter(ArrayList<Subtask> mSubtasks, OnSubtaskListener onSubtaskListener) {
        this.mSubtasks = mSubtasks;
        this.mOnSubtaskListener = onSubtaskListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.subtask_row_layout, parent, false);
        return new SubtaskAdapter.ViewHolder(view, mOnSubtaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Subtask task = mSubtasks.get(position);

        holder.stName.setText(task.getName());

        // Complete - show complete image
        if (task.getSubtaskStatusBool()) {
            holder.stStatus.setImageResource(R.drawable.subtask_done);
        }
        else {
            holder.stStatus.setImageResource(R.drawable.subtask_empty);
        }
    }

    @Override
    public int getItemCount() {
        return mSubtasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView stName;
        private final ImageView stStatus;

        public ViewHolder(@NonNull View itemView, OnSubtaskListener onSubtaskListener) {
            super(itemView);
            stName = itemView.findViewById(R.id.stNameTextLayout);
            stStatus = itemView.findViewById(R.id.subtaskStatusLayout);

            stStatus.setOnClickListener(v -> onSubtaskListener.OnSubtaskClick(getAdapterPosition()));
        }
    }

    public interface OnSubtaskListener {
        void OnSubtaskClick(int position);
    }
}
