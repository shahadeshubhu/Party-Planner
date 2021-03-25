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

    private Context mContext;
    private ArrayList<Party> mParties;

    public PartyAdapter(Context mContext, ArrayList<Party> mParties) {
        this.mContext = mContext;
        this.mParties = mParties;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.party_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //TODO: NEEDS TO ADD TIME (Model does not contain a time value)
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView pName;
        private TextView pType;
        private TextView pDateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pName = itemView.findViewById(R.id.pNameLayout);
            pType = itemView.findViewById(R.id.pTypeLayout);
            pDateTime = itemView.findViewById(R.id.dateTimeLayout);


        }
    }
}
