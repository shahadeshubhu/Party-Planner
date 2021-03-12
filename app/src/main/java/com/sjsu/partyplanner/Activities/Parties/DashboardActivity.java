package com.sjsu.partyplanner.Activities.Parties;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
}