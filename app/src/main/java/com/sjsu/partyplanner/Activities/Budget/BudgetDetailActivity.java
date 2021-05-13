package com.sjsu.partyplanner.Activities.Budget;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Models.Budget;
import com.sjsu.partyplanner.Models.Payment;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityBudgetDetailBinding;

import java.util.ArrayList;

public class BudgetDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
    PaymentAdapter.OnPaymentListener, AddPaymentDialog.AddPaymentInterface {

    // Key
    public static final String BUDGET_KEY = "BUDGET";

    // Binding, Toolbar
    private ActivityBudgetDetailBinding binding;
    private Toolbar toolbar;

    // Budget, Payments
    private Budget budget;
    private ArrayList<Payment> payments;
    private RecyclerView.Adapter<PaymentAdapter.ViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBudgetDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Toolbar, TextViews
        setUpToolbar();
        setTextViews();
        setUpRecycler();
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
    }

    // Sets up the recycler
    public void setUpRecycler() {
        // Sets up budget payments
        payments = budget.getPayments();

        // Sets up recyclerview
        binding.bdRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.bdRecycler.setLayoutManager(layoutManager);
        mAdapter = new PaymentAdapter(payments, this);
        binding.bdRecycler.setAdapter(mAdapter);
    }

    // Add Payment to RecyclerView
    public void addPayment(View view) {
        AddPaymentDialog addPaymentDialog = new AddPaymentDialog();
        addPaymentDialog.show(getSupportFragmentManager(), "test custom dialog");
    }

    @Override
    public void OnPaymentClick(int position) {
        payments.get(position).changeStatus();
        mAdapter.notifyItemChanged(position);
        budget.setPayments(payments);
    }

    @Override
    public void getDialogInputs(String inputName, String inputNumber) {
        if (!inputName.isEmpty() && !inputNumber.isEmpty()) {
            payments.add(new Payment(inputName, Integer.parseInt(inputNumber)));
            mAdapter.notifyItemInserted(payments.size());
            budget.setPayments(payments);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Empty
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Empty
    }

}