package com.sjsu.partyplanner.Activities.Events;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.sjsu.partyplanner.Models.Party;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityEventDetailBinding;

public class EventDetailActivity extends AppCompatActivity {

    private ActivityEventDetailBinding binding;
    private Toolbar toolbar;
    private Party party;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEventDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpToolbar();
        setTV();
    }

    // Sets up Toolbar
    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Event Details");
        toolbar.setNavigationOnClickListener(v -> finish());        // Closes Activity
    }

    // Sets up TextViews
    private void setTV() {

        party = getIntent().getParcelableExtra("party");

        binding.edpName.setText(party.getName());
        binding.edpType.setText(party.getType());
        binding.edpDescriptionText.setText(party.getDescription());
        binding.edLocationText.setText(party.getAddress());
        binding.edDateTimeText.setText(String.valueOf(party.getDate()));

    }
}