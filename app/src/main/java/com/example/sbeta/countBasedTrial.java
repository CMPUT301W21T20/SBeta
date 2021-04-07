package com.example.sbeta;

import android.location.Location;

import java.util.Date;

public class countBasedTrial extends Trial {

    public countBasedTrial(double result, String participant, Location location, String trialName, int trialNum, Date date) {
        super(result, participant, location, trialName, trialNum, date);
    }
}
