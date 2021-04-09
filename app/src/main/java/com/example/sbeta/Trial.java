package com.example.sbeta;

import android.location.Location;


import java.sql.Timestamp;

/**
 * This is an abstract class representing a trial
 */

public abstract class Trial {
    double result;
    String participant;
    Location location;
    String trialName;
    int trialNum;
    boolean isIgnored = false;

    Timestamp createdTime;

    /**
     * Constructor for Trial
     * @param result (Double) trial result
     * @param participant (String) participant name
     * @param location (Location) location
     * @param trialName (String) trial name
     * @param trialNum (Integer) trial number
     */
    public Trial(double result, String participant, Location location, String trialName, int trialNum) {
        this.result = result;
        this.participant = participant;
        this.location = location;
        this.trialName = trialName;
        this.trialNum = trialNum;
    }

    public int getTrialNum() {
        return trialNum;
    }

    public String getTrialName() {
        return trialName;
    }

    public double getResult() { return result;}

    public boolean getIsIgnored() {return isIgnored;}

    public void setIgnored(boolean ignored) {
        isIgnored = ignored;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }
}
