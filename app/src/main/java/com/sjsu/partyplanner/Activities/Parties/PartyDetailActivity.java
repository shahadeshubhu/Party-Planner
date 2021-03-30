package com.sjsu.partyplanner.Activities.Parties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sjsu.partyplanner.R;

import org.w3c.dom.Text;

public class PartyDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private String partyID;
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
        //TODO: implement buttons

        // Task List Button OnClick
        if(v == findViewById(R.id.pdTaskButton)) {

        }
        // Guest List Button OnClick
        else if (v == findViewById(R.id.pdGuestButton)) {

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
        name = findViewById(R.id.pdNameText);
        type = findViewById(R.id.pdTypeText);
        description = findViewById(R.id.pdDescriptionText);
        location = findViewById(R.id.pdLocationText);
        dateTime = findViewById(R.id.pdDateTimeText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            partyID = extras.getString("id");
            name.setText(extras.getString("name"));
            type.setText(extras.getString("type"));
            location.setText(extras.getString("location"));
            dateTime.setText(extras.getString("datetime"));
            description.setText(extras.getString("description"));

        }
    }
}