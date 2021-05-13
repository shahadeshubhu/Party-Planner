package com.sjsu.partyplanner.Activities.Parties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sjsu.partyplanner.Models.Party;
import com.sjsu.partyplanner.Models.Task;
import com.sjsu.partyplanner.R;

import java.util.ArrayList;

public class PartyDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Party party;
    public static final String GUEST_KEY = "GUEST_LIST";
    public static final String TASK_KEY = "TASK_LIST";
    public static final String PARTY_ID = "com.sjsu.partyplanner.Activities.Parties.partyid";
    public static final String NEW_PARTY = "com.sjsu.partyplanner.Activities.Parties.newparty";
    public static final int DETAIL_REQUEST = 515;
    private static final String TAG = "OnPartyDetail";


    private TextView name;
    private TextView type;
    private TextView description;
    private TextView location;
    private TextView dateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_detail);


        // Toolbar, TextView
        setUpToolbar();
        setTV();
    }


    // onClick method for buttons
    public void onClick(View v) {
        // Task List Button OnClick
        if(v == findViewById(R.id.pdTaskButton)) {
            Intent intent = new Intent(this, TaskListActivity.class);
            Bundle uBundle = new Bundle();
            uBundle.putParcelableArrayList(TASK_KEY, (ArrayList<? extends Parcelable>) party.getTasks());

            intent.putExtras(uBundle);
            startActivityForResult(intent, DETAIL_REQUEST);
        }
        // Guest List Button OnClick
        else if (v == findViewById(R.id.pdGuestButton)) {
            Intent intent = new Intent(this, GuestListActivity.class);
            Bundle uBundle = new Bundle();
            uBundle.putParcelableArrayList(GUEST_KEY, (ArrayList<? extends Parcelable>) party.getGuests());
            intent.putExtras(uBundle);
            startActivity(intent);
        }
    }

    // Adds Icons to Toolbar (other than back button)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    // Handles Menu Items on Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.emEdit) {
            toastMsg("Edit Button (Unfinished)");
            /*
            Intent intent = new Intent(this, EditPartyActivity.class);
            intent.putExtra("partyInfo", party);
            startActivity(intent);
             */
            return true;
        }
        else if (item.getItemId() == R.id.partyCheck)
        {

            Intent intent = new Intent();
            Bundle extra = new Bundle();
            extra.putParcelable(NEW_PARTY, party);
            intent.putExtras(extra);
            Log.d("Testing", "onOptionsItemSelected: setting a reply");
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    // Sets up Toolbar
    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Party Details");
        toolbar.setNavigationOnClickListener(v -> finish());        // Closes Activity
    }


    // Added this:

    // Sets up TextViews
    private void setTV() {
        name = findViewById(R.id.id_pName);
        type = findViewById(R.id.id_pType);
        description = findViewById(R.id.id_pDescriptionText);
        location = findViewById(R.id.id_locationText);
        dateTime = findViewById(R.id.id_dateTimeText);

        party = getIntent().getParcelableExtra("party");
        Log.d("Testing", "setTV: Loading party details");
        Log.d("Testing", "Testing: Party recieved on PartyDetailsActivity: " + party.getTasks().get(1));
        Log.d(TAG, "setTV: calling intent again. ");
        name.setText(party.getName());
        type.setText(party.getType());
        description.setText(party.getDescription());
        location.setText(party.getAddress());
        dateTime.setText((party.getDate()).toString());

    }

    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: recieved intent, request code: " + requestCode + " result code: " + resultCode);

        if (requestCode == DETAIL_REQUEST)
        {
            if (resultCode == RESULT_OK)
            {
                Bundle bundle = data.getExtras();
                //ArrayList<Task> temp2 = data.getParcelableArrayListExtra(TaskListActivity.REPLY_KEY);

                if (bundle != null)
                {
                    Log.d(TAG, "onActivityResult: correctly recieved result intent");
                    ArrayList<Task> tempTaskList = bundle.getParcelableArrayList(TaskListActivity.REPLY_KEY);
                    party.setTasks(tempTaskList);

                    for ( int i = 0; i < tempTaskList.size(); i++)
                    {
                        Log.d(TAG, "onActivityResult: " + tempTaskList.get(i));
                    }
                }
                else
                {
                    Log.d(TAG, "onActivityResult: result list is empty");
                }

            }
        }
    }
}