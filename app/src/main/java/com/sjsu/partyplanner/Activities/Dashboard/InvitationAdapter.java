package com.sjsu.partyplanner.Activities.Dashboard;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Activities.Parties.TaskAdapter;
import com.sjsu.partyplanner.R;

public class InvitationAdapter {



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tName;
        private TextView tStatus;
        private TextView tCompleted;    // completed subtasks / total
        private TaskAdapter.TaskClick rvClick;

        public ViewHolder(@NonNull final View itemView, TaskAdapter.TaskClick rvClick) {
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
    public interface InvitationClick {
        void onInviteClick(View v, int position);
    }
}
