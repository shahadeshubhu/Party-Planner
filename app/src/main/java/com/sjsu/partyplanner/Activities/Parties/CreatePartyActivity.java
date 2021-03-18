package com.sjsu.partyplanner.Activities.Parties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sjsu.partyplanner.R;

import java.util.Calendar;

public class CreatePartyActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Button btnDatePicker, btnTimePicker;
    private EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);

        // Toolbar
        setUpToolbar();

        // Date/Time Picker
        btnDatePicker = findViewById(R.id.cpSelectDateButton);
        txtDate = findViewById(R.id.cpDateTB);
        btnDatePicker.setOnClickListener(this);
        btnTimePicker = findViewById(R.id.cpSelectTimeButton);
        txtTime = findViewById(R.id.cpTimeTB);
        btnTimePicker.setOnClickListener(this);
    }

    // Adds Icons to Toolbar (other than back button)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_party_menu, menu);
        return true;
    }

    // Handles Menu Items on Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cpCheck:
                // TODO Create a Party Object and put it into the database.
                toastMsg("Create Party");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addClick(View view) {
        if (view == findViewById(R.id.cpGuestButton)) {
            //TODO Send to Invite Guests Page
            toastMsg("Invite Guests");
        }
        else if (view == findViewById(R.id.cpTaskButton)) {
            //TODO Send to Add Tasks Page
            toastMsg("Add Tasks");
            startActivity(new Intent(this, TaskListActivity.class));
        }
    }

    // Handles Date and Time Selection
    @Override
    public void onClick(View view) {

        // Date Picker
        if (view == btnDatePicker) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        String dateSet = "";

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
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view12, hourOfDay, minute) -> {
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

                        // Append in a StringBuilder
                        String time = new StringBuilder().append(hourOfDay).append(':').append(min ).append(" ").append(timeSet).toString();
                        txtTime.setText(time);

                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
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

    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

}