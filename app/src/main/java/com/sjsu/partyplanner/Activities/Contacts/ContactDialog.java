package com.sjsu.partyplanner.Activities.Contacts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.sjsu.partyplanner.R;

public class ContactDialog extends AppCompatDialogFragment {

    private TextView contactName;
    private TextView contactEmail;
    private final String cName;
    private final String cEmail;

    public ContactDialog(String cName, String cEmail) {
        this.cName = cName;
        this.cEmail = cEmail;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_contact, null);

        builder.setView(view);
        contactName = view.findViewById(R.id.cdName);
        contactEmail = view.findViewById(R.id.cdEmail);

        contactName.setText(cName);
        contactEmail.setText(cEmail);

        return builder.create();
    }

}
