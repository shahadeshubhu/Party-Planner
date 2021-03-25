package com.sjsu.partyplanner.Activities.Parties;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Models.Subtask;
import com.sjsu.partyplanner.Models.Task;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityTaskListBinding;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    private ActivityTaskListBinding binding;
    private Toolbar toolbar;
    private ArrayList<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Toolbar
        setUpToolbar();

        //TODO: get the input data of task list!
        taskList = new ArrayList<Task>();

        // ------Testing Task List

        // Not Started Example
        ArrayList<Subtask> st = new ArrayList<Subtask>();
        st.add(new Subtask("Suntasdf", "taskID", "taskID"));

        // Pending Example
        ArrayList<Subtask> subtasks = new ArrayList<Subtask>();
        Subtask s1 = new Subtask("Suntasdf", "name", "taskID");
        s1.changeStatus();      // Completed subtask
        subtasks.add(s1);
        subtasks.add(new Subtask("Suntaasdfsdf", "name", "taskID"));

        // Complete Example
        ArrayList<Subtask> subtask2 = new ArrayList<Subtask>();


        taskList.add(new Task("TaskName", "category", st, "partyID"));
        taskList.add(new Task("dsfaf", "sda", subtasks, "dsdsd"));
        taskList.add(new Task("nane", "gorew", subtask2, "idparty"));

        // TESTING TASK LIST

        //---------------ENd of To Do

        //Recycler
        setUpRecycler();


        // Gets rid of extra text
        if(taskList.size() > 0) {
            binding.noTasksTL.setText("");
        }

    }


    /**
     * Starts 'Create Task' activity
     * @param v
     */
    public void createTasks (View v) {
        startActivity(new Intent(this, CreateTaskActivity.class));
    }

    // Sets up Toolbar
    public void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Task List");
        toolbar.setNavigationOnClickListener(v -> finish());        // Closes Activity
    }

    /**
     * Sets up RecyclerView
     */
    public void setUpRecycler() {
        binding.tasklistRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.tasklistRecycler.setLayoutManager(layoutManager);
        RecyclerView.Adapter<TaskAdapter.ViewHolder> mAdapter = new TaskAdapter(taskList);
        binding.tasklistRecycler.setAdapter(mAdapter);
    }

}