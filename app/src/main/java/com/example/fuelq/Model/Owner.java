/*
  ---------------------
   Model: Owner.java
  ---------------------
*/

package com.example.fuelq.Model;

public class Owner {
    String ownerName;
    String ownerEmail;
    String ownerPassword;
    String ownerContact;
    String ownerFuelStation;
    String ownerLocation;

    public Owner(String ownerName, String ownerEmail, String ownerPassword, String ownerContact, String ownerFuelStation, String ownerLocation) {
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.ownerPassword = ownerPassword;
        this.ownerContact = ownerContact;
        this.ownerFuelStation = ownerFuelStation;
        this.ownerLocation = ownerLocation;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerPassword() {
        return ownerPassword;
    }

    public void setOwnerPassword(String ownerPassword) {
        this.ownerPassword = ownerPassword;
    }

    public String getOwnerContact() {
        return ownerContact;
    }

    public void setOwnerContact(String ownerContact) {
        this.ownerContact = ownerContact;
    }

    public String getOwnerFuelStation() {
        return ownerFuelStation;
    }

    public void setOwnerFuelStation(String ownerFuelStation) {
        this.ownerFuelStation = ownerFuelStation;
    }

    public String getOwnerLocation() {
        return ownerLocation;
    }

    public void setOwnerLocation(String ownerLocation) {
        this.ownerLocation = ownerLocation;
    }
}
