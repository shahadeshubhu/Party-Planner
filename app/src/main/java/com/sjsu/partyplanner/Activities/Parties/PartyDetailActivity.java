package com.sjsu.partyplanner.Activities.Parties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sjsu.partyplanner.Models.Party;
import com.sjsu.partyplanner.R;

public class PartyDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private Party party;

    private TextView name;
    private TextView type;
    private TextView description;
    private TextView location;
    private TextView dateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_detail);

        // Toolbar, TextView
        setUpToolbar();
        setTV();
    }


    // onClick method for buttons
    public void onClick(View v) {
        // Task List Button OnClick
        if(v == findViewById(R.id.pdTaskButton)) {
            Intent intent = new Intent(this, CreateTaskListActivity.class);
            intent.putExtra("partyInfo", party);
            startActivity(intent);
        }
        // Guest List Button OnClick
        else if (v == findViewById(R.id.pdGuestButton)) {
            Intent intent = new Intent(this, GuestListActivity.class);

            // TODO: SEND GUEST LIST: an ArrayList<User>

            startActivity(intent);
        }
    }

    // Adds Icons to Toolbar (other than back button)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    // Handles Menu Items on Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.emEdit:

                toastMsg("Edit Button");

                Intent intent = new Intent(this, EditPartyActivity.class);
                intent.putExtra("partyInfo", party);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Sets up Toolbar
    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Party Details");
        toolbar.setNavigationOnClickListener(v -> finish());        // Closes Activity
    }

    // Sets up TextViews
    private void setTV() {
        name = findViewById(R.id.id_pName);
        type = findViewById(R.id.id_pType);
        description = findViewById(R.id.id_pDescriptionText);
        location = findViewById(R.id.id_locationText);
        dateTime = findViewById(R.id.id_dateTimeText);

        party = getIntent().getParcelableExtra("party");
        name.setText(party.getName());
        type.setText(party.getType());
        description.setText(party.getDescription());
        location.setText(party.getAddress());
        dateTime.setText((party.getDate()).toString());

    }

    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }
}