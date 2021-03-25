package com.sjsu.partyplanner.Activities.Parties;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.sjsu.partyplanner.Models.Party;
import com.sjsu.partyplanner.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartiesFragment} factory method to
 * create an instance of this fragment.
 */
public class PartiesFragment extends Fragment {

    // RecyclerView
    private View v;
    private RecyclerView rView;
    private ArrayList<Party> parties;       // Get from activity


    // Layout Stuff
    private TextView noPartiesText;


    // Constructor
    public PartiesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parties = new ArrayList<Party>();

        // TESTING
        parties.add(new Party("O Pname", "PType", "PLocation", "PDescription", new Date(), "userID"));
        parties.add(new Party("O Pname2", "PType2", "PLocation", "PDescription", new Date(), "userID"));
        parties.add(new Party("O Pname3", "PType3", "PLocation", "PDescription", new Date(), "userID"));
        parties.add(new Party("O Pname", "PType", "PLocation", "PDescription", new Date(), "userID"));
        parties.add(new Party("O Pname2", "PType2", "PLocation", "PDescription", new Date(), "userID"));
        parties.add(new Party("O Pname3", "PType3", "PLocation", "PDescription", new Date(), "userID"));
        parties.add(new Party("O Pname", "PType", "PLocation", "PDescription", new Date(), "userID"));
        parties.add(new Party("O Pname2", "PType2", "PLocation", "PDescription", new Date(), "userID"));
        parties.add(new Party("O Pname3", "PType3", "PLocation", "PDescription", new Date(), "userID"));
        // AFTER TESTING


        //TODO: retrieve data NOT WORKING

        // Gets ArrayList
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            //parties.add(extras.getParcelableArrayList("key"));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_parties, container, false);


        // Set text to empty string if there are parties
        if(parties != null && parties.size() > 0) {
            noPartiesText = v.findViewById(R.id.noPP);
            noPartiesText.setText("");
        }

        // Recycler View
        rView = v.findViewById(R.id.partyRecyclerView);
        PartyAdapter pAdapter = new PartyAdapter(getContext(), parties);
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rView.setAdapter(pAdapter);

        return v;
    }
}