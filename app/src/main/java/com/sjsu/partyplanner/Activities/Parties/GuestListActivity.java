package com.sjsu.partyplanner.Activities.Parties;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Activities.Contacts.ContactDialog;
import com.sjsu.partyplanner.Models.Guest;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityGuestListBinding;

import java.util.ArrayList;

public class GuestListActivity extends AppCompatActivity implements GuestAdapter.GuestClick {

    private ActivityGuestListBinding binding;
    private Toolbar toolbar;
    private ArrayList<Guest> guestList;
    private Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGuestListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

    // Sets up Guest List
    private void setUpGuestList() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            guestList = extras.getParcelableArrayList(PartyDetailActivity.GUEST_KEY);
        }

        // Checks Guest List
        if (guestList == null) {
            guestList = new ArrayList<>();
        }

        // Gets rid of extra text
        if(guestList.size() > 0) {
            binding.noGuestsTL.setText("");
        }
    }

    // Sets up Recycler
    private void setUpRecycler() {
        setUpGuestList();
        binding.guestListRV.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.guestListRV.setLayoutManager(layoutManager);
        binding.guestListRV.setAdapter(new GuestAdapter(guestList, this));
    }

    // onClick on RecyclerView Item
    @Override
    public void onGuestClick(View v, int position) {
        myDialog = new Dialog(getApplicationContext());
        myDialog.setContentView(R.layout.dialog_contact);

        final Guest guest = guestList.get(position);
        openDialog(guest.getName(), guest.getEmail());
    }

    // Creates a contact dialog
    private void openDialog(String name, String email) {
        ContactDialog contactDialog = new ContactDialog(name, email);
        contactDialog.show(getSupportFragmentManager(), "contact dialog");
    }

}