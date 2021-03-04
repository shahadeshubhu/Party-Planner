package com.sjsu.partyplanner.Models;

import java.util.ArrayList;
import java.util.Date;

public class Event {
  String name;
  String address;
  Date date;
  ArrayList<Item> partyNeededItem;
  ArrayList<User> guesses;
}
