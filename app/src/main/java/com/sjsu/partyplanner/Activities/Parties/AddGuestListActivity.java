package com.sjsu.partyplanner.Activities.Parties;

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
import com.sjsu.partyplanner.R;

import java.util.ArrayList;


public class AddGuestListActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_add_guest_list);

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (AddGuestListActivity.this, R.layout.guest_layout, ListViewItems );

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sparseBooleanArray = listview.getCheckedItemPositions();

                // GET RID OF THIS CODE WHEN WE CONFIRM IT WORKS
                /*
                String ValueHolder = "" ;
                int i = 0 ;
                while (i < sparseBooleanArray.size()) {
                    if (sparseBooleanArray.valueAt(i)) {
                        ValueHolder += ListViewItems [ sparseBooleanArray.keyAt(i) ] + ",";
                    }
                    i++ ;
                }
                ValueHolder = ValueHolder.replaceAll("(,)*$", "");
                Toast.makeText(AddGuestListActivity.this, "ListView Selected Values = " + ValueHolder, Toast.LENGTH_LONG).show();
                 */

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

                 //TODO: Somehow get the Party object and call the method on it - Party.setGuests()

                 /*
                 ArrayList<User> guestList = new ArrayList<User>();
                 for (User user : userList) {
                     int i = 0;
                    while (i < sparseBooleanArray.size()) {
                        if(sparseBooleanArray.valueAt(i) && ListViewItems [sparseBooleanArray.keyAt(i)] == user.getName()) {
                            guestList.add(user);
                        }
                     }
                 }
                 */




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