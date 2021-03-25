package com.sjsu.partyplanner.Models;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User implements Serializable {
  public String uid;
  private String firstName;
  private String lastName;
  private boolean isVerified;
  private ArrayList<Party> parties;

  public User(){};

  public User(String uid, String firstName, String lastName){
    this.firstName = firstName;
    this.lastName = lastName;
    this.uid = uid;
    this.parties = new ArrayList<>();

  }

  public User(String firstName, String lastName){
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }



  public void addEvent(Party e){
    parties.add(e);
  }
  public ArrayList<Party> getParties(){
    return parties;
  }

  @NotNull
  @Override
  public String toString() {
    return "User{" +
      "firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      '}';
  }

  @Exclude
  public Map<String, Object> toMap() {
    HashMap<String, Object> user = new HashMap<>();
    user.put("uid", uid);
    user.put("firstName", firstName);
    user.put("lastName", lastName);
    return user;
  }
}
