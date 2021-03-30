package com.sjsu.partyplanner.Activities.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sjsu.partyplanner.Models.Invitation;
import com.sjsu.partyplanner.R;

import org.w3c.dom.Text;

public class InvitationDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Invitation invite;

    private TextView pName;
    private TextView pType;
    private TextView pHost;
    private TextView pDescription;
    private TextView pLocation;
    private TextView pDateTime;

    private Button accept;
    private Button decline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_detail);

        setUpToolbar();
        invite = new Invitation();
        setWidgets();
    }

    public void onClick(View v) {
        invite.setHasSelected();        // A choice has been made
        if (v == findViewById(R.id.id_acceptButton)) {
            invite.setAccepted(true);
        }
        else if (v == findViewById(R.id.id_declineButton)) {
            invite.setAccepted(false);
        }
    }

    // Sets up Toolbar
    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Party Invitation");
        toolbar.setNavigationOnClickListener(v -> finish());        // Closes Activity
    }

    private void setWidgets() {

        // Set up buttons
        accept = findViewById(R.id.id_acceptButton);
        decline = findViewById(R.id.id_declineButton);

        // If you make a choice (accept/decline), you cannot change it [buttons disappear]
        if (invite.getHasSelected()) {
            accept.setVisibility(View.GONE);
            decline.setVisibility(View.GONE);
        }

        // Set up textViews
        pName = findViewById(R.id.id_pName);
        pType = findViewById(R.id.id_pType);
        pHost = findViewById(R.id.id_pHost);
        pDescription = findViewById(R.id.id_pDescriptionText);
        pLocation = findViewById(R.id.id_locationText);
        pDateTime = findViewById(R.id.id_dateTimeText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //TODO: set the textviews here!

            //pName.setText(...) example

        }


    }


}