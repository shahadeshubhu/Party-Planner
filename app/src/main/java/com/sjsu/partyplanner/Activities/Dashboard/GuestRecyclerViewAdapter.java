package com.sjsu.partyplanner.Activities.Dashboard;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sjsu.partyplanner.databinding.FragmentGuestRowBinding;
import com.sjsu.partyplanner.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class GuestRecyclerViewAdapter extends RecyclerView.Adapter<GuestRecyclerViewAdapter.GuestViewHolder> {

  private final List<DummyItem> mValues;
//  private final Context mainContext;
  public GuestRecyclerViewAdapter(List<DummyItem> items) {
    mValues = items;
  }

  @NonNull
  @Override
  public GuestRecyclerViewAdapter.GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    FragmentGuestRowBinding row = FragmentGuestRowBinding.inflate(inflater);
    return new GuestViewHolder(row);
  }

  @Override
  public void onBindViewHolder(final GuestViewHolder holder, int position) {
    DummyItem dItem = mValues.get(position);
    holder.rowBinding.setGuest(dItem);
;
  }

  @Override
  public int getItemCount() {
    return mValues.size();
  }

  public class GuestViewHolder extends RecyclerView.ViewHolder {
    protected final FragmentGuestRowBinding rowBinding;


    public GuestViewHolder(FragmentGuestRowBinding binding) {
      super(binding.getRoot());
      this.rowBinding = binding;
    }
  }
}