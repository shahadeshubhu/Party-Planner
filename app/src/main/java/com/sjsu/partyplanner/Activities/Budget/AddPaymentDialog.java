package com.sjsu.partyplanner.Activities.Budget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.sjsu.partyplanner.R;

public class AddPaymentDialog extends AppCompatDialogFragment {

    private AddPaymentDialog.AddPaymentInterface addPaymentInterface;
    private EditText paymentName;
    private EditText paymentAmount;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Create Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_payment, null);

        // Get Input
        paymentName = view.findViewById(R.id.paymentNameET);
        paymentAmount = view.findViewById(R.id.paymentAmountET);

        // Set Dialog Buttons
        builder.setView(view).setNegativeButton("Cancel", (dialog, which) -> {})
                .setPositiveButton("Ok", (dialog, which) -> {
                    addPaymentInterface.getDialogInputs(paymentName.getText().toString(), paymentAmount.getText().toString()) ;
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        addPaymentInterface = (AddPaymentDialog.AddPaymentInterface) context;
    }

    public interface AddPaymentInterface {
        void getDialogInputs(String inputName, String inputNumber);
    }
}