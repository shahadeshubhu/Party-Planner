package com.sjsu.partyplanner.Activities.Parties;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.sjsu.partyplanner.Models.Guest;
import com.sjsu.partyplanner.R;

import java.util.ArrayList;


public class CreateGuestListActivity extends AppCompatActivity{

    public static final String GUEST_LIST_KEY = "Selected Guests";
    private ListView listview ;
    private SparseBooleanArray sparseBooleanArray;
    private final ArrayList<String> ListViewItems= new ArrayList<>();
    private ArrayList<Guest> guestList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_guest_list);

        Log.d("create guest list", "creating the guest list");
        setUpToolbar();
        setUpList();

    }

    // Sets up the list of users
    public void setUpList() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            guestList = extras.getParcelableArrayList(CreatePartyActivity.GUEST_KEY);
            sparseBooleanArray = new SparseBooleanArray(guestList.size());
            int i=0;
            for(Guest g: guestList){
                ListViewItems.add(g.toString());
                sparseBooleanArray.append(i,false);
                i++;
            }
        }

        setUpListView();
    }

    // Sets up List View
    public void setUpListView() {
        listview = findViewById(R.id.glistView);
        ArrayAdapter<String> adapter = new ArrayAdapter<> (CreateGuestListActivity.this, R.layout.guest_layout, ListViewItems );

        listview.setAdapter(adapter);
        listview.setOnItemClickListener((parent, view, position, id) -> {
            sparseBooleanArray = listview.getCheckedItemPositions();
            Log.d("onItemClick", ""+sparseBooleanArray.toString());
        });
    }

    // When you click the checkmark, you create a guest list to pass back!
    private void passSelectedGuest() {
        ArrayList<Guest> invitedGuests = new ArrayList<>();
        int i = 0;
        while (i < sparseBooleanArray.size()) {
            if(sparseBooleanArray.valueAt(i)){
                invitedGuests.add(guestList.get(sparseBooleanArray.keyAt(i)));
            }
            i++ ;
        }
        Log.d("invitedGuests", ""+ invitedGuests.toString());
        Intent rIntent =  new Intent(this, CreatePartyActivity.class);
        Bundle extra =  new Bundle();
        extra.putParcelableArrayList(GUEST_LIST_KEY, invitedGuests);
        rIntent.putExtras(extra);
        setResult(Activity.RESULT_OK, rIntent);
        finish();
    }


    // Sets up Toolbar
    public void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add Guests");
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    // Adds Icons to Toolbar (other than back button)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cpCheck) {
            passSelectedGuest();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}