package com.sjsu.partyplanner.Models;

import java.io.Serializable;

public class Item implements Serializable {
  private String name;
  private enum Type {
    FOOD,
    DRINK,
    DECORATION
  }
}
