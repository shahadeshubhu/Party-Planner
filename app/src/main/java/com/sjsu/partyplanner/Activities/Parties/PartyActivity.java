package com.sjsu.partyplanner.Activities.Parties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sjsu.partyplanner.Controllers.PartyController;
import com.sjsu.partyplanner.Controllers.UserController;
import com.sjsu.partyplanner.Models.Party;
import com.sjsu.partyplanner.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class PartyActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Party> allParties = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_page);

        // Party Controller
        PartyController p = new PartyController();
        p.getParties(this, UserController.currentUser.getUid());

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Parties");
        toolbar.setNavigationOnClickListener(v -> finish());    // Goes back to Dashboard

        // Manage Tab Layout
        initializeTabLayout();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Empty
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Empty
            }
        });
    }

    // onClick Method: Create Party
    public void createParty (View view) {
        startActivity(new Intent(this, CreatePartyActivity.class));
    }

    // Handle Fetch Parties
    public void handleFetchParties(boolean isSuccessful, ArrayList<Party> p){
        if (isSuccessful){
            allParties = p;
            Log.d("parties length", ""+allParties.size());

        }else{
            //TODO: display some error message
        }
    }




    // Initialize TabLayout
    private void initializeTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.partiesTabLayout);
        viewPager = findViewById(R.id.partiesViewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
    }

    /**
     * Inner Class for ViewPager Adapter
     * Sets up the Fragment
     */
    public class PagerAdapter extends FragmentPagerAdapter {

        private int numOfTabs;

        public PagerAdapter(FragmentManager fm, int numOfTabs) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.numOfTabs = numOfTabs;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                    //TODO: get parties from database
                    //TODO: set data to fragment (not working right now)

                    // TEMP PARTIES
                    ArrayList<Party> upParties = new ArrayList<Party>();
                    upParties.add(new Party("Pname", "PType", "PLocation", "PDescription", new Date(), "userID"));
                    upParties.add(new Party("Pname2", "PType2", "PLocation", "PDescription", new Date(), "userID"));
                    upParties.add(new Party("Pname3", "PType3", "PLocation", "PDescription", new Date(), "userID"));

                    // Create Bundle of Parties
                    Bundle uBundle = new Bundle();
                    uBundle.putParcelableArrayList("key", upParties);


                    // Create Fragment with Arguments
                    PartiesFragment upFrag = new PartiesFragment();
                    upFrag.setArguments(uBundle);

                    return upFrag;
                case 1:


                    // TEMP PARTYIES
                    ArrayList<Party> pastParties = new ArrayList<Party>();
                    pastParties.add(new Party("O Pname", "PType", "PLocation", "PDescription", new Date(), "userID"));
                    pastParties.add(new Party("O Pname2", "PType2", "PLocation", "PDescription", new Date(), "userID"));
                    pastParties.add(new Party("O Pname3", "PType3", "PLocation", "PDescription", new Date(), "userID"));

                    // Create Bundle of Parties
                    Bundle pBundle = new Bundle();
                    pBundle.putParcelableArrayList("key", pastParties);


                    // Create Fragment with Arguments
                    PartiesFragment pastFrag = new PartiesFragment();
                    pastFrag.setArguments(pBundle);


                    return pastFrag;
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