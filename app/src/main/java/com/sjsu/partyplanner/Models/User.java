package com.sjsu.partyplanner.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private boolean isVerified;
  private ArrayList<Event> events;
  public User(String firstName, String lastName, String email, String password ){
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.events = new ArrayList<>();

  }

  public String getEmail() {
    return email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPassword() {
    return password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void addEvent(Event e){
    events.add(e);
  }
  public ArrayList<Event> getEvents(){
    return events;
  }

  @Override
  public String toString() {
    return "User{" +
      "firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      ", email='" + email + '\'' +
      ", password='" + password + '\'' +
      '}';
  }
}
