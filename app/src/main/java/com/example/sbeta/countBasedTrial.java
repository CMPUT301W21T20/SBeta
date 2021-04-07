package com.example.sbeta;

import android.location.Location;

import java.util.Date;

public class countBasedTrial extends Trial {

    public countBasedTrial(double result, String participant, Location location, String trialName, int trialNum) {
        super(result, participant, location, trialName, trialNum);
    }
}
