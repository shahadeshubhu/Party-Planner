package com.sjsu.partyplanner.Activities.Parties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sjsu.partyplanner.Models.Subtask;
import com.sjsu.partyplanner.Models.Task;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityCreateTaskListBinding;
import com.sjsu.partyplanner.databinding.ActivityTaskListBinding;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity implements TaskAdapter.TaskClick {

    public static final int TEXT_REQUEST = 500;
    public static final String TASKLIST_KEY = "TASKLISTd";
    private static final String TAG = "In Task List Activity";

    private ActivityTaskListBinding binding;
    private RecyclerView.Adapter<TaskAdapter.ViewHolder> mAdapter;
    private Toolbar toolbar;
    private ArrayList<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Toolbar, Recycler
        setUpToolbar();
        setUpRecycler();
    }

    // Sets up Toolbar
    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Task List");
        toolbar.setNavigationOnClickListener(v -> finish());        // Closes Activity
    }

    // Adds Icons to Toolbar (other than back button)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_create_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cpAdd:
                Log.d(TAG, "createTasks: Launching CreateTaskActivity");
                startActivityForResult(new Intent(this, CreateTaskActivity.class), TEXT_REQUEST);
                Log.d(TAG, "createTasks: Launched CreateTaskActivity");
            case R.id.cpCheck:
                Intent rIntent =  new Intent(this, CreateTaskActivity.class);
                Bundle extra =  new Bundle();
                extra.putParcelableArrayList(TASKLIST_KEY, taskList);
                rIntent.putExtras(extra);
                setResult(Activity.RESULT_OK, rIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Sets up Task List
    private void setUpTaskList() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            taskList = extras.getParcelableArrayList(PartyDetailActivity.TASK_KEY);

            ArrayList<Subtask> st = new ArrayList<Subtask>();
            st.add(new Subtask("NAMESS"));
            st.add(new Subtask("NAMESS22"));
            taskList.add(new Task("FRNEHC", "asdf", "dsaf", st));

        }

        // Checks Guest List
        if (taskList == null) {
            taskList = new ArrayList<>();
        }

        // Gets rid of extra text
        if(taskList.size() > 0) {
            binding.noTasksTLDetail.setText("");
        }
    }

    // Sets up Recycler
    private void setUpRecycler() {
        setUpTaskList();
        binding.tlDetailRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.tlDetailRecycler.setLayoutManager(layoutManager);
        binding.tlDetailRecycler.setAdapter(new TaskAdapter(taskList, this));
    }

    @Override
    public void onTaskClick(View v, int position) {
        Task task = taskList.get(position);
        Intent intent = new Intent(getApplicationContext(), TaskDetailActivity.class);
        Bundle extra =  new Bundle();
        extra.putParcelable(TaskDetailActivity.TASK_KEY, task);
        intent.putExtras(extra);
        startActivity(intent);
    }
}