package com.sjsu.partyplanner.Activities.Tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Models.Subtask;
import com.sjsu.partyplanner.Models.Task;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityTaskListBinding;
import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity implements TaskAdapter.TaskClick {

    private  ActivityTaskListBinding binding;
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
        ArrayList<Subtask> st = new ArrayList<Subtask>();
        st.add(new Subtask("stname"));
        taskList.add(new Task("name", "categorty", "note", st));

        //Recycler
        setUpRecycler();

        // Gets rid of extra text
        if(taskList.size() > 0) {
            binding.noTasksTL.setText("");
        }

    }

    /**
     * Sets up RecyclerView
     */
    private void setUpRecycler() {
        binding.tasklistRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.tasklistRecycler.setLayoutManager(layoutManager);

        TaskAdapter ta = new TaskAdapter(taskList, this);
        RecyclerView.Adapter<TaskAdapter.ViewHolder> mAdapter = ta;
        binding.tasklistRecycler.setAdapter(mAdapter);
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
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    // Handles Menu Items on Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clAdd:
                startActivity(new Intent(this, CreateTaskActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Task Item Click method for recycler view
     * @param v
     * @param position
     */
    @Override
    public void onTaskClick(View v, int position) {

        TextView status = findViewById(R.id.tStatusLayout);

        Task task = taskList.get(position);
        Intent intent = new Intent(getApplicationContext(), TaskDetailActivity.class);

        // TODO: For some reason, it thinks I am sending a party, not a task object
        /*
        intent.putExtra("task", task);
         */
        intent.putExtra("id", task.getTaskID());
        intent.putExtra("name", task.getName());
        intent.putExtra("category", task.getTaskCategory());
        intent.putExtra("note", task.getNote());
        intent.putExtra("status", task.getStatus());
        intent.putExtra("statusColor", status.getCurrentTextColor());
        intent.putExtra("subtasks", task.getSubtasks());

        intent.putExtra("completed", task.getCompletedSubtasks());
        intent.putExtra("total", task.getTotalSubtasks());

        startActivity(intent);
    }


    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }
}