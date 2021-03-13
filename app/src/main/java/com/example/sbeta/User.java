package com.example.sbeta;

import java.util.ArrayList;

public class User {
    String userID;
    String contactInfo;
    ArrayList<Experiment> subscribedExperimetns;
    ArrayList<Experiment> ownedExperiemnts;

    public User(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public ArrayList<Experiment> getSubscribedExperimetns() {
        return subscribedExperimetns;
    }

    public ArrayList<Experiment> getOwnedExperiemnts() {
        return ownedExperiemnts;
    }
}
