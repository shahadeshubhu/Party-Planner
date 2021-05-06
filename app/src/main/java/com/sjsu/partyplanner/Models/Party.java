package com.sjsu.partyplanner.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Date;

@IgnoreExtraProperties
public class Party implements Parcelable {
  private String pId;
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




  public String getpId() {
    return pId;
  }

  public void setpId(String pId) {
    this.pId = pId;
  }


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

  public ArrayList<Task> getTasks() {
    return tasks;
  }

  public void setTasks(ArrayList<Task> tasks) {
    this.tasks = tasks;
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



  // Parcelable Code

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.pId);
    dest.writeString(this.name);
    dest.writeString(this.address);
    dest.writeString(this.description);
    dest.writeString(this.type);
    dest.writeLong(this.date != null ? this.date.getTime() : -1);
    dest.writeList(this.partyNeededItem);
    dest.writeTypedList(this.tasks);
    dest.writeTypedList(this.guests);
    dest.writeString(this.ownerID);
  }

  public void readFromParcel(Parcel source) {
    this.pId = source.readString();
    this.name = source.readString();
    this.address = source.readString();
    this.description = source.readString();
    this.type = source.readString();
    long tmpDate = source.readLong();
    this.date = tmpDate == -1 ? null : new Date(tmpDate);
    this.partyNeededItem = new ArrayList<Item>();
    source.readList(this.partyNeededItem, Item.class.getClassLoader());
    this.tasks = source.createTypedArrayList(Task.CREATOR);
    this.guests = source.createTypedArrayList(Guest.CREATOR);
    this.ownerID = source.readString();
  }

  protected Party(Parcel in) {
    this.pId = in.readString();
    this.name = in.readString();
    this.address = in.readString();
    this.description = in.readString();
    this.type = in.readString();
    long tmpDate = in.readLong();
    this.date = tmpDate == -1 ? null : new Date(tmpDate);
    this.partyNeededItem = new ArrayList<Item>();
    in.readList(this.partyNeededItem, Item.class.getClassLoader());
    this.tasks = in.createTypedArrayList(Task.CREATOR);
    this.guests = in.createTypedArrayList(Guest.CREATOR);
    this.ownerID = in.readString();
  }

  public static final Parcelable.Creator<Party> CREATOR = new Parcelable.Creator<Party>() {
    @Override
    public Party createFromParcel(Parcel source) {
      return new Party(source);
    }

    @Override
    public Party[] newArray(int size) {
      return new Party[size];
    }
  };

  // end of Parcelable code
}
