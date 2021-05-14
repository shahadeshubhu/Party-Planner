package com.sjsu.partyplanner.Activities.Parties;

import android.content.Intent;
import android.os.Bundle;
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
        usedIndex = -1;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            taskList = extras.getParcelableArrayList(PartyDetailActivity.TASK_KEY);
        }
        else { taskList = new ArrayList<>(); }

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
                startActivityForResult(new Intent(this, CreateTaskActivity.class), TEXT_REQUEST);
                return true; // added this
            case R.id.cpCheck:

                // update database
                Intent rIntent =  new Intent();
                Bundle extra =  new Bundle();
                extra.putParcelableArrayList(REPLY_KEY, taskList);
                rIntent.putExtras(extra);
                setResult(RESULT_OK, rIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Sets up Recycler
    private void setUpRecycler() {
        binding.tlDetailRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.tlDetailRecycler.setLayoutManager(layoutManager);
        binding.tlDetailRecycler.setAdapter(new TaskAdapter(taskList, this));

        if(taskList.size() > 0) { binding.noTasksTLDetail.setText(""); }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DETAIL_REQUEST) {
            if (resultCode == RESULT_OK) {
                Bundle responseExtras = data.getExtras();
                if (responseExtras != null) {
                    Task temp =responseExtras.getParcelable(TaskDetailActivity.EXTRA_REPLY);
                    taskList.set(usedIndex,temp);
                    setUpRecycler();
                }
            }
        }
        else if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Task temp = data.getParcelableExtra(CreateTaskActivity.TASK_KEY);
                if (temp != null) {
                    taskList.add(temp);
                    setUpRecycler();
                }
            }
        }
    }
}