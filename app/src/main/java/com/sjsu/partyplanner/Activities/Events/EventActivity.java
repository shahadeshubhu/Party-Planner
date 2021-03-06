package com.sjsu.partyplanner.Activities.Events;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sjsu.partyplanner.Controllers.PartyController;
import com.sjsu.partyplanner.Models.Invitation;
import com.sjsu.partyplanner.Models.Party;
import com.sjsu.partyplanner.R;

import java.util.ArrayList;
import java.util.Date;

public class EventActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private final ArrayList<Party> myParties = new ArrayList<>();
    private final ArrayList<Party> invitedParties = new ArrayList<>();
    private PartyController p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        getMyParties();
        setupToolbar();
    }

    // Sets up toolbar
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Events");
        toolbar.setNavigationOnClickListener(v -> finish());    // Goes back to Dashboard
    }

    // Gets Parties from database
    private void getMyParties() {
        p = PartyController.getInstance();
        p.getParties(this);
        p.getUserInvitations(this);
    }

    // Fetches my parties from database
    public void handleFetchParties(boolean isSuccessful, ArrayList<Party> p){
        Date now = new Date();

        if (isSuccessful){
            for(Party party : p){
                if(party != null && party.getDate().compareTo(now) >= 0) { myParties.add(party); }
            }
        }
        initializeTabLayout();
    }

    // Fetches Invitations from database
    public void handleGetInvitationSuccess(ArrayList<Invitation> invites){
        for (Invitation invite: invites) {
            p.getParty(invite.getPartyId(), this);
        }
    }

    // Fetches all invited parties from database
    public void handleGetPartySuccess(Party party) {
        invitedParties.add(party);
        initializeTabLayout();
    }

    // Initialize TabLayout
    private void initializeTabLayout() {
        TabLayout tabLayout = findViewById(R.id.partiesTabLayout);
        viewPager = findViewById(R.id.partiesViewPager);
        EventActivity.PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), myParties, invitedParties);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    /**
     * Inner Class for ViewPager Adapter
     */
    public static class PagerAdapter extends FragmentStatePagerAdapter {

        private final int numOfTabs;
        private final ArrayList<Party> myParties;
        private final ArrayList<Party> invitedParties;

        public PagerAdapter(FragmentManager fm, int numOfTabs, ArrayList<Party> myParties, ArrayList<Party> invitedParties) {
            super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.numOfTabs = numOfTabs;
            this.myParties = myParties;
            this.invitedParties = invitedParties;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    // Create Bundle of Parties
                    Bundle uBundle = new Bundle();
                    uBundle.putParcelableArrayList("key", myParties);

                    // Create Fragment with Arguments
                    EventFragment myFrag = new EventFragment();
                    myFrag.setArguments(uBundle);

                    return myFrag;
                case 1:
                    // Create Bundle of Parties
                    Bundle pBundle = new Bundle();
                    pBundle.putParcelableArrayList("key", invitedParties);

                    // Create Fragment with Arguments
                    EventFragment inviteFrag = new EventFragment();
                    inviteFrag.setArguments(pBundle);

                    return inviteFrag;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numOfTabs;
        }
    }
}