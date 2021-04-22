package com.sjsu.partyplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.sjsu.partyplanner.Controllers.UserController;
import com.sjsu.partyplanner.Models.User;
import java.util.ArrayList;


public class GuestListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listview ;
    private SparseBooleanArray sparseBooleanArray;
    private String[] ListViewItems = new String[] {
            "ListView ITEM-1",
            "ListView ITEM-2",
            "ListView ITEM-3",
            "ListView ITEM-4",
            "ListView ITEM-5",
            "ListView ITEM-6",
            "ListView ITEM-7",
            "ListView ITEM-8",
            "ListView ITEM-9",
            "ListView ITEM-10"

    };

    private ArrayList<User> userList = new ArrayList<User>();
    private ArrayList<String> invitedGuests = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list);

        setUpList();
        setUpToolbar();
        setUpListView();
    }

    // Sets up the list of users
    public void setUpList() {
        //TODO: UserController.getAllUsers() is async! Must be sync, use callback! Tested with print statements
        userList = UserController.getAllUsers();
        for (User user : userList) {
            invitedGuests.add(user.getName());
        }
        //ListViewItems = (String[]) invitedGuests.toArray();
    }

    // Sets up List View
    public void setUpListView() {
        listview = (ListView)findViewById(R.id.glistView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (GuestListActivity.this, R.layout.guest_layout, ListViewItems );

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sparseBooleanArray = listview.getCheckedItemPositions();
                String ValueHolder = "" ;
                int i = 0 ;

                while (i < sparseBooleanArray.size()) {
                    if (sparseBooleanArray.valueAt(i)) {
                        ValueHolder += ListViewItems [ sparseBooleanArray.keyAt(i) ] + ",";
                    }
                    i++ ;
                }

                ValueHolder = ValueHolder.replaceAll("(,)*$", "");



                Toast.makeText(GuestListActivity.this, "ListView Selected Values = " + ValueHolder, Toast.LENGTH_LONG).show();

            }
        });
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

                 //TODO: CREATE LIST OF GUESTS
                 // MATCH userList and invitedGuestList
                 // Pass back array of userIDs


                 finish();
             }
        });

    }

    // Adds Icons to Toolbar (other than back button)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_menu, menu);
        return true;
    }
}