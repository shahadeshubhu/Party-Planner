package com.sjsu.partyplanner.Models;

import java.io.Serializable;
import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@com.google.firebase.firestore.IgnoreExtraProperties
public class User implements Serializable {
  private String uid;
  private String firstName;
  private String lastName;
  private String email;
  private ArrayList<String> parties;
  private ArrayList<String> invitation;

  public User(){};

  public User(String firstName, String lastName, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.parties = new ArrayList<>();
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  @com.google.firebase.firestore.Exclude
  public String getUid() {
    return uid;
  }

  public void setUid(String uid){
    this.uid=uid;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setParties(ArrayList<String> parties) {
    this.parties = parties;
  }

  public ArrayList<String> getInvitation() {
    return invitation;
  }

  public void setInvitation(ArrayList<String> invitation) {
    this.invitation = invitation;
  }

  public ArrayList<String> getParties(){
    return parties;
  }

  @Override
  public String toString() {
    return "User{" +
            "uid='" + uid + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            '}';
  }

  @com.google.firebase.firestore.Exclude
  public Map<String, Object> toMap() {
    HashMap<String, Object> user = new HashMap<>();
    user.put("firstName", firstName);
    user.put("lastName", lastName);
    return user;
  }
}
