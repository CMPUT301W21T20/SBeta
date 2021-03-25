package com.example.sbeta;

import android.location.Location;

public abstract class Trial {
    double result;
    String participant;
    Location location;
    String trialName;
    int trialNum;

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
}
