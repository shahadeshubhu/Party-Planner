package com.sjsu.partyplanner.Activities.Parties;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.sjsu.partyplanner.R;

public class SubtaskDialog extends AppCompatDialogFragment {
    private EditText subtaskName;
    private SubtaskDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_subtask, null);

        builder.setView(view)
                .setTitle("Create Subtask")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String stName = subtaskName.getText().toString();
                        listener.applyTexts(stName);
                    }
                });
        subtaskName = view.findViewById(R.id.addSubtaskDialog);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (SubtaskDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SubtaskDialogListener");
        }
    }

    public interface SubtaskDialogListener {
        void applyTexts(String subtaskName);
    }



}
