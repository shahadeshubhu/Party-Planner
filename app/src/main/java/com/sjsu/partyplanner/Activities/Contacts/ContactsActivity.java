package com.sjsu.partyplanner.Activities.Contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sjsu.partyplanner.Activities.Tasks.SubtaskDialog;
import com.sjsu.partyplanner.Models.User;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityContactsListBinding;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity implements ContactAdapter.ContactClick {

    private ActivityContactsListBinding binding;
    private Toolbar toolbar;
    private ArrayList<User> contacts = new ArrayList<User>();
    private Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Toolbar
        setUpToolbar();

        contacts.add(new User("firstName", "lastName", "email"));

        setUpRecycler();

        // Gets rid of extra text
        if(contacts.size() > 0) {
            binding.noContactsCL.setText("");
        }
    }

    // Sets up Toolbar
    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Contacts List");
        toolbar.setNavigationOnClickListener(v -> finish());        // Closes Activity
    }

    // Adds Icons to Toolbar (other than back button)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    // Handles Menu Items on Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clAdd:
                //TODO: ADD NEW CONTACT


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Sets up Recycler
    private void setUpRecycler() {
        binding.contactRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.contactRecycler.setLayoutManager(layoutManager);

        ContactAdapter ca = new ContactAdapter(contacts, this);
        RecyclerView.Adapter<ContactAdapter.ViewHolder> mAdapter = ca;
        binding.contactRecycler.setAdapter(mAdapter);
    }

    // onClick on RecyclerView Item
    @Override
    public void onContactClick(View v, int position) {
        myDialog = new Dialog(getApplicationContext());
        myDialog.setContentView(R.layout.dialog_contact);

        final User contact = contacts.get(position);
        openDialog(contact.getName(), contact.getEmail());

    }

    // Creates a contact dialog
    private void openDialog(String name, String email) {
        ContactDialog contactDialog = new ContactDialog(name, email);
        contactDialog.show(getSupportFragmentManager(), "contact dialog");
    }
}