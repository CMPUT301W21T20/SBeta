package com.example.sbeta;

import android.location.Location;

import java.sql.Timestamp;

public abstract class Trial {
    double result;
    String participant;
    Location location;
    String trialName;
    int trialNum;
    boolean isIgnored = false;
    Timestamp createdTime;

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
