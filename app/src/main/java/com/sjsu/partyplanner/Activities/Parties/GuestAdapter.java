package com.sjsu.partyplanner.Activities.Parties;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Models.Guest;
import com.sjsu.partyplanner.R;

import java.util.ArrayList;

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.ViewHolder> {

    private ArrayList<Guest> guests;
    private GuestAdapter.GuestClick listener;

    // Constructor
    public GuestAdapter(ArrayList<Guest> guests, GuestAdapter.GuestClick listener) {
        this.guests = guests;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GuestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contact_layout, parent, false);
        return new GuestAdapter.ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestAdapter.ViewHolder holder, int position) {
        final Guest guest = guests.get(position);
        holder.cName.setText(guest.getName());
        holder.cEmail.setText(guest.getEmail());
    }

    @Override
    public int getItemCount() {
        return guests.size();
    }


    // Same As Contactadapter
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ConstraintLayout dialogLayout;
        private TextView cName;
        private TextView cEmail;
        private GuestAdapter.GuestClick rvClick;

        public ViewHolder(@NonNull final View itemView, GuestAdapter.GuestClick rvClick) {
            super(itemView);
            dialogLayout = (ConstraintLayout) itemView.findViewById(R.id.contact_item_id);
            cName = itemView.findViewById(R.id.cName);
            cEmail = itemView.findViewById(R.id.cEmail);

            // OnClickListener
            this.rvClick = rvClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            rvClick.onGuestClick(v, getAdapterPosition());
        }
    }

    // Interface onClick Method
    public interface GuestClick {
        void onGuestClick(View v, int position);
    }



}
