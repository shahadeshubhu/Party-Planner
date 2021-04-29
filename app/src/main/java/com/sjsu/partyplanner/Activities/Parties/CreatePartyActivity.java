package com.sjsu.partyplanner.Activities.Parties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.sjsu.partyplanner.Controllers.PartyController;
import com.sjsu.partyplanner.Controllers.UserController;
import com.sjsu.partyplanner.Models.Party;
import com.sjsu.partyplanner.Models.Task;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityCreatePartyBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreatePartyActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int VIEW_CODE = 1;
    private Toolbar toolbar;
    private Button btnDatePicker, btnTimePicker;
    private EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private PartyController partyController;
    private Party party;
    private Calendar pickedDateTime;
    protected ActivityCreatePartyBinding binding;
    public Party createdParty;
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

        // Toolbar, Date/Time Picker, Autocomplete Suggestions
        setUpToolbar();
        setupPickers();
        setupAutoComplete();

        // Party Controller
        partyController = new PartyController();
        party = new Party();
    }

    // Handles Menu Items on Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cpCheck:
              Log.d("date", txtDate.getText().toString());
              Log.d("time", txtTime.getText().toString());
                if(pickedDateTime.getTime().compareTo(new Date())<= 0){
                    toastMsg("Date and Time is invalid");
                    // TODO: Flag the date and time field
                    return false;
                }else {
                    createdParty = new Party(
                            binding.cpNameText.getText().toString(),
                            binding.cpPartyTypeTB.getText().toString(),
                            binding.cpLocationText.getText().toString(),
                            binding.cpDescriptionText.getText().toString(),
                            pickedDateTime.getTime());
                    partyController.createParty(this, createdParty);
                    toastMsg(binding.cpNameText.getText().toString());
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIEW_CODE && resultCode == Activity.RESULT_OK) {
            //ArrayList<Task> t = data.getParcelableArrayListExtra(TaskListActivity.TASKLIST_KEY);
            Bundle extras = data.getExtras();
            ArrayList<Task> t = extras.getParcelableArrayList(TaskListActivity.TASKLIST_KEY);
            Log.d("TASK!","Task name: \n"  + t.get(0));
            //party.addTask(t);
        }
        else {

        }
    }

    public void addClick(View view) {
        if (view == findViewById(R.id.cpGuestButton)) {

            UserController.getAllUsers();

            //TODO Send to Invite Guests Page
            toastMsg("Invite Guests");
        }
        else if (view == findViewById(R.id.cpTaskButton)) {
            startActivityForResult(new Intent(this, TaskListActivity.class), VIEW_CODE);
        }
    }


  public void handleSuccess(){
      Intent intent = new Intent();
      intent.putExtra("newParty", createdParty);
      setResult(RESULT_OK, intent);
      finish();
    //TODO: reload the party list to get update party
  }

  public void handleFailure(){
    //    TODO: Display error message
  }















  // Sets up the autocomplete suggestions for party type
  public void setupAutoComplete() {
      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, PARTY_TYPES);
      AutoCompleteTextView partyTypeText = (AutoCompleteTextView) findViewById(R.id.cpPartyTypeTB);
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
                        String dateSet = "";
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
                        String timeSet = "";
                        if (hourOfDay > 12) { hourOfDay -= 12; timeSet = "PM"; }
                        else if (hourOfDay == 0) { hourOfDay += 12; timeSet = "AM"; }
                        else if (hourOfDay == 12) timeSet = "PM";
                        else timeSet = "AM";

                        // Selecting Minute
                        String min = "";
                        if (minute < 10) min = "0" + minute;
                        else min = String.valueOf(minute);
                        String z = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pickedDateTime.getTime());
                        Log.d("calendar", z);

                        // Append in a StringBuilder
                        String time = new StringBuilder().append(hourOfDay).append(':').append(min ).append(" ").append(timeSet).toString();
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