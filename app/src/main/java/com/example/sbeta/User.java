package com.example.sbeta;

import com.google.type.Expr;

import java.util.ArrayList;

/**
 * This class is used to model a User,
 * it has string fields of userID, userName, and contactInfo
 * it has two arraylists of subscribedExperimetns and ownedExperiemnts
 */
public class User {
    String userID;
    String userName;
    String contactInfo;
    ArrayList<Experiment> subscribedExperiments;
    ArrayList<Experiment> ownedExperiments;

    public User(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    /**
     * Get the subsribed Experiments of this user
     * @return
     *   Return an ArrayList of Experiments subcribed by this user
     */
    public ArrayList<Experiment> subscribedExperiments() {
        return subscribedExperiments;
    }

    /**
     * Get the own Experiments of this user
     * @return
     *   Return an ArrayList of Experiments own by this user
     */
    public ArrayList<Experiment> getOwnedExperiments() {
        return ownedExperiments;
    }

    /**
     * Given a new experiment, add it to the user's subsribed experiments list
     * @param newExp
     */
    public void addsubscribedExperimetns (Experiment newExp) {
        this.subscribedExperiments.add(newExp);
        return;
    }

    /**
     * Check if a particular experiment is subsribed by this user
     * @param experiment
     * @return
     *   true if this experiment is subscribed by this user,
     *   false otherwise
     */
    public boolean checkSubscribed(Experiment experiment) {
        if (this.subscribedExperiments.contains(experiment)) {
            return true;
        }
        else {
            return false;
        }
    }

}
