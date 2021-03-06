package com.sjsu.partyplanner.Activities.Parties;

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

public class AddSubtaskDialog  extends AppCompatDialogFragment {

    private AddSubtaskInterface addSubtaskInterface;
    private EditText subTaskName;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Create Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_subtask, null);

        // Get Input
        subTaskName = view.findViewById(R.id.subTaskEditText);

        // Set Dialog Buttons
        builder.setView(view).setNegativeButton("Cancel", (dialog, which) -> {})
            .setPositiveButton("Ok", (dialog, which) -> {
                String editText = subTaskName.getText().toString();
                addSubtaskInterface.getDialogText(editText);
            });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        addSubtaskInterface = (AddSubtaskInterface) context;
    }

    public interface AddSubtaskInterface {
        void getDialogText(String inputText);
    }
}
