package com.example.fuelq;

public class Customer {
    private final int customerID;
    private final String customerEmail;
    private final String customerPw;
    private final int customerVehNumber;
    private final String customerVehType;
    private final String customerFuelType;

    public Customer(int customerID, String customerEmail, String customerPw, int customerVehNumber, String customerVehType, String customerFuelType) {
        this.customerID = customerID;
        this.customerEmail = customerEmail;
        this.customerPw = customerPw;
        this.customerVehNumber = customerVehNumber;
        this.customerVehType = customerVehType;
        this.customerFuelType = customerFuelType;
    }
}
