package com.sjsu.partyplanner.Activities.Parties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.sjsu.partyplanner.Models.Subtask;
import com.sjsu.partyplanner.databinding.ActivityCreateTaskBinding;
import com.sjsu.partyplanner.R;

import java.util.ArrayList;

public class CreateTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar toolbar;
    private Spinner dropDown;
    private String taskCategory;

    private ActivityCreateTaskBinding binding;
    private ArrayList<Subtask> subtaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Toolbar, Dropdown
        setUpToolbar();
        setUpSpinner();

        //TODO: Subtask recyclerview is not scrolling

        //T----TESTING WITH SUBTASKS RECYCLERVIEW
        subtaskList = new ArrayList<Subtask>();
        subtaskList.add(new Subtask("111task"));
        subtaskList.add(new Subtask("222task"));
        subtaskList.add(new Subtask("333task"));
        subtaskList.add(new Subtask("444task"));

       setUpRecycler();
    }



    public void setUpRecycler() {

        binding.ctRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.ctRecycler.setLayoutManager(layoutManager);
        RecyclerView.Adapter<SubtaskAdapter.ViewHolder> mAdapter = new SubtaskAdapter(subtaskList);
        binding.ctRecycler.setAdapter(mAdapter);
    }

    // Sets up Spinner (Drop-down menu)
    public void setUpSpinner() {
        dropDown = (Spinner) findViewById(R.id.ctTaskCategoryDropDown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.taskCategories, R.layout.spinner_text);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        dropDown.setAdapter(adapter);
        dropDown.setOnItemSelectedListener(this);
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
        inflater.inflate(R.menu.create_party_menu, menu);
        return true;
    }

    // Handles Menu Items on Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cpCheck:

                toastMsg("created task");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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


    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }
}