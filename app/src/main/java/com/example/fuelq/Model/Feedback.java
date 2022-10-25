package com.example.fuelq.Model;

public class Feedback {
    String starFeedback;
    String userFeedback;
    String station;

    public Feedback(String starFeedback, String userFeedback, String station) {
        this.starFeedback = starFeedback;
        this.userFeedback = userFeedback;
        this.station = station;
    }

    public String getStarFeedback() {
        return starFeedback;
    }

    public void setStarFeedback(String starFeedback) { this.starFeedback = starFeedback; }

    public String getUserFeedback() {
        return userFeedback;
    }

    public void setUserFeedback(String userFeedback) {
        this.userFeedback = userFeedback;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
