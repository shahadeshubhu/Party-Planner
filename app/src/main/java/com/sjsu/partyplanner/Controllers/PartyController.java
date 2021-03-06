package com.sjsu.partyplanner.Controllers;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.sjsu.partyplanner.Activities.Dashboard.InvitationDetailActivity;
import com.sjsu.partyplanner.Activities.Dashboard.InvitationListActivity;
import com.sjsu.partyplanner.Activities.Events.EventActivity;
import com.sjsu.partyplanner.Activities.Parties.CreatePartyActivity;
import com.sjsu.partyplanner.Activities.Parties.PartyActivity;
import com.sjsu.partyplanner.Models.Guest;
import com.sjsu.partyplanner.Models.Invitation;
import com.sjsu.partyplanner.Models.Party;

import java.util.ArrayList;

public class PartyController {
    public static final String EVENT_DB_NAME = "Parties";
    public static final String INVITE_DB_NAME= "Invitations";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private static PartyController PartyController_instance = null;

    // Constructor
    private PartyController() {}

    // static method to create instance of Singleton class
    public static PartyController getInstance()
    {
        if (PartyController_instance == null)
            PartyController_instance = new PartyController();

        return PartyController_instance;
    }

    // Create Party in database
    public void createParty(CreatePartyActivity activity, Party p) {
        //Log.d("databaseDebug", "createParty: saving database");
        String ownerId = UserController.currentUser.getUid();
        p.setOwnerID(ownerId);
        db.collection(EVENT_DB_NAME).add(p)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference partyDocumentReference) {
                        db.collection(UserController.ASSOCIATE_DB_NAME)
                                .document(ownerId)
                                .update("parties", FieldValue.arrayUnion(partyDocumentReference.getId()));

                        //Log.d("#EC createEvent success", "DocumentSnapshot written with ID: " + partyDocumentReference.getId());
                        //Log.d("databaseDebug", "createParty: saved successfuly with id: " + partyDocumentReference.getId());
                        ArrayList<Guest> guests = p.getGuests();
                        if (guests == null || guests.size() <= 0){
                            activity.handleSuccess();
                            return;
                        }
                        inviteGuestByEmail(activity, guests, partyDocumentReference.getId(), p.getName(),
                                String.format("%s %s",UserController.currentUserInfo.getFirstName(),
                                        UserController.currentUserInfo.getLastName()),
                                String.valueOf(p.getDate()));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w("#EC createEvent fail", "Error adding document", e);
                        //Log.d("databaseDebug", "createParty: failed to save");
                        activity.handleFailure();
                    }
                });
    }

    // Gets Parties from database for PartyActivity
    public void getParties(PartyActivity activity) {
        ArrayList<Party> parties = new ArrayList<>();
        ArrayList<String> userParties = UserController.getCurrentUser().getParties();     //************changed!
        if(userParties != null && userParties.size() > 0) {
            db.collection(EVENT_DB_NAME).whereIn(FieldPath.documentId(), userParties)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Party p = document.toObject(Party.class);
                            //Log.d("#document data", "" + document.getId());
                            p.setpId(document.getId()); /// ***********changed;
                            parties.add(p);
                        }
                        activity.handleFetchParties(true, parties);
                        //Log.d("#getParties", "" + parties.size());

                    } else {
                        //Log.d("#getParties error", "Error getting documents: ", task.getException());
                        activity.handleFetchParties(false, parties);

                    }
                }
            });
        }
    }

    // Gets Parties from database for EventActivity
    public void getParties(EventActivity activity) {
        ArrayList<Party> parties = new ArrayList<>();
        ArrayList<String> userParties = UserController.getCurrentUser().getParties();
        if(userParties != null && userParties.size() > 0) {
            db.collection(EVENT_DB_NAME).whereIn(FieldPath.documentId(), userParties)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Party p = document.toObject(Party.class);
                            //Log.d("#document data", "" + document.getId());

                            parties.add(p);
                        }
                        activity.handleFetchParties(true, parties);
                        //Log.d("#getParties", "" + parties.size());

                    } else {
                        //Log.d("#getParties error", "Error getting documents: ", task.getException());
                        activity.handleFetchParties(false, parties);

                    }
                }
            });
        }
    }

    // Create Invitations for Party Created
    public void inviteGuestByEmail(CreatePartyActivity activity, ArrayList<Guest> guests, String partyId, String partyTitle, String hostName, String dateTime) {

        ArrayList<Invitation> invitations = new ArrayList<>();
        ArrayList<String> emailList = new ArrayList<>();
        String subject = "YOU ARE INVITED!!!";
        String text = ("Hi," +"\n" + "You have been invited to a party. Please accept the " +
                "invitation on the PartyPlanner app. Your presence would certainly mean a lot to us"
                + "\n" + "Regards,"+"\n" + "Team PartyPlanner");

        for( Guest g : guests){
            invitations.add(new Invitation(partyId, partyTitle, hostName, g.getUid(), dateTime));
            emailList.add(g.getEmail());
        }
        String[] emailArray = emailList.toArray(new String[0]);

        final Intent emailLauncher = new Intent(Intent.ACTION_SEND);
        emailLauncher.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailLauncher.putExtra(Intent.EXTRA_EMAIL, emailArray);
        emailLauncher.putExtra(Intent.EXTRA_TEXT, text);
        emailLauncher.setType("message/rfc822");
        try{
            activity.startActivity(emailLauncher);
        }catch(ActivityNotFoundException e){
            //Log.d("Error", e.getMessage());
        }

        //Log.d("inviteGuestByEmail", ""+invitations.size());
        if (invitations.size()>0) {
            WriteBatch batch = db.batch();
            for(Invitation i : invitations){
                batch.set(db.collection(INVITE_DB_NAME).document(), i);
            }
            // Commit the batch
            batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    activity.handleSuccess();
                }
            });
        } else {
            //Log.d("inviteGuestByEmail", " No guest ");
            activity.handleSuccess();
        }

    }

    // Get Invitations for InvitationListActivity
    public void getUserInvitations(InvitationListActivity activity){
        String uId = UserController.currentUser.getUid();
        db.collection(INVITE_DB_NAME).whereEqualTo("guestId", uId)
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Invitation> invitations = new ArrayList<>();
                if(task.isComplete()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        invitations.add(document.toObject(Invitation.class));
                    }
                    activity.handleGetInvitationSuccess(invitations);
                }
            }
        });
    }

    // Get Invitations for InvitationListActivity
    public void getUserInvitations(EventActivity activity){
        String uId = UserController.currentUser.getUid();
        db.collection(INVITE_DB_NAME).whereEqualTo("guestId", uId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Invitation> invitations = new ArrayList<>();
                if(task.isComplete()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        invitations.add(document.toObject(Invitation.class));
                    }
                    activity.handleGetInvitationSuccess(invitations);
                }
            }
        });
    }

    // Get Party for InvitationListActivity based on partyID
    public void getParty(String partyId, InvitationDetailActivity activity){
        db.collection(EVENT_DB_NAME).whereEqualTo(FieldPath.documentId(), partyId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Party p = document.toObject(Party.class);
                        p.setpId(document.getId());
                        //Log.d("#getParty", document.getId() + " => " + p.toString());
                        activity.handleGetPartySuccess(p);
                    }
                } else {
                    //Log.d("#getParty error", "Error getting documents: ", task.getException());

                }
            }
        });
    }

    // Get Party for EventActivity based on partyID
    public void getParty(String partyId, EventActivity activity){
        db.collection(EVENT_DB_NAME).whereEqualTo(FieldPath.documentId(), partyId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Party p = document.toObject(Party.class);
                        p.setpId(document.getId());
                        //Log.d("#getParty", document.getId() + " => " + p.toString());
                        activity.handleGetPartySuccess(p);
                    }
                } else {
                    //Log.d("#getParty error", "Error getting documents: ", task.getException());

                }
            }
        });
    }

    public void updateParty(PartyActivity activity, Party inputParty)
    {

        //Log.d("Testing", "updateParty: executing updatePary in PartyController");
        if (inputParty != null && inputParty.getpId() != null && !inputParty.getpId().isEmpty())
        {
            String documentId  = inputParty.getpId();
            DocumentReference reference = db.collection(EVENT_DB_NAME).document(documentId);

            reference.set(inputParty).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        //Log.d("Testing", "onComplete: successfully updated document");
                        //getParties(activity);
                        activity.handleUpdateSuccess();
                    }
                    else
                    {
                        //Log.d("Testing", "onComplete: failed to update document");
                        activity.handleUpdateFailure();

                    }
                }
            });
        }
        else
        {
            //Log.d("Testing", "updateParty: Failure, party or document ID are null or empty");
            activity.handleUpdateFailure();
        }
    }



}
