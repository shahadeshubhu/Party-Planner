package com.sjsu.partyplanner.Activities.Parties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Models.Subtask;
import com.sjsu.partyplanner.Models.Task;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityTaskDetailBinding;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TaskDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        SubtaskAdapter.OnSubtaskListener, AddSubtaskDialog.AddSubtaskInterface {

    public static final String TASK_KEY = "TASK";
    private Task task;

    private ActivityTaskDetailBinding binding;
    private Toolbar toolbar;
    private ArrayList<Subtask> subtaskList;
    private RecyclerView.Adapter<SubtaskAdapter.ViewHolder> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTaskDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Toolbar, TextViews, Recycler
        setUpToolbar();
        setTV();
        setUpRecycler();

    }

    // Sets up Toolbar
    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Task Details");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
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
        if (item.getItemId() == R.id.emEdit) {
            toastMsg("Edit Task");
            //startActivity(new Intent(this, EditTaskActivity.class));
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    // Sets up TextViews
    private void setTV() {
        // Sets the values from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            task = extras.getParcelable(TASK_KEY);

            // Sets Text
            binding.tdNameText.setText(task.getName());
            binding.tdStatus.setText(task.getStatus());
            binding.tdcategoryText.setText(task.getTaskCategory());
            binding.tdNoteText.setText(task.getNote());

            updateSubTasks();
        }
    }

    // Sets up the recycler
    public void setUpRecycler() {
        binding.tdRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.tdRecycler.setLayoutManager(layoutManager);
        mAdapter = new SubtaskAdapter(subtaskList, this);
        binding.tdRecycler.setAdapter(mAdapter);
    }

    // Handles selected task category
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Empty
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Empty
    }

    @Override
    public void OnSubtaskClick(int position) {
        subtaskList.get(position).changeStatus();
        mAdapter.notifyItemChanged(position);

        // Dynamically Updating When Checking For completion
        updateSubTasks();
    }

    @Override
    public void getDialogText(String inputText) {
        if (!inputText.isEmpty()) {
            subtaskList.add(new Subtask(inputText));
            mAdapter.notifyItemInserted(subtaskList.size());
            updateSubTasks();
        }
    }

    public void addSubTask(View view) {
        AddSubtaskDialog addSubtaskDialog = new AddSubtaskDialog();
        addSubtaskDialog.show(getSupportFragmentManager(), "test custom dialog");
    }

    // Dynamically updates Task
    private void updateSubTasks() {
        task.setSubtasks(subtaskList);

        // Sets Text Color
        binding.tdStatus.setText(task.getStatus());

        int completed = task.getCompletedSubtasks();
        int total = task.getTotalSubtasks();
        if (completed == total) { binding.tdStatus.setTextColor(Color.parseColor("#037d50")); } // Dark Green
        else if (completed == 0) { binding.tdStatus.setTextColor(Color.RED); }
        else { binding.tdStatus.setTextColor(Color.BLUE); }
    }

    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }
}