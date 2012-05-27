package com.pwners.darts;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A basic Player object that can be parcelled to transfer between objects
 * 
 * @author adamecker
 * 
 */
public class Player implements Parcelable {

    /* Private Data Members */
    private String name;
    private String phoneNumber;

    /**
     * Normal constructor
     * 
     * @param name
     *            name of player
     */
    public Player(String name, String phoneNumber) {
	this.name = name;
	this.phoneNumber = phoneNumber;
    }

    /**
     * Constructor to use when re-constructing object from a parcel
     * 
     * @param in a parcel from which to read this object
     */
    public Player(Parcel in) {
	readFromParcel(in);
    }


    /**
     * Get the name of the player
     * @return name of player
     */
    public String getName() {
	return name;
    }

    /**
     * Set the name of the player
     * @param name name of player
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
	return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    public int describeContents() {
	return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
	// Write each field into the parcel. When we read from the parcel, they
	// will come back in the same order
	dest.writeString(name);
	dest.writeString(phoneNumber);
    }

    private void readFromParcel(Parcel in) {
	// Read back each field in the order that it was written to the parcel
	name = in.readString();
	phoneNumber = in.readString();
    }

    /**
     * 
     * This field is needed for Android to be able to create new objects,
     * individually or as arrays.
     * 
     * This also means that you can use use the default constructor to create
     * the object and use another method to hyrdate it as necessary.
     * 
     */
    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
	public Player createFromParcel(Parcel in) {
	    return new Player(in);
	}

	public Player[] newArray(int size) {
	    return new Player[size];
	}
    };
}
