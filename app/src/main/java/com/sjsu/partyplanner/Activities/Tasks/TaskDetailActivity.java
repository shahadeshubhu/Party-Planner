package com.sjsu.partyplanner.Activities.Tasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Models.Subtask;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityTaskDetailBinding;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TaskDetailActivity extends AppCompatActivity implements SubtaskDialog.SubtaskDialogListener {

    private ActivityTaskDetailBinding binding;
    private Toolbar toolbar;
    private String taskID;
    private ArrayList<Subtask> subtaskList;
    RecyclerView.Adapter<SubtaskAdapter.ViewHolder> mAdapter;

    // Set Text doesn't work with databinding
    private TextView nameTV;
    private TextView statusTV;
    private TextView categoryTV;
    private TextView noteTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTaskDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        subtaskList = new ArrayList<Subtask>();

        // Toolbar, TextViews, Button
        setUpToolbar();
        setTV();
        setUpButton();

    }

    // Sets up button
    public void setUpButton() {
        // Set up button image
        ImageView addSubtask = findViewById(R.id.tdAddSubtaskButton);
        addSubtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    // Creates the add subtask dialog
    public void openDialog() {
        SubtaskDialog subtaskDialog = new SubtaskDialog();
        subtaskDialog.show(getSupportFragmentManager(), "subtask dialog");
    }

    // SubtaskDialog.SubtaskDialogListener
    @Override
    public void applyTexts(String subtaskName) {
        subtaskList.add(new Subtask(subtaskName));
        setUpRecycler();        // updates the recycler
    }

    // Sets up the recycler
    public void setUpRecycler() {
        binding.tdRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.tdRecycler.setLayoutManager(layoutManager);
        mAdapter = new SubtaskAdapter(subtaskList);
        binding.tdRecycler.setAdapter(mAdapter);
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

        //TODO: Get Parcelable for Task
        // NOT WORKING. For some reason, it thinks I am getting a Party object, not a task object.
        /**
         * task = getIntent().getParcelableExtra("task");
         * subtaskList = task.getSubtasks();
         * setUpRecyclerView();
         */

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

                toastMsg("Edit Task");


                //startActivity(new Intent(this, EditTaskActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}