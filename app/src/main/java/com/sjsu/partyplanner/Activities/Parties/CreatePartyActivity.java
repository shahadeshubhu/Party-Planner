package com.sjsu.partyplanner.Activities.Parties;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.sjsu.partyplanner.Controllers.PartyController;
import com.sjsu.partyplanner.Controllers.UserController;
import com.sjsu.partyplanner.Models.Guest;
import com.sjsu.partyplanner.Models.Party;
import com.sjsu.partyplanner.Models.Task;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityCreatePartyBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class CreatePartyActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int VIEW_CODE = 1;
    public static final int GUEST_INVITE_VIEW_CODE = 300;
    public static final String GUEST_KEY = "GUEST_LIST";
    public static final String CREATE_TASK_KEY = "CREATE_TASK_KEY";
    private static final String TAG = "OnCreatePartyActivity";

    private Toolbar toolbar;
    private Button btnDatePicker, btnTimePicker;
    private EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private PartyController partyController;
    private Party party;
    private ArrayList<Guest> selectedGuests = new ArrayList<>();

    private ArrayList<Guest> guests;
    private ArrayList<Task> tasks;
    private Calendar pickedDateTime;
    protected ActivityCreatePartyBinding binding;
    public Party createdParty;
    private String location;
    // Autocomplete suggestions:
    private static final String[] PARTY_TYPES = new String[] {
            "Birthday Party", "Graduation Party", "Anniversary Party", "Christmas Party", "Easter Party", "Thanksgiving Party", "Costume Party",
            "Halloween Party", "New Year's Eve Party", "Dance Party", "Housewarming Party", "Pool Party", "Garden Party", "Tea Party",
            "Fundraising Party", "Dinner Party", "Reception", "Holiday", "Bachelor Party", "Bacherlorette Party",
            "Farewell Party", "Sleepover", "Pizza Party"
    };

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pickedDateTime = Calendar.getInstance();
        binding = ActivityCreatePartyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // setting up tasks
         tasks = new ArrayList<Task>();
        // Toolbar, Date/Time Picker, Autocomplete Suggestions
        setUpToolbar();
        setupPickers();
        setupAutoComplete();

        // Party Controller
        partyController = PartyController.getInstance();
        party = new Party();

        // Setting up autocomplete fragment
      // Initializing Google Places
      Places.initialize(getApplicationContext(), "AIzaSyBEB2d7C3dCIXAKII3eBRpjofu0Rokw36A");
      PlacesClient placesClient = Places.createClient(this);
      location = "";
      setUpLocationAutocompleteFragment();
    }

    private void setUpLocationAutocompleteFragment()
    {
        // Set up autocomplete fragement
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS);
        autocompleteFragment.setCountries("US");
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.d(TAG, "onPlaceSelected: Place: Address: "  + place.getAddress()) ;
                location = place.getAddress();
                autocompleteFragment.setText(location);

            }

            @Override
            public void onError(@NonNull Status status) {
                Log.d(TAG, "onError: Error in Places API " + status.toString());
                location = "";
            }
        });
    }
    // Handles Menu Items on Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cpCheck) {
            Log.d("date", txtDate.getText().toString());
            Log.d("time", txtTime.getText().toString());
            if (pickedDateTime.getTime().compareTo(new Date()) <= 0) {
                toastMsg("Date and Time is invalid");
                return false;
            }
            else {
                createdParty = new Party(
                        binding.cpNameText.getText().toString(),
                        binding.cpPartyTypeTB.getText().toString(),
                        location,
                        binding.cpDescriptionText.getText().toString(),
                        pickedDateTime.getTime());
                createdParty.setTasks(tasks);
                createdParty.setGuests(selectedGuests);
                partyController.createParty(this, createdParty);
                toastMsg(binding.cpNameText.getText().toString());
                return true;
            }
        }
        else { return super.onOptionsItemSelected(item); }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("COME_BACK!","CREATE_PARTY");

        if (requestCode == VIEW_CODE && resultCode == Activity.RESULT_OK) {

            Bundle extras = data.getExtras();
            tasks = extras.getParcelableArrayList(CreateTaskListActivity.TASKLIST_KEY);
            Log.d("TASK!","Task name: \n"  + tasks.size());

        }
        else if (requestCode == GUEST_INVITE_VIEW_CODE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            selectedGuests = extras.getParcelableArrayList(CreateGuestListActivity.GUEST_LIST_KEY);
            if(selectedGuests == null){
                selectedGuests = new ArrayList<Guest>();
            }
            binding.cpGuestButton.setText(String.format("%s (%d)", "Invited Guests", selectedGuests.size()));
            Log.d("Selected Guest size!", ""+ selectedGuests.size());
        }
    }

    public void addClick(View view) {
        if (view == findViewById(R.id.cpGuestButton)) {
            if(guests == null || guests.size() <=0) {
                UserController.getAllUsers(this);
            }else{
                showInviteGuestPage(guests);
            }
        }
        else if (view == findViewById(R.id.cpTaskButton)) {

            Intent mIntent  = new Intent(this, CreateTaskListActivity.class);
            Bundle extra =  new Bundle();
            extra.putParcelableArrayList(CREATE_TASK_KEY, tasks);
            mIntent.putExtras(extra);
            startActivityForResult(mIntent, VIEW_CODE);
        }
    }


    public void showInviteGuestPage(ArrayList<Guest> allGuests){
        guests = allGuests;
        Intent intent = new Intent(this, CreateGuestListActivity.class);
        Bundle uBundle = new Bundle();
        uBundle.putParcelableArrayList(GUEST_KEY, allGuests);
        intent.putExtras(uBundle);
        startActivityForResult(intent, GUEST_INVITE_VIEW_CODE);
    }


    public void handleSuccess(){

      UserController.getUserInfo();


      Intent intent = new Intent();
      intent.putExtra("newParty", createdParty);
      setResult(RESULT_OK, intent);
      finish();
  }

  public void handleFailure(){
    toastMsg("Failed to create party");
  }



  // Sets up the autocomplete suggestions for party type
  public void setupAutoComplete() {
      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, PARTY_TYPES);
      AutoCompleteTextView partyTypeText = findViewById(R.id.cpPartyTypeTB);
      partyTypeText.setAdapter(adapter);
  }

  // Sets up the date/time pickers
  public void setupPickers () {
      btnDatePicker = findViewById(R.id.cpDateButton);
      txtDate = findViewById(R.id.cpDateTimeText);
      btnDatePicker.setOnClickListener(this);
      btnTimePicker = findViewById(R.id.cpTimeButton);
      txtTime = findViewById(R.id.cpTimeText);
      btnTimePicker.setOnClickListener(this);
  }

    // Handles Date and Time Selection
    @Override
    public void onClick(View view) {
        final Calendar c = Calendar.getInstance();
        // Date Picker
        if (view == btnDatePicker) {
            // Get Current Date
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            Log.d("calendar", ""+c );

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        String dateSet;
                        pickedDateTime.set(Calendar.YEAR, year);
                        pickedDateTime.set(Calendar.MONTH, monthOfYear);
                        pickedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // Selecting Month
                        if (monthOfYear+1 < 10) dateSet = "0" + (monthOfYear+1) + "/";
                        else dateSet = (monthOfYear+1) + "/";

                        // Selecting Day
                        if (dayOfMonth < 10) dateSet = dateSet + "0" + dayOfMonth + "/";
                        else dateSet = dateSet + dayOfMonth + "/";

                        // Adding Year
                        dateSet = dateSet + year;

                        txtDate.setText(dateSet);
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        // Time Picker
        else if (view == btnTimePicker) {

            // Get Current Time
//            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view12, hourOfDay, minute) -> {
                        pickedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        pickedDateTime.set(Calendar.MINUTE, minute);
                      // Selecting Hour (AM/PM)
                        String timeSet;
                        if (hourOfDay > 12) { hourOfDay -= 12; timeSet = "PM"; }
                        else if (hourOfDay == 0) { hourOfDay += 12; timeSet = "AM"; }
                        else if (hourOfDay == 12) timeSet = "PM";
                        else timeSet = "AM";

                        // Selecting Minute
                        String min;
                        if (minute < 10) min = "0" + minute;
                        else min = String.valueOf(minute);
                        String z = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pickedDateTime.getTime());
                        Log.d("calendar", z);

                        // Append in a StringBuilder
                        String time = String.valueOf(hourOfDay) + ':' + min + " " + timeSet;
                        txtTime.setText(time);

                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
      String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
      Log.d("calendar", time );

      time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pickedDateTime.getTime());
    }

    // Sets up Toolbar
    public void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Create Party");
        toolbar.setNavigationOnClickListener(v -> finish());        // Closes Activity
    }

    // Adds Icons to Toolbar (other than back button)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_menu, menu);
        return true;
    }

    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }
}