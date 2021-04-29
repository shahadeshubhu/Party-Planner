package com.sjsu.partyplanner.Activities.Parties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.sjsu.partyplanner.Activities.Contacts.ContactAdapter;
import com.sjsu.partyplanner.Activities.Contacts.ContactDialog;
import com.sjsu.partyplanner.Models.User;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityGuestListBinding;

import java.util.ArrayList;

public class GuestListActivity extends AppCompatActivity implements ContactAdapter.ContactClick {

    private ActivityGuestListBinding binding;
    private Toolbar toolbar;
    private ArrayList<User> guestList = new ArrayList<User>();
    private Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGuestListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //TODO: Get Guest List of party from party details activity
        guestList.add(new User("firstName", "lastName", "email"));

        // Toolbar, RecyclerView
        setUpToolbar();
        setUpRecycler();
    }

    // Sets up Toolbar
    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Guest List");
        toolbar.setNavigationOnClickListener(v -> finish());        // Closes Activity
    }

    // Sets up Recycler
    private void setUpRecycler() {
        binding.guestListRV.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.guestListRV.setLayoutManager(layoutManager);

        ContactAdapter ca = new ContactAdapter(guestList, this);
        RecyclerView.Adapter<ContactAdapter.ViewHolder> mAdapter = ca;
        binding.guestListRV.setAdapter(mAdapter);
    }

    // onClick on RecyclerView Item
    @Override
    public void onContactClick(View v, int position) {
        myDialog = new Dialog(getApplicationContext());
        myDialog.setContentView(R.layout.dialog_contact);

        final User contact = guestList.get(position);
        openDialog(contact.getName(), contact.getEmail());
    }

    // Creates a contact dialog
    private void openDialog(String name, String email) {
        ContactDialog contactDialog = new ContactDialog(name, email);
        contactDialog.show(getSupportFragmentManager(), "contact dialog");
    }
}