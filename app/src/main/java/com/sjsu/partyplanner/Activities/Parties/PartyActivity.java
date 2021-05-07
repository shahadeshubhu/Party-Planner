package com.sjsu.partyplanner.Activities.Parties;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sjsu.partyplanner.Controllers.PartyController;
import com.sjsu.partyplanner.Models.Party;
import com.sjsu.partyplanner.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PartyActivity extends AppCompatActivity {

    private static final int RETURN_NEW_PARTY = 1111 ;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Party> pastParties = new ArrayList<>();
    private ArrayList<Party> upParties = new ArrayList<>();

    private PartyController p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_page);

        p = PartyController.getInstance();
        p.getParties(this);
        Log.d("PRINTINGPRINGING", "PRINTINGIPNT");

        setupToolbar();
    }

    // When Create Party Activity Finishes
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("editTextValue");

                String name = data.getStringExtra("name");
                String type = data.getStringExtra("type");
                String location = data.getStringExtra("location");
                String description = data.getStringExtra("description");
                String date = data.getStringExtra("date");

                Date date1 = null;
                try {
                    date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                upParties.add(new Party(name, type, location, description, date1));

                initializeTabLayout();
            }
        }
        else if (requestCode == RETURN_NEW_PARTY) {
            if(resultCode == RESULT_OK) {
                Party newParty = data.getParcelableExtra("newParty");
                upParties.add(newParty);
                Log.d("return PArty\n", newParty.toString());
            }
            initializeTabLayout();
        }
    }


    // Fetches parties from database
    public void handleFetchParties(boolean isSuccessful, ArrayList<Party> p){
        Date now = new Date();

        if (isSuccessful){
            for(Party party : p){
                if(party != null && party.getDate().compareTo(now) < 0) { pastParties.add(party); }
                else{ upParties.add(party); }
            }
        }

        initializeTabLayout();
    }

    // Initialize TabLayout
    private void initializeTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.partiesTabLayout);
        viewPager = findViewById(R.id.partiesViewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), pastParties,upParties);
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
    public class PagerAdapter extends FragmentPagerAdapter {

        private int numOfTabs;
        private ArrayList<Party> pastParties;
        private ArrayList<Party> upcomingParties;

        public PagerAdapter(FragmentManager fm, int numOfTabs, ArrayList<Party> pastParties,ArrayList<Party> upcomingParties) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.numOfTabs = numOfTabs;
            this.pastParties = pastParties;
            this.upcomingParties = upcomingParties;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Log.d("before pass upParties", ""+upcomingParties.size());
                    // Create Bundle of Parties
                    Bundle uBundle = new Bundle();
                    uBundle.putParcelableArrayList("key", (ArrayList<? extends Parcelable>) upcomingParties);


                    // Create Fragment with Arguments
                    PartiesFragment upFrag = new PartiesFragment();
                    upFrag.setArguments(uBundle);

                    return upFrag;
                case 1:
                    // Create Bundle of Parties
                    Bundle pBundle = new Bundle();
                    pBundle.putParcelableArrayList("key", (ArrayList<? extends Parcelable>) pastParties);


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

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Parties");
        toolbar.setNavigationOnClickListener(v -> finish());    // Goes back to Dashboard
    }

    // Adds Icons to Toolbar (other than back button)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    // Handles Menu Items on Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clAdd:
                startActivityForResult(new Intent(this, CreatePartyActivity.class), RETURN_NEW_PARTY);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}