package com.sjsu.partyplanner.Activities.Budget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.sjsu.partyplanner.Models.Budget;
import com.sjsu.partyplanner.Models.Payment;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityBudgetDetailBinding;

import java.util.ArrayList;

public class BudgetDetailActivity extends AppCompatActivity {

    private ActivityBudgetDetailBinding binding;
    private Toolbar toolbar;
    private Budget budget;
    private ArrayList<Payment> payments;

    public static final String BUDGET_KEY = "BUDGET";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBudgetDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Toolbar, TextViews
        setUpToolbar();
        setTextViews();
    }

    // Sets up Toolbar
    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Budget Details");
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    // Gets the Budget
    private void getBudget() {
        Bundle extras = getIntent().getExtras();
        budget = extras.getParcelable(BUDGET_KEY);
    }

    // Set up Text Views
    private void setTextViews() {
        getBudget();

        binding.bdName.setText(budget.getName());
        binding.bdParty.setText(budget.getParty());
        binding.bdAmount.setText("$" + String.valueOf(budget.getAmount()));

        // Remove Payment Stuff For Now
        binding.bdPayments.setVisibility(View.INVISIBLE);
        binding.bdAddPayment.setVisibility(View.INVISIBLE);
    }

    // Add Payment to RecyclerView
    public void addPayment(View view) {
        // No Time to implement
    }

    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }
}