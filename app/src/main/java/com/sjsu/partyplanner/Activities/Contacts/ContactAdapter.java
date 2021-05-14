package com.sjsu.partyplanner.Activities.Contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Models.User;
import com.sjsu.partyplanner.R;

import java.util.ArrayList;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private final ArrayList<User> contacts;
    private final ContactAdapter.ContactClick listener;

    // Constructor
    public ContactAdapter(ArrayList<User> contacts, ContactAdapter.ContactClick listener) {
        this.contacts = contacts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contact_layout, parent, false);
        return new ContactAdapter.ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User contact = contacts.get(position);
        holder.cName.setText(contact.getName());
        holder.cEmail.setText(contact.getEmail());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ConstraintLayout dialogLayout;
        private final TextView cName;
        private final TextView cEmail;
        private final ContactAdapter.ContactClick rvClick;

        public ViewHolder(@NonNull final View itemView, ContactAdapter.ContactClick rvClick) {
            super(itemView);
            dialogLayout = itemView.findViewById(R.id.contact_item_id);
            cName = itemView.findViewById(R.id.cName);
            cEmail = itemView.findViewById(R.id.cEmail);

            // OnClickListener
            this.rvClick = rvClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            rvClick.onContactClick(v, getAdapterPosition());
        }
    }

    // Interface onClick Method
    public interface ContactClick {
        void onContactClick(View v, int position);
    }
}
