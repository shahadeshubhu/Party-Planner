package com.sjsu.partyplanner.Activities.Dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.sjsu.partyplanner.Activities.Parties.PartyActivity;
import com.sjsu.partyplanner.Activities.Users.LoginActivity;
import com.sjsu.partyplanner.Models.Party;
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





// TODO Dashboard Buttons


  /**
   * onClick method for 'parties'
   * Goes to the 'parties' page
   */
  public void partyClick(View view) {
      startActivity(new Intent(this, PartyActivity.class));
  }

  /**
   * onClick method for 'events'
   * Goes to the 'events' page
   */
  public void eventClick(View view) {
    toastMsg("Event Click");
  }

  /**
   * onClick method for 'contacts'
   * Goes to the 'contacts' page
   */
  public void contactClick(View view) {
    toastMsg("Contact Click");
  }

  /**
   * onClick method for 'budget'
   * Goes to the 'budget' page
   */
  public void budgetClick(View view) {
    toastMsg("Budget Click");
  }

  /**
   * onClick method for 'user icon'
   * Opens Side Bar
   */
  public void userClick(View view) {
    toastMsg("User Icon Click");
  }

    /**
     * Testing onClick methods
     * @param msg to toast
     */
    public void toastMsg(String msg) {
      Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
      toast.show();
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

        //TODO: Set up Side bar options for profile and about
        //TODO: Set displayname for sidebar

        switch (item.getItemId()) {
            case R.id.nav_profile:

                break;
            case R.id.nav_about:

                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.nav_parties:
                partyClick(findViewById(R.id.nav_parties));
                break;
            case R.id.nav_events:
                eventClick(findViewById(R.id.nav_events));
                break;
            case R.id.nav_contacts:
                contactClick(findViewById(R.id.nav_contacts));
                break;
            case R.id.nav_budget:
                budgetClick(findViewById(R.id.nav_budget));
                break;
        }

        return true;
    }

}