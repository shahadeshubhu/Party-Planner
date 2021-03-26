package com.sjsu.partyplanner.Controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
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
    p.setOwnerID(UserController.associateUserId);
    db.collection(EVENT_DB_NAME).add(p)
      .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
       @Override
        public void onSuccess(DocumentReference partyDocumentReference) {
         db.collection(UserController.ASSOCIATE_DB_NAME)
                 .document(UserController.associateUserId)
                 .update("parties", FieldValue.arrayUnion(partyDocumentReference.getId()));

          Log.d("#EC createEvent success", "DocumentSnapshot written with ID: " + partyDocumentReference.getId());
          activity.handleSuccess();
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

  public void getParties(PartyActivity activity){
      Log.d("#getParties", ""+ UserController.associateUserId);

    db.collection(EVENT_DB_NAME).whereIn(FieldPath.documentId(),UserController.currentUserInfo.getParties())
      .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        ArrayList<Party> parties = new ArrayList<>();

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
          if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : task.getResult()) {
              Party p = document.toObject(Party.class);
              parties.add(p);
            }
            activity.handleFetchParties(true, parties);
            Log.d("#getParties", ""+ parties.size());

          } else {
            Log.d("#getParties error", "Error getting documents: ", task.getException());
            activity.handleFetchParties(false, parties);

          }
        }
      });
  }

}
