package com.sjsu.partyplanner.Activities.Parties;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.Models.Party;
import com.sjsu.partyplanner.R;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartiesFragment} factory method to
 * create an instance of this fragment.
 */
public class PartiesFragment extends Fragment implements PartyAdapter.PartyClick {


    public static final int PARY_REQUEST = 520;
    private ArrayList<Party> parties = new ArrayList<>();       // Get from activity

    // Constructor
    public PartiesFragment() {
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
        Intent intent = new Intent(getContext(), PartyDetailActivity.class);
        intent.putExtra("party", party);
        Log.d("Testing", "onPartyClick: starting party details");
        startActivityForResult(intent,PARY_REQUEST);
    }

    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(getContext(), msg, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PARY_REQUEST)
        {
            if(resultCode == RESULT_OK) {
                Log.d("Testing", "onActivityResult: recieved back a reply");
                PartyActivity activity = (PartyActivity) getActivity();

                Bundle bundleRecieved = data.getExtras();

                if (bundleRecieved != null)
                {
                    Party temp = bundleRecieved.getParcelable(PartyDetailActivity.NEW_PARTY);

                    if (temp != null)
                    {
                        Log.d("Testing", "onActivityResult: Recieved: Party name: " + temp.getName() + " id " + temp.getpId());
                        Log.d("Testing", "onActivityResult: calling method UpdateParty on Party Activity");
                        activity.updateParty(temp);

                    }


                }

            }
        }
    }
}