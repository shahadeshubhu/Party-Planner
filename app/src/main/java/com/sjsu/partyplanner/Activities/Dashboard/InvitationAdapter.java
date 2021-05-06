package com.sjsu.partyplanner.Activities.Dashboard;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sjsu.partyplanner.Models.Invitation;
import com.sjsu.partyplanner.R;
import java.util.ArrayList;

public class InvitationAdapter extends RecyclerView.Adapter<InvitationAdapter.ViewHolder> {

    private ArrayList<Invitation> invites;
    private InvitationAdapter.InvitationClick listener;

    // Constructor
    public InvitationAdapter(ArrayList<Invitation> invites, InvitationAdapter.InvitationClick listener) {
        this.invites = invites;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.invitation_layout, parent, false);
        return new InvitationAdapter.ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Invitation invite = invites.get(position);
        // TODO: get party from database using invite's partyID and set it in values

        holder.pName.setText(invite.getPartyId());
        holder.pDateTime.setText(invite.getPartyId());
        holder.pHostName.setText(invite.getHostName());

        // Changing Text Style/Color based on invitation status
        if (invite.getHasRead()) {
            holder.pName.setTypeface(Typeface.DEFAULT);

            // Guest has chosen to go or not to go
            if(invite.getHasSelected()) {
                // Guest is going
                if(invite.isAccepted()) { holder.pName.setTextColor(Color.BLUE); }
                //Guest is not going
                else { holder.pName.setTextColor(Color.GRAY); }
            }
        }

    }

    @Override
    public int getItemCount() {
        return invites.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView pName;
        private TextView pDateTime;
        private TextView pHostName;
        private InvitationAdapter.InvitationClick rvClick;

        public ViewHolder(@NonNull final View itemView, InvitationAdapter.InvitationClick rvClick) {
            super(itemView);

            pName = itemView.findViewById(R.id.invite_pName);
            pDateTime = itemView.findViewById(R.id.invite_pDateTime);
            pHostName = itemView.findViewById(R.id.invite_hostName);

            // OnClickListener
            this.rvClick = rvClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            rvClick.onInviteClick(v, getAdapterPosition());
        }
    }

    // Interface onClick Method
    public interface InvitationClick {
        void onInviteClick(View v, int position);
    }
}
