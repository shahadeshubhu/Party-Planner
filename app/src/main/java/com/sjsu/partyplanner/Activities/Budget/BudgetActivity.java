package com.sjsu.partyplanner.Activities.Budget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.sjsu.partyplanner.Activities.Contacts.ContactAdapter;
import com.sjsu.partyplanner.Models.Budget;
import com.sjsu.partyplanner.Models.User;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityBudgetBinding;

import java.util.ArrayList;

public class BudgetActivity extends AppCompatActivity implements BudgetAdapter.BudgetClick{

    private ActivityBudgetBinding binding;
    private Toolbar toolbar;
    private ArrayList<Budget> budgetList = new ArrayList<Budget>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBudgetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Toolbar
        setUpToolbar();

        budgetList.add(new Budget("Food Budget", "Halloween Party", 300));

        setUpRecycler();

        // Gets rid of extra text
        if(budgetList.size() > 0) {
            binding.noBudgets.setText("");
        }
    }

    // Sets up Toolbar
    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Budget List");
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
                //TODO: ADD NEW BUDGET


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Sets up Recycler
    private void setUpRecycler() {
        binding.budgetRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.budgetRecycler.setLayoutManager(layoutManager);

        BudgetAdapter ba = new BudgetAdapter(budgetList, this);
        RecyclerView.Adapter<BudgetAdapter.ViewHolder> mAdapter = ba;
        binding.budgetRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onBudgetClick(View v, int position) {

    }
}