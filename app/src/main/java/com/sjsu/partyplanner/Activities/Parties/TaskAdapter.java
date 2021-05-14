package com.sjsu.partyplanner.Activities.Parties;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Models.Task;
import com.sjsu.partyplanner.R;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private final ArrayList<Task> mTasks;
    private final TaskClick listener;

    // Constructor
    public TaskAdapter(ArrayList<Task> mTasks, TaskClick listener) {
        this.mTasks = mTasks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Task task = mTasks.get(position);

        holder.tName.setText(task.getName());
        holder.tStatus.setText(task.getStatus());
        holder.tCompleted.setText(task.getCompleted());

        // Changes text color based on status
        int completed = task.getCompletedSubtasks();
        if (completed == task.getTotalSubtasks()) { holder.tStatus.setTextColor(Color.parseColor("#037d50")); }     // Dark Green
        else if (completed == 0) { holder.tStatus.setTextColor(Color.RED); }
        else { holder.tStatus.setTextColor(Color.BLUE); }
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tName;
        private final TextView tStatus;
        private final TextView tCompleted;    // completed subtasks / total
        private final TaskClick rvClick;

        public ViewHolder(@NonNull final View itemView, TaskClick rvClick) {
            super(itemView);
            tName = itemView.findViewById(R.id.tNameLayout);
            tStatus = itemView.findViewById(R.id.tStatusLayout);
            tCompleted = itemView.findViewById(R.id.tSubtaskLayout);

            // OnClickListener
            this.rvClick = rvClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            rvClick.onTaskClick(v, getAdapterPosition());
        }
    }

    // Interface onClick Method
    public interface TaskClick {
        void onTaskClick(View v, int position);
    }

}
