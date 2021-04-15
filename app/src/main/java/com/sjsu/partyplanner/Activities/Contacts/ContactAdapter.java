package com.sjsu.partyplanner.Activities.Contacts;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.sjsu.partyplanner.Models.Task;
import com.sjsu.partyplanner.Models.User;
import com.sjsu.partyplanner.R;

import java.util.ArrayList;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<User> contacts;
    private ContactAdapter.ContactClick listener;
    private Dialog myDialog;

    // Constructor
    public ContactAdapter(ArrayList<User> contacts, ContactAdapter.ContactClick listener) {
        //this.mContext = mContext;
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

        private ConstraintLayout dialogLayout;
        private TextView cName;
        private TextView cEmail;
        private ContactAdapter.ContactClick rvClick;

        public ViewHolder(@NonNull final View itemView, ContactAdapter.ContactClick rvClick) {
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
            rvClick.onContactClick(v, getAdapterPosition());
        }
    }

    // Interface onClick Method
    public interface ContactClick {
        void onContactClick(View v, int position);
    }
}
