package com.sjsu.partyplanner.Activities.Budget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Models.Budget;
import com.sjsu.partyplanner.R;

import java.util.ArrayList;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {

    private final ArrayList<Budget> budgets;
    private final BudgetAdapter.BudgetClick listener;

    // Constructor
    public BudgetAdapter(ArrayList<Budget> budgets, BudgetAdapter.BudgetClick listener) {
        this.budgets = budgets;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BudgetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.budget_layout, parent, false);
        return new BudgetAdapter.ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetAdapter.ViewHolder holder, int position) {

        final Budget budget = budgets.get(position);
        float amount = budget.getAmount();
        String amountString = "$" + amount;

        holder.bName.setText(budget.getName());
        holder.bParty.setText(budget.getParty());
        holder.blAmount.setText(amountString);
    }

    @Override
    public int getItemCount() {
        return budgets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView bName;
        private final TextView bParty;
        private final TextView blAmount;
        private final BudgetAdapter.BudgetClick rvClick;

        public ViewHolder(@NonNull final View itemView, BudgetAdapter.BudgetClick rvClick) {
            super(itemView);
            bName = itemView.findViewById(R.id.bName);
            bParty = itemView.findViewById(R.id.bParty);
            blAmount = itemView.findViewById(R.id.blAmount);

            // OnClickListener
            this.rvClick = rvClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            rvClick.onBudgetClick(v, getAdapterPosition());
        }
    }

    // Interface onClick Method
    public interface BudgetClick {
        void onBudgetClick(View v, int position);
    }

}
