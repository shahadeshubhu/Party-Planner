package com.sjsu.partyplanner.Controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sjsu.partyplanner.Models.Event;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class EventController {
  public static final String EVENT_DB_NAME = "Events";
  private final FirebaseFirestore db = FirebaseFirestore.getInstance();

  public void createEvent(){
    Map<String, Object> newEvent = new HashMap<>();
    newEvent.put("name", "Tokyo");
    newEvent.put("address", "Japan");
    db.collection(EVENT_DB_NAME).add(newEvent)
      .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
        @Override
        public void onSuccess(DocumentReference documentReference) {
          Log.d("#EC createEvent success", "DocumentSnapshot written with ID: " + documentReference.getId());
        }
      })
      .addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          Log.w("#EC createEvent fail", "Error adding document", e);
        }
      });
  }

}
