package com.sjsu.partyplanner.Activities.Budget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Models.Payment;
import com.sjsu.partyplanner.R;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    private final ArrayList<Payment> mPayments;
    private final OnPaymentListener mOnPaymentListener;

    // Constructor
    public PaymentAdapter(ArrayList<Payment> mPayments, OnPaymentListener onPaymentListener) {
        this.mPayments = mPayments;
        this.mOnPaymentListener = onPaymentListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.payment_row_layout, parent, false);
        return new ViewHolder(view, mOnPaymentListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Payment payment = mPayments.get(position);

        holder.name.setText(payment.getName());
        holder.amount.setText("$" + payment.getAmount());

        // Status - Paid
        if (payment.getStatusBool()) {
            holder.status.setImageResource(R.drawable.subtask_done);
        } else {
            holder.status.setImageResource(R.drawable.subtask_empty);
        }
    }

    @Override
    public int getItemCount() {
        return mPayments.size();
    }

    // View Holder Class
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView amount;
        private final ImageView status;

        public ViewHolder(@NonNull View itemView, OnPaymentListener onPaymentListener) {
            super(itemView);
            name = itemView.findViewById(R.id.paymentNameLayout);
            amount = itemView.findViewById(R.id.paymentAmountLayout);
            status = itemView.findViewById(R.id.paymentStatusLayout);

            status.setOnClickListener(v -> onPaymentListener.OnPaymentClick(getAdapterPosition()));
        }
    }

    // Interface
    public interface OnPaymentListener {
        void OnPaymentClick(int position);
    }
}
