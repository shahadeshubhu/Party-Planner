package com.sjsu.partyplanner.Activities.Budget;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Models.Budget;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityBudgetBinding;

import java.util.ArrayList;

public class BudgetActivity extends AppCompatActivity implements BudgetAdapter.BudgetClick{

    private ActivityBudgetBinding binding;
    private Toolbar toolbar;
    private final ArrayList<Budget> budgetList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBudgetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Toolbar
        setUpToolbar();

        budgetList.add(new Budget("Example1 Budget", "Halloween Party", 300));
        budgetList.add(new Budget("Example2 Budget", "Christmas Party", 500));
        budgetList.add(new Budget("Example3 Budget", "Birthday Party", 400));

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
                toastMsg("Unimplemented");
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
        binding.budgetRecycler.setAdapter(new BudgetAdapter(budgetList, this));
    }

    @Override
    public void onBudgetClick(View v, int position) {
        Budget budget = budgetList.get(position);
        Intent intent = new Intent(getApplicationContext(), BudgetDetailActivity.class);
        Bundle extra =  new Bundle();
        extra.putParcelable(BudgetDetailActivity.BUDGET_KEY, budget);
        intent.putExtras(extra);
        startActivity(intent);
    }

    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

}