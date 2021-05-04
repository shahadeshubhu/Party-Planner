package com.sjsu.partyplanner.Activities.Parties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.sjsu.partyplanner.Models.Subtask;
import com.sjsu.partyplanner.Models.Task;
import com.sjsu.partyplanner.databinding.ActivityCreateTaskBinding;
import com.sjsu.partyplanner.R;

import java.util.ArrayList;

public class CreateTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        SubtaskAdapter.OnSubtaskListener, AddSubtaskDialog.AddSubtaskInterface {
    public static final String TASK_KEY = "ABCDE";
    private Toolbar toolbar;
    private Spinner dropDown;
    private String taskCategory;
    private ActivityCreateTaskBinding binding;
    private ArrayList<Subtask> subtaskList = new ArrayList<Subtask>();
    private RecyclerView.Adapter<SubtaskAdapter.ViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Toolbar, Dropdown, Recycler
        setUpToolbar();
        setUpSpinner();
        setUpRecycler();
    }

    // Sets up Toolbar
    public void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Create Task");
        toolbar.setNavigationOnClickListener(v -> finish());        // Closes Activity
    }

    // Adds Icons to Toolbar (other than back button)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_menu, menu);
        return true;
    }

    // Handles Menu Items on Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cpCheck:

                Task task = new Task(binding.ctNameText.getText().toString(), taskCategory, binding.ctCategory.getText().toString(), subtaskList);
                Intent rIntent = new Intent(); // Changed from: new Intent(this, CreateTaskListActivity.class);
                rIntent.putExtra(TASK_KEY, task);
                setResult(Activity.RESULT_OK, rIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Sets up Spinner (Drop-down menu)
    public void setUpSpinner() {
        dropDown = (Spinner) findViewById(R.id.ctCategoryDropDown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.taskCategories, R.layout.spinner_text);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        dropDown.setAdapter(adapter);
        dropDown.setOnItemSelectedListener(this);
    }

    // Sets up recyclerview
    public void setUpRecycler() {
        binding.ctRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.ctRecycler.setLayoutManager(layoutManager);
        mAdapter = new SubtaskAdapter(subtaskList, this);
        binding.ctRecycler.setAdapter(mAdapter);// changed from: binding.ctRecycler.setAdapter(new SubtaskAdapter(subtaskList, this));
    }

    // Handles selected task category
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        taskCategory = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Empty
    }

    @Override
    public void OnSubtaskClick(int position) {
        subtaskList.get(position).changeStatus();
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void getDialogText(String inputText) {
        if (!inputText.isEmpty()) {
            subtaskList.add(new Subtask(inputText));
            mAdapter.notifyItemInserted(subtaskList.size() -1);
        }
    }

    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    public void addSubTask(View view) {
        AddSubtaskDialog addSubtaskDialog = new AddSubtaskDialog();
        addSubtaskDialog.show(getSupportFragmentManager(), "test custom dialog");
    }

}