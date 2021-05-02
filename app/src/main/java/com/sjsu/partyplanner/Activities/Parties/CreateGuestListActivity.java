package com.sjsu.partyplanner.Activities.Parties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.sjsu.partyplanner.Controllers.UserController;
import com.sjsu.partyplanner.Models.Guest;
import com.sjsu.partyplanner.Models.Task;
import com.sjsu.partyplanner.Models.User;
import com.sjsu.partyplanner.R;

import java.util.ArrayList;


public class CreateGuestListActivity extends AppCompatActivity{

    public static final String GUEST_LIST_KEY = "Selected Guests";
    private Toolbar toolbar;
    private ListView listview ;
    private SparseBooleanArray sparseBooleanArray;

    private ArrayList<String> ListViewItems= new ArrayList<String>();

    private ArrayList<Guest> guestList = new ArrayList<Guest>();
    private ArrayList<Guest> invitedGuests = new ArrayList<Guest>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_guest_list);

        setUpList();
        setUpToolbar();
        setUpListView();
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
    }

    // Sets up List View
    public void setUpListView() {
        listview = (ListView)findViewById(R.id.glistView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (CreateGuestListActivity.this, R.layout.guest_layout, ListViewItems );

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sparseBooleanArray = listview.getCheckedItemPositions();
                Log.d("onItemClick", ""+sparseBooleanArray.toString());

            }
        });
    }



    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    // Sets up Toolbar
    public void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add Guests");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 passSelectedGuest();
                 //TODO: Cannot go back to createPartyActivity
             }
        });
    }

    private void passSelectedGuest(){
        invitedGuests = new ArrayList<Guest>();
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


    // Adds Icons to Toolbar (other than back button)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_menu, menu);
        return true;
    }
}