package com.sjsu.partyplanner.Activities.Parties;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Models.Subtask;
import com.sjsu.partyplanner.Models.Task;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityCreateTaskListBinding;
import java.util.ArrayList;

public class CreateTaskListActivity extends AppCompatActivity implements TaskAdapter.TaskClick {

    public static final int TEXT_REQUEST = 500;
    public static final String TASKLIST_KEY = "TASKLISTd";
    private static final String TAG = "In Task List Activity";
    private TextView noTasksAvailable;
    private  ActivityCreateTaskListBinding binding;
    private RecyclerView.Adapter<TaskAdapter.ViewHolder> mAdapter;
    private Toolbar toolbar;
    private ArrayList<Task> taskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTaskListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Toolbar
        setUpToolbar();

        //TODO: get the input data of task list!
        taskList = new ArrayList<Task>();
        ArrayList<Subtask> st = new ArrayList<Subtask>();
        st.add(new Subtask("NAMESS"));
        st.add(new Subtask("NAMESS22"));

        taskList.add(new Task("name", "asdf", "dsaf", st));

        noTasksAvailable = binding.noTasksTL;

        //Recycler
        setUpRecycler();

        // Gets rid of extra text
        if(taskList.size() > 0) {
            noTasksAvailable.setText("");
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // making sure it receives the correct intent reply
        if (requestCode == TEXT_REQUEST)
        {
            // making sure reply is good
            if (resultCode == RESULT_OK)
            {
                Log.d(TAG, "onActivityResult: Received Intent reply");

                taskList.add(data.getParcelableExtra(CreateTaskActivity.TASK_KEY));
                setUpRecycler();
                //mAdapter.notifyItemInserted(taskList.size() -1);
                noTasksAvailable.setVisibility(View.INVISIBLE);
                Log.d("ActivityResult", "onActivityResult: Successfully loaded task named: ");

            }
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
        mAdapter = ta;
        binding.tasklistRecycler.setAdapter(mAdapter);
    }

    /**
     * Task Item Click method for recycler view
     * @param v
     * @param position
     */
    @Override
    public void onTaskClick(View v, int position) {
        Task task = taskList.get(position);
        Intent intent = new Intent(getApplicationContext(), TaskDetailActivity.class);
        Bundle extra =  new Bundle();
        extra.putParcelable(TaskDetailActivity.TASK_KEY, task);
        intent.putExtras(extra);
        startActivity(intent);
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

}