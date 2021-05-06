package com.sjsu.partyplanner.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.sjsu.partyplanner.Controllers.PartyController;

public class Invitation implements Parcelable {

    private String partyId;
    private String hostName;
    private String inviteTitle;
    private String dateTime;

    private boolean hasRead = false;        // Has read the invitation
    private boolean hasSelected = false;    // Made a choice to go or not to got (has decided)
    private boolean accepted = false;       // Going or Not Going (By default, not going)
    private String guestId;

    public Invitation() {}

    public Invitation(String partyId, String inviteTitle, String hostName, String guestId){
        this.partyId=partyId;
        this.inviteTitle=inviteTitle;
        this.guestId = guestId;
        this.hostName =hostName;
    }

    /**
     * Permanently sets the invitation to hasRead.
     */
    public void setHasRead() {
        hasRead = true;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
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

    // Parcelable Implementation
    protected Invitation(Parcel in) {
        partyId = in.readString();
        hostName = in.readString();
        inviteTitle = in.readString();
        hasRead = in.readByte() != 0;
        hasSelected = in.readByte() != 0;
        accepted = in.readByte() != 0;
        guestId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(partyId);
        dest.writeString(hostName);
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


    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
