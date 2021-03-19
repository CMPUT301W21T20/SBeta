package com.example.sbeta;

/**
 * This is a class that keeps track of experiment objects
 */
import java.util.ArrayList;

public class Experiment {
    ArrayList<Trial> trials;
    User owner;
    ArrayList<User> participants;
    String description;
    Boolean isEnded;
    Boolean isPublished;
    long minTrials;
    Boolean locationRequired;
    String experimentType;
    String status;
    String name;
    String userName;

//    public Experiment(User owner, String description, String status, String name) {
//        this.owner = owner;
//        this.description = description;
//        this.status = status;
//        this.name = name;
//    }

    /**
     * This create a new experiment
     * @param description
     *      This is the description of experiments
     * @param isEnded
     *      This is to show whether the experiment has ended
     * @param isPublished
     *      This is to show whether the experiment has published
     * @param minTrials
     *      This is how many trials needed
     * @param locationRequired
     *      This is to show whether the experiment need location
     * @param experimentType
     *      This is to show which type the experiment is
     * @param name
     *      This is the name of experiments
     * @param userName
     *      This is the owner of experiments
     */
    public Experiment(String description, Boolean isEnded, Boolean isPublished, long minTrials, Boolean locationRequired, String experimentType, String name, String userName) {
        this.description = description;
        this.isEnded = isEnded;
        this.isPublished = isPublished;
        this.minTrials = minTrials;
        this.locationRequired = locationRequired;
        this.experimentType = experimentType;
        this.name = name;
        this.userName = userName;
    }

    /**
     * This set trials
     */
    public void setTrials(ArrayList<Trial> trials) {
        this.trials = trials;
    }

    /**
     * This set owner
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * This set participants
     */
    public void setParticipants(ArrayList<User> participants) {
        this.participants = participants;
    }

    /**
     * This set description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This set whether ended
     */
    public void setEnded(Boolean ended) {
        isEnded = ended;
    }

    /**
     * This set whether published
     */
    public void setPublished(Boolean published) {
        isPublished = published;
    }

    /**
     * This set trials number
     */
    public void setMinTrials(Integer minTrials) {
        this.minTrials = minTrials;
    }

    /**
     * This set whether location is required
     */
    public void setLocationRequired(Boolean locationRequired) {
        this.locationRequired = locationRequired;
    }

    /**
     * This set the type of experiments
     */
    public void setExperimentType(String experimentType) {
        this.experimentType = experimentType;
    }

    /**
     * This set the status of experiments
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This returns trials list
     * @return
     *      Return list of trials
     */
    public ArrayList<Trial> getTrials() {
        return trials;
    }

    /**
     * This returns owner
     * @return
     *      Return owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     * This returns participants
     * @return
     *      Return participants
     */
    public ArrayList<User> getParticipants() {
        return participants;
    }

    /**
     * This returns description
     * @return
     *      Return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * This returns whether ended
     * @return
     *      Return whether ended
     */
    public Boolean getEnded() {
        return isEnded;
    }

    /**
     * This returns whether published
     * @return
     *      Return whether published
     */
    public Boolean getPublished() {
        return isPublished;
    }

    /**
     * This returns minimal trials
     * @return
     *      Return minimal trials
     */
    public long getMinTrials() {
        return minTrials;
    }

    /**
     * This returns whether location required
     * @return
     *      Return whether location required
     */
    public Boolean getLocationRequired() {
        return locationRequired;
    }

    /**
     * This returns type
     * @return
     *      Return type
     */
    public String getExperimentType() {
        return experimentType;
    }

    /**
     * This returns status
     * @return
     *      Return status
     */
    public String getStatus() {
        if (isEnded == true) {
            return "Ended";
        } else {
            if (isPublished == false) {
                return "Unpublished";
            } else {
                return "Published";
            }
        }
    }

    /**
     * This returns name
     * @return
     *      Return name
     */
    public String getName() {
        return name;
    }


    /**
     * This returns user name
     * @return
     *      Return user name
     */
    public String getUserName() {
        return userName;
    }
}
