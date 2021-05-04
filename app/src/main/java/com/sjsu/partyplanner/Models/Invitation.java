package com.sjsu.partyplanner.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Invitation implements Parcelable {

    private String partyId;
    private String inviteTitle;
    private boolean hasRead = false;
    private boolean hasSelected = false;    // selected to go or not
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

    protected Invitation(Parcel in) {
        partyId = in.readString();
        inviteTitle = in.readString();
        hasRead = in.readByte() != 0;
        hasSelected = in.readByte() != 0;
        accepted = in.readByte() != 0;
        guestId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(partyId);
        dest.writeString(inviteTitle);
        dest.writeByte((byte) (hasRead ? 1 : 0));
        dest.writeByte((byte) (hasSelected ? 1 : 0));
        dest.writeByte((byte) (accepted ? 1 : 0));
        dest.writeString(guestId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Invitation> CREATOR = new Creator<Invitation>() {
        @Override
        public Invitation createFromParcel(Parcel in) {
            return new Invitation(in);
        }

        @Override
        public Invitation[] newArray(int size) {
            return new Invitation[size];
        }
    };

    /**
     * Permanently sets the invitation to hasRead.
     */
    public void setHasRead() {
        hasRead = true;
    }

    public boolean getHasRead() {
        return hasRead;
    }

    /**
     * The guest has selected to go or not to go to a party
     */
    public void setHasSelected() {
        hasSelected = true;
    }

    public boolean getHasSelected() {
        return hasSelected;
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
