package com.sjsu.partyplanner.Activities.Events;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    // RecyclerView
    private View v;
    private RecyclerView rView;
    private PartyAdapter pAdapter;
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
            Log.d("parties in Frag", ""+ parties);
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        parties = new ArrayList<>();
        Bundle extras = this.getArguments();
        if (extras != null) {
            parties = extras.getParcelableArrayList("key");
            Log.d("parties in Frag", ""+ parties);
        }

        //pAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_parties, container, false);

        // Set text to empty string if there are parties
        if(parties != null && parties.size() > 0) {
            ((TextView) v.findViewById(R.id.noPP)).setText("");
        }

        // Recycler View
        rView = v.findViewById(R.id.partyRecyclerView);
        pAdapter = new PartyAdapter(getContext(), parties, this);

        rView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rView.setAdapter(pAdapter);

        return v;
    }

    /**
     * Party Item Click method for recycler view
     * @param v
     * @param position
     */
    @Override
    public void onPartyClick(View v, int position) {
        Party party = parties.get(position);
        Intent intent = new Intent(getContext(), EventDetailActivity.class);
        intent.putExtra("party", party);
        startActivity(intent);
    }
}