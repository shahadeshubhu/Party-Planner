package com.sjsu.partyplanner.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Date;

@IgnoreExtraProperties
public class Party implements Parcelable {
  private String name;
  private String address;
  private String description;
  private String type;
  private Date date;
  private ArrayList<Item> partyNeededItem;
  private ArrayList<Task> tasks = new ArrayList<>();
  private ArrayList<Guest> guests = new ArrayList<>();
  private String ownerID;

  public Party(){}

  public Party(String name, String type, String location, String des, Date startAt){
    this.name = name;
    this.address = location;
    this.date = startAt;
    this.description = des;
    this.type = type;
  }

  // Parcel Stuff------------------------------------------------
  protected Party(Parcel in) {
    name = in.readString();
    address = in.readString();
    description = in.readString();
    type = in.readString();
    ownerID = in.readString();
    date= (java.util.Date) in.readSerializable();

  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeString(address);
    dest.writeString(description);
    dest.writeString(type);
    dest.writeString(ownerID);
    dest.writeSerializable(date);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<Party> CREATOR = new Creator<Party>() {
    @Override
    public Party createFromParcel(Parcel in) {
      Log.d("#####Party","Create From Parce");
      return new Party(in);
    }

    @Override
    public Party[] newArray(int size) {
      return new Party[size];
    }
  };

  // END OF PARCEL STUFF

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public ArrayList<Item> getPartyNeededItem() {
    return partyNeededItem;
  }

  public ArrayList<Guest> getGuests() {
    return guests;
  }

  public String getOwnerID() {
    return ownerID;
  }

  public void setPartyNeededItem(ArrayList<Item> partyNeededItem) {
    this.partyNeededItem = partyNeededItem;
  }
  public void addTask(Task t){
    tasks.add(t);
  }
  public void setGuests(ArrayList<Guest> guests) {
    this.guests = guests;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Date getDate() {
    return date;
  }

  public String getType() {
    return type;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public void setOwnerID(String ownerID) {
    this.ownerID = ownerID;
  }

  @Override
  public String toString() {
    return "Party{" +
      "name='" + name + '\'' +
      ", address='" + address + '\'' +
      ", description='" + description + '\'' +
      ", type='" + type + '\'' +
      ", date=" + date +
      ", owner=" + ownerID +
      '}';
  }
}
