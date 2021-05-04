package com.sjsu.partyplanner.Activities.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sjsu.partyplanner.Controllers.PartyController;
import com.sjsu.partyplanner.Models.Invitation;
import com.sjsu.partyplanner.Models.Party;
import com.sjsu.partyplanner.R;
import java.util.ArrayList;
import com.sjsu.partyplanner.databinding.ActivityInvitationListBinding;


public class InvitationListActivity extends AppCompatActivity implements InvitationAdapter.InvitationClick {

    private ActivityInvitationListBinding binding;
    private Toolbar toolbar;
    private ArrayList<Invitation> invites;
    private PartyController partyController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvitationListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpToolbar();
        partyController = PartyController.getInstance();

        //TODO: Get input of Invitations List
        invites = new ArrayList<Invitation>();
        partyController.getUserInvitations(this);

        //Recycler
        setUpRecycler();

        // Gets rid of extra text
        if(invites.size() > 0) {
            binding.noInvitations.setText("");
        }

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

    /**
     * Sets up RecyclerView
     */
    private void setUpRecycler() {
        binding.inviteListRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.inviteListRecycler.setLayoutManager(layoutManager);

        InvitationAdapter ia = new InvitationAdapter(invites, this);
        RecyclerView.Adapter<InvitationAdapter.ViewHolder> mAdapter = ia;
        binding.inviteListRecycler.setAdapter(mAdapter);
    }

    public void handleGetInvitationSuccess(ArrayList<Invitation>invites){
        //TODO: Display this invitation list
        this.invites=invites;
        Log.d("Invitation", ""+invites.toString());
    }

    @Override
    public void onInviteClick(View v, int position) {

        Invitation invitation = invites.get(position);
        invitation.setHasRead();        // The invitation has been read (clicked on before)

        Intent intent = new Intent(getApplicationContext(), InvitationDetailActivity.class);

        //TODO: send invitation over (get party information in the invitation detail activity)
        // We get the invitation to be able to set whether or not it has been accepted/declined
        // and get the host name


        // DO HERE







    }



}