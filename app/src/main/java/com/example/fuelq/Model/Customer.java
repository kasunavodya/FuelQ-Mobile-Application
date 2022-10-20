package com.example.fuelq.Model;

public class Customer {
    String customerEmail;
    String customerPassword;
    int customerVehicleNumber;
    String customerVehicleType;
    String customerFuelType;

    public Customer(String customerEmail, String customerPassword, int customerVehicleNumber, String customerVehicleType, String customerFuelType) {
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
        this.customerVehicleNumber = customerVehicleNumber;
        this.customerVehicleType = customerVehicleType;
        this.customerFuelType = customerFuelType;
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
}
