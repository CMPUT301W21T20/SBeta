package com.example.sbeta;

import android.location.Location;

import java.util.Date;

public class measurementTrial extends Trial {
    public measurementTrial(double result, String participant, Location location, String trialName, int trialNum, Date date) {
        super(result, participant, location, trialName, trialNum, date);
    }
}
