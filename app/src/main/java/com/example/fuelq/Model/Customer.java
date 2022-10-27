/*
  ---------------------
  Model: Customer.java
  ---------------------
*/

package com.example.fuelq.Model;

public class Customer {
    String id;
    String customerEmail;
    String customerPassword;
    int customerVehicleNumber;
    String customerVehicleType;
    String customerFuelType;
    String awaitingTime;
    int token;
    String arrivalTimeQ;
    String departTimeQ;
    int requestedLitres;
    String shedQ;

    public Customer() {
    }

    public Customer(String id, String customerEmail, String customerPassword, int customerVehicleNumber, String customerVehicleType, String customerFuelType, String awaitingTime, int token, String arrivalTimeQ, String departTimeQ, int requestedLitres, String shedQ) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
        this.customerVehicleNumber = customerVehicleNumber;
        this.customerVehicleType = customerVehicleType;
        this.customerFuelType = customerFuelType;
        this.awaitingTime = awaitingTime;
        this.token = token;
        this.arrivalTimeQ = arrivalTimeQ;
        this.departTimeQ = departTimeQ;
        this.requestedLitres = requestedLitres;
        this.shedQ = shedQ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public int getCustomerVehicleNumber() {
        return customerVehicleNumber;
    }

    public void setCustomerVehicleNumber(int customerVehicleNumber) {
        this.customerVehicleNumber = customerVehicleNumber;
    }

    public String getCustomerVehicleType() {
        return customerVehicleType;
    }

    public void setCustomerVehicleType(String customerVehicleType) {
        this.customerVehicleType = customerVehicleType;
    }

    public String getCustomerFuelType() {
        return customerFuelType;
    }

    public void setCustomerFuelType(String customerFuelType) {
        this.customerFuelType = customerFuelType;
    }

    public String getAwaitingTime() {
        return awaitingTime;
    }

    public void setAwaitingTime(String awaitingTime) {
        this.awaitingTime = awaitingTime;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getArrivalTimeQ() {
        return arrivalTimeQ;
    }

    public void setArrivalTimeQ(String arrivalTimeQ) {
        this.arrivalTimeQ = arrivalTimeQ;
    }

    public String getDepartTimeQ() {
        return departTimeQ;
    }

    public void setDepartTimeQ(String departTimeQ) {
        this.departTimeQ = departTimeQ;
    }

    public int getRequestedLitres() {
        return requestedLitres;
    }

    public void setRequestedLitres(int requestedLitres) {
        this.requestedLitres = requestedLitres;
    }

    public String getShedQ() {
        return shedQ;
    }

    public void setShedQ(String shedQ) {
        this.shedQ = shedQ;
    }
}