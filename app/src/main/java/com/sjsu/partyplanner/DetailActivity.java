package com.sjsu.partyplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sjsu.partyplanner.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
  private ActivityDetailBinding detailBinding;
  private FragmentTransaction fragmentTransaction;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    detailBinding = ActivityDetailBinding.inflate(getLayoutInflater());
    View view = detailBinding.getRoot();
    setContentView(view);
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