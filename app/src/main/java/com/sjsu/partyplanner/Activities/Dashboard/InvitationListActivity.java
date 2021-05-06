package com.sjsu.partyplanner.Activities.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sjsu.partyplanner.Activities.Parties.TaskDetailActivity;
import com.sjsu.partyplanner.Controllers.PartyController;
import com.sjsu.partyplanner.Models.Invitation;
import com.sjsu.partyplanner.Models.Party;
import com.sjsu.partyplanner.Models.Task;
import com.sjsu.partyplanner.R;
import java.util.ArrayList;
import com.sjsu.partyplanner.databinding.ActivityInvitationListBinding;

public class InvitationListActivity extends AppCompatActivity implements InvitationAdapter.InvitationClick {

    private ActivityInvitationListBinding binding;
    private Toolbar toolbar;
    private PartyController partyController;
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
        toolbar = findViewById(R.id.toolbar);
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

        InvitationAdapter ia = new InvitationAdapter(invites, this);
        RecyclerView.Adapter<InvitationAdapter.ViewHolder> mAdapter = ia;
        binding.inviteListRecycler.setAdapter(mAdapter);
    }

    // Set up invitations
    private void setUpInvitations() {
        partyController = PartyController.getInstance();
        invites = new ArrayList<Invitation>();
        partyController.getUserInvitations(this);
    }
    
    // Handles Get Invitation Success
    public void handleGetInvitationSuccess(ArrayList<Invitation> invites){
        this.invites = invites;
        Log.d("Invitation", ""+invites.toString());

        // Setup RecyclerView When you get all invitations
        setUpRecycler();
        // Gets rid of extra text
        if(invites.size() > 0) {
            binding.noInvitations.setText("");
        }
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