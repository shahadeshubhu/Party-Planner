package com.sjsu.partyplanner.Activities.Dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.partyplanner.databinding.FragmentGuestListBinding;
import com.sjsu.partyplanner.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 */
public class GuestFragment extends DialogFragment {

  // TODO: Customize parameter argument names
  private static final String ARG_COLUMN_COUNT = "column-count";
  // TODO: Customize parameters
  private int mColumnCount = 1;
  private FragmentGuestListBinding fGuestListBinding;
  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public GuestFragment() {
  }

  // TODO: Customize parameter initialization
  @SuppressWarnings("unused")
  public static GuestFragment newInstance(int columnCount) {
    GuestFragment fragment = new GuestFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_COLUMN_COUNT, columnCount);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    fGuestListBinding = FragmentGuestListBinding.inflate(getLayoutInflater());
    View view = fGuestListBinding.getRoot();

    // Set the adapter
    if (view instanceof RecyclerView) {
      Context context = view.getContext();
      RecyclerView recyclerView = (RecyclerView) view;
      if (mColumnCount <= 1) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
      } else {
        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
      }
      recyclerView.setAdapter(new GuestRecyclerViewAdapter(DummyContent.ITEMS));
    }
    return view;
  }

//  @Override
//  public void onDestroyView() {
//    super.onDestroyView();
//    fGuestListBinding = null;
//  }
}