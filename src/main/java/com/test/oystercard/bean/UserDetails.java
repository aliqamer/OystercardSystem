package com.test.oystercard.bean;

import java.util.List;

/**
 * Created by Ali on 5/14/2017.
 */
public class UserDetails {

    private String userName;
    private Double balance;
    private List<String> trips;

    public List<String> getTrips() {
        return trips;
    }

    public void setTrips(List<String> trips) {
        this.trips = trips;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
