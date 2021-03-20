package com.sjsu.partyplanner.Controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sjsu.partyplanner.Activities.Parties.CreatePartyActivity;
import com.sjsu.partyplanner.Activities.Parties.PartyActivity;
import com.sjsu.partyplanner.Models.Party;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PartyController {
  public static final String EVENT_DB_NAME = "Parties";
  private final FirebaseFirestore db = FirebaseFirestore.getInstance();

  public void createParty(CreatePartyActivity activity, Party p){
    db.collection(EVENT_DB_NAME).add(p)
      .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
       @Override
        public void onSuccess(DocumentReference documentReference) {
          activity.handleSuccess();
          Log.d("#EC createEvent success", "DocumentSnapshot written with ID: " + documentReference.getId());
        }
      })
      .addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          Log.w("#EC createEvent fail", "Error adding document", e);
          activity.handleFailure();
        }
      });
  }

  public void getParties(PartyActivity activity, String userId){
    Log.d("getParties userId", userId);

    db.collection(EVENT_DB_NAME)
      .whereEqualTo("userID", userId)
      .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        ArrayList<Party> parties = new ArrayList<>();

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
          if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : task.getResult()) {
              Party p = document.toObject(Party.class);
              parties.add(p);
              Log.d("#getParties", document.getId() + " => " + p.toString());
            }
            activity.handleFetchParties(true, parties);
          } else {
            Log.d("#getParties error", "Error getting documents: ", task.getException());
            activity.handleFetchParties(false, parties);

          }
        }
      });
  }

}
