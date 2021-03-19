package com.sjsu.partyplanner.Activities.Parties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sjsu.partyplanner.R;

public class PartyActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_page);

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    // Initialize TabLayout
    private void initializeTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.partiesTabLayout);
        viewPager = findViewById(R.id.partiesViewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
    }

    /**
     * Inner Class for ViewPager Adapter
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
                    return new UpcomingPartiesFragment();
                case 1:
                    return new PastPartiesFragment();
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