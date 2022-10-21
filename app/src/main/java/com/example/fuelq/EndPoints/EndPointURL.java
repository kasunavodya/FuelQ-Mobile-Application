package com.example.fuelq.EndPoints;

public class EndPointURL {

    public static final String endPointURL = "192.168.1.5:5000";

    public static final String GET_ALL_CUSTOMER = "http://" + endPointURL + "/api/Customer";
    public static final String GET_ALL_OWNER = "http://" + endPointURL + "/api/Owner";
    public static final String GET_ALL_FUEL = "http://" + endPointURL + "/api/Fuel";
    public static final String GET_OWNER_BY_ID = "http://" + endPointURL + "/api/Owner/63527ddae520aa4781a6f10d";
    public static final String UPDATE_FUEL_BY_ID = "http://" + endPointURL + "/api/Fuel/63526fcce77068ba135982e0";
}
