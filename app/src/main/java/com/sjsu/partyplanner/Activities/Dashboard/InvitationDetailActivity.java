package com.sjsu.partyplanner.Activities.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.sjsu.partyplanner.Models.Invitation;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityInvitationDetailBinding;

public class InvitationDetailActivity extends AppCompatActivity {

    public static final String INVITATION_KEY = "INVITATION";
    private ActivityInvitationDetailBinding binding;

    private Toolbar toolbar;
    private Invitation invite;

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

            // Sets Text
            /*
            binding.idPName.setText();
            binding.idPType.setText();
            binding.idPHost.setText();
            binding.idPDescriptionText.setText();
            binding.idLocationText.setText();
            binding.idDateTimeText.setText();

             */

            if(invite.getHasSelected()) {
                binding.idAcceptButton.setVisibility(View.INVISIBLE);
                binding.idDeclineButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    // Button Clicks
    public void onClick(View v) {
        invite.setHasSelected();        // A choice has been made
        if (v == findViewById(R.id.id_acceptButton)) {
            invite.setAccepted(true);
            invite.setHasSelected();
            v.setVisibility(View.INVISIBLE);
        }
        else if (v == findViewById(R.id.id_declineButton)) {
            invite.setAccepted(false);
            invite.setHasSelected();
            v.setVisibility(View.INVISIBLE);
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

}