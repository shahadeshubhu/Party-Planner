package com.sjsu.partyplanner.Activities.Parties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
  private ActivityDetailBinding binding;
  private FragmentTransaction fragmentTransaction;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityDetailBinding.inflate(getLayoutInflater());
    setContentView( binding.getRoot());
    fragmentTransaction = getSupportFragmentManager().beginTransaction();
  }

  public void showGuests(View view){
    // Begin the transaction
    // Replace the contents of the container with the new fragment
    fragmentTransaction.replace(R.id.eventDetailDisplay, new GuestFragment());
    // or ft.add(R.id.your_placeholder, new FooFragment());
    // Complete the changes added above
    fragmentTransaction.commit();
  }
}