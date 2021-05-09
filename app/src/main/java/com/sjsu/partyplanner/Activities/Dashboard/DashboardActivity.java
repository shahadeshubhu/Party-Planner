package com.sjsu.partyplanner.Activities.Dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.sjsu.partyplanner.Activities.Budget.BudgetActivity;
import com.sjsu.partyplanner.Activities.Contacts.ContactsActivity;
import com.sjsu.partyplanner.Activities.Events.EventActivity;
import com.sjsu.partyplanner.Activities.Parties.PartyActivity;
import com.sjsu.partyplanner.Activities.Users.LoginActivity;
import com.sjsu.partyplanner.Controllers.UserController;
import com.sjsu.partyplanner.databinding.ActivityDashboardBinding;
import com.sjsu.partyplanner.R;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityDashboardBinding binding;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Navigation View
        setupNavigation();

        navigationView.setNavigationItemSelectedListener(this);
    }

    // onClick Methods
    public void onClick(View v) {
        if (v == binding.partiesButton)  { startActivity(new Intent(this, PartyActivity.class)); }
        else if (v == binding.eventsButton) { startActivity(new Intent(this, EventActivity.class)); }
        else if (v == binding.contactsButton) { startActivity(new Intent(this, ContactsActivity.class)); }
        else if (v == binding.budgetButton) { startActivity(new Intent(this, BudgetActivity.class)); }
    }

    // Sets up navigation view (side bar)
    private void setupNavigation() {
        drawerLayout = binding.drawerLayout;
        navigationView = binding.navView;
        navigationView.bringToFront();

        // Set up toolbar
        toolbar = (Toolbar) findViewById(R.id.dash_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        // Set up sidebar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }


    // Adds Icons to Toolbar (other than back button)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.db_invitations_menu, menu);
        return true;
    }

    // Handles Menu Items on Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dbMail:
                startActivity(new Intent(this, InvitationListActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                toastMsg("Profile");
                break;
            case R.id.nav_about:
                toastMsg("About");
                break;
            case R.id.nav_logout:
                UserController.getInstance().signOutUser();
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                break;
            case R.id.nav_parties:
                onClick(binding.partiesButton);
                break;
            case R.id.nav_events:
                onClick(binding.eventsButton);
                break;
            case R.id.nav_contacts:
                onClick(binding.contactsButton);
                break;
            case R.id.nav_budget:
                onClick(binding.budgetButton);
                break;
        }
        return true;
    }

    /**
     * Testing onClick methods
     * @param msg to toast
     */
    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

}