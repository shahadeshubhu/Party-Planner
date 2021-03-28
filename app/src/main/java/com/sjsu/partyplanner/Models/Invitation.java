package com.sjsu.partyplanner.Models;

public class Invitation {
    private String partyId;
    private String inviteTitle;
    private boolean accepted = false;
    private String guestId;
    public Invitation(){}
    public Invitation(String partyId, String inviteTitle, String guestId){
        this.partyId=partyId;
        this.inviteTitle=inviteTitle;
        this.guestId = guestId;
    }

    public Invitation(String partyId, String inviteTitle, String guessId, boolean accepted){
        this(partyId,inviteTitle,guessId);
        this.accepted=accepted;
    }

    public String getPartyId() {
        return partyId;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getInviteTitle() {
        return inviteTitle;
    }

    public void setInviteTitle(String inviteTitle) {
        this.inviteTitle = inviteTitle;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "partyId='" + partyId + '\'' +
                ", inviteTitle='" + inviteTitle + '\'' +
                ", accepted=" + accepted +
                ", guestId='" + guestId + '\'' +
                '}';
    }


}
