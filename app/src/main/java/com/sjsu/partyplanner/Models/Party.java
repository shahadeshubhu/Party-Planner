package com.sjsu.partyplanner.Models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Date;

@IgnoreExtraProperties
public class Party {
  private String name;
  private String address;
  private String description;
  private String type;
  private Date date;
  private ArrayList<Item> partyNeededItem;
  private ArrayList<User> guesses;
  private String userID;
  public Party(){}

  public Party(String name, String type, String location, String des, Date startAt, String userID){
    this.name = name;
    this.address = location;
    this.date = startAt;
    this.description = des;
    this.type = type;
    this.userID = userID;
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

  public ArrayList<User> getGuesses() {
    return guesses;
  }

  public String getUserID() {
    return userID;
  }

  public void setPartyNeededItem(ArrayList<Item> partyNeededItem) {
    this.partyNeededItem = partyNeededItem;
  }

  public void setGuesses(ArrayList<User> guesses) {
    this.guesses = guesses;
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

  public void setUserID(String userID) {
    this.userID = userID;
  }

  @Override
  public String toString() {
    return "Party{" +
      "name='" + name + '\'' +
      ", address='" + address + '\'' +
      ", description='" + description + '\'' +
      ", type='" + type + '\'' +
      ", date=" + date +
      ", owner=" + userID +
      '}';
  }
}
