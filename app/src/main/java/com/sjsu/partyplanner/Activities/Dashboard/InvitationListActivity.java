package com.sjsu.partyplanner.Activities.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Controllers.PartyController;
import com.sjsu.partyplanner.Models.Invitation;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityInvitationListBinding;

import java.util.ArrayList;

public class InvitationListActivity extends AppCompatActivity implements InvitationAdapter.InvitationClick {

    private ActivityInvitationListBinding binding;
    private ArrayList<Invitation> invites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvitationListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Toolbar, Invitations
        setUpToolbar();
        setUpInvitations();
    }

    // Sets up Toolbar
    public void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Invitations");
        toolbar.setNavigationOnClickListener(v -> finish());        // Closes Activity
    }

    // Sets up RecyclerView
    private void setUpRecycler() {
        binding.inviteListRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.inviteListRecycler.setLayoutManager(layoutManager);
        binding.inviteListRecycler.setAdapter(new InvitationAdapter(invites, this));

        // Gets rid of extra text
        if(invites.size() > 0) {
            binding.noInvitations.setText("");
        }
    }

    // Set up invitations
    private void setUpInvitations() {
        PartyController partyController = PartyController.getInstance();
        partyController.getUserInvitations(this);
    }
    
    // Handles Get Invitation Success
    public void handleGetInvitationSuccess(ArrayList<Invitation> invites){
        this.invites = invites;
        setUpRecycler();
    }

    @Override
    public void onInviteClick(View v, int position) {

        Invitation invitation = invites.get(position);
        invitation.setHasRead();        // The invitation has been read (clicked on before)

        Intent intent = new Intent(getApplicationContext(), InvitationDetailActivity.class);
        Bundle extra =  new Bundle();
        extra.putParcelable(InvitationDetailActivity.INVITATION_KEY, invitation);
        intent.putExtras(extra);
        startActivity(intent);
    }
}