package com.sjsu.partyplanner.Activities.Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Toast;

import android.os.Bundle;
import android.view.View;

import com.sjsu.partyplanner.Activities.Parties.PartyActivity;
import com.sjsu.partyplanner.databinding.ActivityDashboardBinding;
import com.sjsu.partyplanner.databinding.ActivityDetailBinding;

public class DashboardActivity extends AppCompatActivity {
  private ActivityDashboardBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityDashboardBinding.inflate(getLayoutInflater());
    setContentView( binding.getRoot());
  }


// TODO Dashboard Buttons


  /**
   * onClick method for 'parties'
   * Goes to the 'parties' page
   */
  public void partyClick(View view) {
      startActivity(new Intent(this, PartyActivity.class));
  }

  /**
   * onClick method for 'events'
   * Goes to the 'events' page
   */
  public void eventClick(View view) {
    toastMsg("Event Click");
  }

  /**
   * onClick method for 'contacts'
   * Goes to the 'contacts' page
   */
  public void contactClick(View view) {
    toastMsg("Contact Click");
  }

  /**
   * onClick method for 'budget'
   * Goes to the 'budget' page
   */
  public void budgetClick(View view) {
    toastMsg("Budget Click");
  }

  /**
   * onClick method for 'user icon'
   * Opens Side Bar
   */
  public void userClick(View view) {
    toastMsg("User Icon Click");
  }

  /**
   * onClick method for 'mail'
   * Goes to the 'invitations' page
   */
  public void mailClick(View view) {
    toastMsg("Invitation Mail Click");
  }

  /**
   * Testing onClick methods
   * @param msg to toast
   */
  public void toastMsg(String msg) {
    Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
    toast.show();
  }

}