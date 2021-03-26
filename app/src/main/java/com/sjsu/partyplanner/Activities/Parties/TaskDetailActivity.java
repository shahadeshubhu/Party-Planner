package com.sjsu.partyplanner.Activities.Parties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityTaskDetailBinding;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class TaskDetailActivity extends AppCompatActivity {

    private ActivityTaskDetailBinding binding;
    private Toolbar toolbar;
    private String taskID;

    // Set Text doesn't work with databinding
    private TextView nameTV;
    private TextView statusTV;
    private TextView categoryTV;
    private TextView noteTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        //setContentView(binding.getRoot());

        binding = ActivityTaskDetailBinding.inflate(getLayoutInflater());

        // Toolbar, TextViews
        setUpToolbar();
        setTV();

        //TODO: user taskID to get the array of subtasks

        //TODO: EDIT TASK FUNCTION
    }

    // Sets up Toolbar
    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Task Details");
        toolbar.setNavigationOnClickListener(v -> finish());        // Closes Activity
    }

    // Sets up TextViews
    private void setTV() {
        nameTV = findViewById(R.id.tdNameText);
        statusTV = findViewById(R.id.tdStatus);
        categoryTV = findViewById(R.id.tdcategoryText);
        noteTV = findViewById(R.id.tdNoteText);

        // Sets the values from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            taskID = extras.getString("id");
            nameTV.setText(extras.getString("name"));
            statusTV.setText(extras.getString("status"));
            categoryTV.setText(extras.getString("category"));
            noteTV.setText(extras.getString("note"));

            // Set Text Color
            int completed = extras.getInt("completed");
            int total = extras.getInt("total");

            if (completed == total) { statusTV.setTextColor(Color.parseColor("#037d50")); } // Dark Green
            else if (completed == 0) { statusTV.setTextColor(Color.RED); }
            else { statusTV.setTextColor(Color.BLUE); }

        }
    }

}