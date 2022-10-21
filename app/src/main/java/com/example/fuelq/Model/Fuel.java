package com.example.fuelq.Model;

public class Fuel {
    String fuelType;
    String fuelStation;
    String arrivingDate;
    String arrivingTime;
    String arrivedLitres;
    String remainLitres;

    public Fuel(String fuelType, String fuelStation, String arrivingDate, String arrivingTime, String arrivedLitres, String remainLitres) {
        this.fuelType = fuelType;
        this.fuelStation = fuelStation;
        this.arrivingDate = arrivingDate;
        this.arrivingTime = arrivingTime;
        this.arrivedLitres = arrivedLitres;
        this.remainLitres = remainLitres;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getFuelStation() {
        return fuelStation;
    }

    public void setFuelStation(String fuelStation) {
        this.fuelStation = fuelStation;
    }

    public String getArrivingDate() {
        return arrivingDate;
    }

    public void setArrivingDate(String arrivingDate) {
        this.arrivingDate = arrivingDate;
    }

    public String getArrivingTime() {
        return arrivingTime;
    }

    public void setArrivingTime(String arrivingTime) {
        this.arrivingTime = arrivingTime;
    }

    public String getArrivedLitres() {
        return arrivedLitres;
    }

    public void setArrivedLitres(String arrivedLitres) {
        this.arrivedLitres = arrivedLitres;
    }

    public String getRemainLitres() {
        return remainLitres;
    }

    public void setRemainLitres(String remainLitres) {
        this.remainLitres = remainLitres;
    }
}
