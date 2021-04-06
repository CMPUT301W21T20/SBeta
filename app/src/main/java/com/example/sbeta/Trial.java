package com.example.sbeta;

import android.location.Location;

import java.util.Calendar;
import java.util.Date;

public abstract class Trial {
    double result;
    String participant;
    Location location;
    String trialName;
    int trialNum;
    boolean isIgnored = false;
    Date createDate;

    public Trial(double result, String participant, Location location, String trialName, int trialNum) {
        this.result = result;
        this.participant = participant;
        this.location = location;
        this.trialName = trialName;
        this.trialNum = trialNum;
        this.createDate = Calendar.getInstance().getTime();
    }

    public int getTrialNum() {
        return trialNum;
    }

    public String getTrialName() {
        return trialName;
    }

    public double getResult() { return result;}

    public boolean getIsIgnored() {return isIgnored;}

    public Date getCreateDate() {
        return createDate;
    }

    public void setIgnored(boolean ignored) {
        isIgnored = ignored;
    }
}
