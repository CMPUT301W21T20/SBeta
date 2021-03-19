package com.example.sbeta;

import android.location.Location;

public abstract class Trial {
    double result;
    String participant;
    Location location;
    String trialName;

    public Trial(double result, String participant, Location location, String trialName) {
        this.result = result;
        this.participant = participant;
        this.location = location;
        this.trialName = trialName;
    }

    public String getTrialName() {
        return trialName;
    }
}
