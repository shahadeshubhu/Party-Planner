package com.sjsu.partyplanner.Activities.Parties;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Models.Party;
import com.sjsu.partyplanner.R;

import java.util.ArrayList;

public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.ViewHolder> {

    private final Context mContext;
    private final ArrayList<Party> mParties;
    private final PartyClick listener;

    public PartyAdapter(Context mContext, ArrayList<Party> mParties, PartyClick listener) {
        this.mContext = mContext;
        this.mParties = mParties;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.party_layout, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Party party = mParties.get(position);
        String date = (party.getDate()).toString();

        holder.pName.setText(party.getName());
        holder.pType.setText(party.getType());
        holder.pDateTime.setText(date);

    }

    @Override
    public int getItemCount() {
        return mParties.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView pName;
        private final TextView pType;
        private final TextView pDateTime;
        private final PartyClick pClick;

        public ViewHolder(@NonNull View itemView, PartyClick pClick) {
            super(itemView);

            pName = itemView.findViewById(R.id.pNameLayout);
            pType = itemView.findViewById(R.id.pTypeLayout);
            pDateTime = itemView.findViewById(R.id.dateTimeLayout);

            // OnClickListener
            this.pClick = pClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            pClick.onPartyClick(v, getAdapterPosition());
        }
    }

    public interface PartyClick {
        void onPartyClick(View v, int position);
    }
}
