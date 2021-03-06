package com.sjsu.partyplanner.Activities.Events;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Activities.Parties.PartyAdapter;
import com.sjsu.partyplanner.Models.Party;
import com.sjsu.partyplanner.R;

import java.util.ArrayList;


public class EventFragment extends Fragment implements PartyAdapter.PartyClick {

    private ArrayList<Party> parties = new ArrayList<>();       // Get from activity

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Gets ArrayList
        Bundle extras = this.getArguments();
        if (extras != null) {
            parties = extras.getParcelableArrayList("key");
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        parties = new ArrayList<>();
        Bundle extras = this.getArguments();
        if (extras != null) {
            parties = extras.getParcelableArrayList("key");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // RecyclerView
        View v = inflater.inflate(R.layout.fragment_parties, container, false);

        // Set text to empty string if there are parties
        if(parties != null && parties.size() > 0) {
            ((TextView) v.findViewById(R.id.noPP)).setText("");
        }

        // Recycler View
        RecyclerView rView = v.findViewById(R.id.partyRecyclerView);
        PartyAdapter pAdapter = new PartyAdapter(getContext(), parties, this);

        rView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rView.setAdapter(pAdapter);

        return v;
    }

    @Override
    public void onPartyClick(View v, int position) {
        Party party = parties.get(position);
        Intent intent = new Intent(getContext(), EventDetailActivity.class);
        intent.putExtra("party", party);
        startActivity(intent);
    }
}