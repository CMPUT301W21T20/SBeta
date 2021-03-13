package com.example.sbeta;

import java.util.ArrayList;

public class User {
    String userID;
    String contactInfo;
    ArrayList<Experiment> subscribedExperimetns;
    ArrayList<Experiment> ownedExperiemnts;


    public User(String name, String contact){
        this.userID=name;
        this.contactInfo=contact;
    }

}
