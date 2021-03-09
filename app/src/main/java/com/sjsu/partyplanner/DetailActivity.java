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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    detailBinding = ActivityDetailBinding.inflate(getLayoutInflater());
    View view = detailBinding.getRoot();
    setContentView(view);

  }

  public void showGuests(View view){
//    Intent intent = new Intent(this, DetailFragment.class);
////    intent.putExtra(DETAIL_MESSAGE, selectedItem);
//    startActivity(intent);
    // Begin the transaction
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
    ft.replace(R.id.eventDetailDisplay, new GuestFragment());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
    ft.commit();
  }
}