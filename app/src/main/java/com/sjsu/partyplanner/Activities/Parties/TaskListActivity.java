package com.sjsu.partyplanner.Activities.Parties;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
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
    public static final int TEXT_REQUEST = 500;
    public static final String TASKLIST_KEY = "TASKLISTd";
    private static final String TAG = "In Task List Activity";
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

        // Not Started Example
        ArrayList<Subtask> st = new ArrayList<Subtask>();
        st.add(new Subtask("Suntasdf"));

        // Pending Example
        ArrayList<Subtask> subtasks = new ArrayList<Subtask>();
        Subtask s1 = new Subtask("Suntasdf");
        s1.changeStatus();      // Completed subtask
        subtasks.add(s1);
        subtasks.add(new Subtask("Suntaasdfsdf"));

        // Complete Example
        ArrayList<Subtask> subtask2 = new ArrayList<Subtask>();

//
//        taskList.add(new Task("ID", "TaskName", "category", "note", st, "partyID"));
//        taskList.add(new Task("IPD", "dsfaf", "sda","note", subtasks, "dsdsd"));
//        taskList.add(new Task("TASKID", "nane", "gorew","note", subtask2, "idparty"));

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

    /**
     * Starts 'Create Task' activity
     * @param v
     */
    public void createTasks (View v)
    {
        Log.d(TAG, "createTasks: Launching CreateTaskActivity");
        startActivityForResult(new Intent(this, CreateTaskActivity.class), TEXT_REQUEST);
        Log.d(TAG, "createTasks: Launched CreateTaskActivity");

        /*
        startActivity(new Intent(this, CreateTaskActivity.class));

         */
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
                Log.d("ActivityResult", "onActivityResult: Successfully loaded task named: ");

            }
        }

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
        intent.putExtra("id", task.getTaskID());
        intent.putExtra("name", task.getName());
        intent.putExtra("category", task.getTaskCategory());
        intent.putExtra("note", task.getNote());
        intent.putExtra("status", task.getStatus());
        intent.putExtra("statusColor", status.getCurrentTextColor());

        intent.putExtra("completed", task.getCompletedSubtasks());
        intent.putExtra("total", task.getTotalSubtasks());

        startActivity(intent);
    }


    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    // Adds Icons to Toolbar (other than back button)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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