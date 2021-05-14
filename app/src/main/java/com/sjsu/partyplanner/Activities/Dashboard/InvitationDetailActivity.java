package com.sjsu.partyplanner.Activities.Dashboard;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.sjsu.partyplanner.Controllers.PartyController;
import com.sjsu.partyplanner.Models.Invitation;
import com.sjsu.partyplanner.Models.Party;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityInvitationDetailBinding;

public class InvitationDetailActivity extends AppCompatActivity {

    public static final String INVITATION_KEY = "INVITATION";
    public static final String PARTY_KEY = "PARTY";
    private ActivityInvitationDetailBinding binding;

    private Toolbar toolbar;
    Invitation invite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInvitationDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // TextViews, Toolbar
        setupTextViews();
        setUpToolbar();
    }

    // Set up Text in Details
    private void setupTextViews() {
        // Sets the values from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            invite = extras.getParcelable(INVITATION_KEY);

            PartyController partyController = PartyController.getInstance();
            partyController.getParty(invite.getPartyId(), this);

        }
    }

    // Gets the party for invitation from the database
    public void handleGetPartySuccess(Party party) {

        // Sets Text
        binding.idPHost.setText(invite.getHostName());
        binding.idPName.setText(party.getName());
        binding.idPType.setText(party.getType());
        binding.idPDescriptionText.setText(party.getDescription());
        binding.idLocationText.setText(party.getAddress());
        binding.idDateTimeText.setText(String.valueOf(party.getDate()));

        if(invite.getHasSelected()) {
            binding.idAcceptButton.setVisibility(View.INVISIBLE);
            binding.idDeclineButton.setVisibility(View.INVISIBLE);
        }
    }

    // Button Clicks
    public void onClick(View v) {
        invite.setHasSelected();        // A choice has been made
        if (v == findViewById(R.id.id_acceptButton)) {
            invite.setAccepted(true);
            invite.setHasSelected();
        }
        else if (v == findViewById(R.id.id_declineButton)) {
            invite.setAccepted(false);
            invite.setHasSelected();
        }
        binding.idAcceptButton.setVisibility(View.INVISIBLE);
        binding.idDeclineButton.setVisibility(View.INVISIBLE);

        // TODO: Repush invite to list and update list. repush list to firebase!
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

}