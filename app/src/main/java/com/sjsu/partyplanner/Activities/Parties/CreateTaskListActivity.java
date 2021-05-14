package com.sjsu.partyplanner.Activities.Parties;

import android.app.Activity;
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
import com.sjsu.partyplanner.databinding.ActivityCreateTaskListBinding;

import java.util.ArrayList;

public class CreateTaskListActivity extends AppCompatActivity implements TaskAdapter.TaskClick {

    public static final int TEXT_REQUEST = 501;
    public static final int DETAIL_REQUEST = 502;
    public static final String TASKLIST_KEY = "TASKLISTd";
    public static final String INDEX_KEY = "com.sjsu.partyplanner.Activities.Parties.index";
    private  ActivityCreateTaskListBinding binding;
    private ArrayList<Task> taskList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTaskListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Update Task List
        Intent mIntent = getIntent();
        taskList = mIntent.getParcelableArrayListExtra(CreatePartyActivity.CREATE_TASK_KEY);

        // Toolbar, Recycler
        setUpToolbar();
        setUpRecycler();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // making sure it receives the correct intent reply
        if (requestCode == TEXT_REQUEST) {
            // making sure reply is good
            if (resultCode == RESULT_OK) {
                taskList.add(data.getParcelableExtra(CreateTaskActivity.TASK_KEY));
                setUpRecycler();
                binding.noTasksTL.setVisibility(View.INVISIBLE);
            }
        }
        else if ( requestCode == DETAIL_REQUEST) {
            if (resultCode == RESULT_OK)  {
                Bundle responseExtras = data.getExtras();
                if (responseExtras != null) {
                    Task temp =responseExtras.getParcelable(TaskDetailActivity.EXTRA_REPLY);
                    int usedIndex = responseExtras.getInt(TaskDetailActivity.INDEX_REPLY);
                    taskList.set(usedIndex,temp);
                    setUpRecycler();
                }


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

        binding.tasklistRecycler.setAdapter(new TaskAdapter(taskList, this));

        // Gets rid of extra text
        if(taskList.size() > 0) {
            binding.noTasksTL.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onTaskClick(View v, int position) {
        Task task = taskList.get(position);
        Intent intent = new Intent(getApplicationContext(), TaskDetailActivity.class);
        Bundle extra =  new Bundle();
        extra.putParcelable(TaskDetailActivity.TASK_KEY, task);
        extra.putInt(INDEX_KEY, position);
        intent.putExtras(extra);
        startActivityForResult(intent, DETAIL_REQUEST);
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
                return true;
            case R.id.cpCheck:
                Intent rIntent =  new Intent(this, CreatePartyActivity.class);
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