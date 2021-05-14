package com.sjsu.partyplanner.Activities.Parties;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Models.Task;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityTaskListBinding;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity implements TaskAdapter.TaskClick {

    public static final int TEXT_REQUEST = 500;
    public static final int DETAIL_REQUEST = 510;
    public static final String TASKLIST_KEY = "TASKLISTd";
    public static final String REPLY_KEY = "com.sjsu.partyplanner.Activities.Parties"; // REPLY
    public static final String INDEX_KEY = "com.sjsu.partyplanner.Activities.Parties.indexTwo";
    private static final String TAG = "onTaskList";

    private ActivityTaskListBinding binding;
    private RecyclerView.Adapter<TaskAdapter.ViewHolder> mAdapter;
    private ArrayList<Task> taskList;
    private int usedIndex;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d("PartyUpdate", "onCreate: on task list");
        usedIndex = -1;

        Intent mIntent = getIntent();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            taskList = extras.getParcelableArrayList(PartyDetailActivity.TASK_KEY);

        }
        else
        {
            taskList = new ArrayList<Task>();

        }


        // Toolbar, Recycler
        setUpToolbar();
        setUpRecycler();
    }

    // Sets up Toolbar
    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
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
                return true; // added this
            case R.id.cpCheck:

                // update database

                Intent rIntent =  new Intent();//new Intent(this, CreateTaskActivity.class);
                Bundle extra =  new Bundle();
                extra.putParcelableArrayList(REPLY_KEY, taskList);
                rIntent.putExtras(extra);
                //rIntent.putParcelableArrayListExtra(REPLY_KEY, taskList);
                setResult(RESULT_OK, rIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Sets up Task List
    private void setUpTaskList() {
       /*
        Bundle extras = getIntent().getExtras();
        if (extras != null) { taskList = extras.getParcelableArrayList(PartyDetailActivity.TASK_KEY); }

        // Checks Guest List
        if (taskList == null) { taskList = new ArrayList<>(); }

        if (taskList == null) {
            taskList = new ArrayList<>();
        }
        */
        // Gets rid of extra text
        if(taskList.size() > 0) { binding.noTasksTLDetail.setText(""); }
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
        extra.putInt(INDEX_KEY, position);
        usedIndex = position;
        intent.putExtras(extra);
        startActivityForResult(intent, DETAIL_REQUEST);


        setUpRecycler();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: RequestCode: " + requestCode + " result code " + resultCode);
        if (requestCode == DETAIL_REQUEST)
        {
            if (resultCode == RESULT_OK)
            {

                Log.d(TAG, "onActivityResult: Processing task update");
                Log.d(TAG, "onActivityResult: received information back from TaskDetailActivity");
                Bundle responseExtras = data.getExtras();
                if (responseExtras != null)
                {
                    Task temp =responseExtras.getParcelable(TaskDetailActivity.EXTRA_REPLY);
                    //int usedIndex = responseExtras.getInt(TaskDetailActivity.INDEX_REPLY);
                    taskList.set(usedIndex,temp);
                    setUpRecycler();

                    Log.d(TAG, "onActivityResult: Task recieved = " + temp.getName());
                }
            }
        }
        else if (requestCode == TEXT_REQUEST)
        {
            if (resultCode == RESULT_OK)
            {
                Log.d(TAG, "onActivityResult: Processing task creation");
                Task temp = data.getParcelableExtra(CreateTaskActivity.TASK_KEY);

                if (temp != null)
                {
                    taskList.add(temp);
                    setUpRecycler();
                }
            }
        }
    }
}