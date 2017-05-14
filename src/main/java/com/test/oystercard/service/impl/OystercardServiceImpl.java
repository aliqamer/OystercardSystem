package com.test.oystercard.service.impl;

import com.test.oystercard.bean.UserDetails;
import com.test.oystercard.constants.StationDetails;
import com.test.oystercard.service.OystercardService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.util.stream.Collectors.toList;

/**
 * Created by Ali on 5/14/2017.
 */
public class OystercardServiceImpl implements OystercardService {

    private StationDetails stationDetails = new StationDetails();
    private static DecimalFormat df2 = new DecimalFormat(".##");


    /**
     * This method takes userDetails as input, validate users journey, calculate users remaining balance based on
     * user's journey in different zones.
     * @param userDetails
     * @return remaining balance
     */
    @Override
    public double calcualateBalance(UserDetails userDetails) {

        double balance = userDetails.getBalance();

        List<String> trips = userDetails.getTrips();

        List<String> validTrips = new ArrayList<>();

        List<Double> fareDetails = trips.stream().filter(trip -> isValidateTrip(trip.trim())).map(trip -> {

            validTrips.add(trip);
            if(trip.contains("bus")){
                return stationDetails.getZoneFareByZone("bus");
            }
            String from = getFromStation(trip.trim());
            String to = getToStation(trip.trim());
            double fare = getFareForJourney(from, to);

            return fare;
        }).collect(toList());


        balance = getRemainingBalance(balance, trips, validTrips, fareDetails);

        return parseDouble(df2.format(balance));
    }

    /**
     * This method takes fromStation & toStation as input and returns fares based on zone
     * @param fromStation
     * @param toStation
     * @return fare for single given journey
     */
    protected double getFareForJourney(String fromStation, String toStation) {

        double maxFare = 3.2;
        if(toStation.length() == 0){
            return maxFare;
        }
        double fare = 0;

        List<Integer> fromStationZones = stationDetails.getZonesByStation(fromStation.toLowerCase());
        List<Integer> toStationZones = stationDetails.getZonesByStation(toStation.toLowerCase());
        int fromZone = 0;
        int toZone = 0;

        if(fromStationZones.size() > 1 && toStationZones.size() > 1) {

            if(fromStationZones.get(1) == toStationZones.get(1)){
                fromZone = fromStationZones.get(1);
                toZone = toStationZones.get(1);

            } else if(fromStationZones.get(1) == toStationZones.get(0)){
                fromZone = fromStationZones.get(1);
                toZone = toStationZones.get(0);

            } else if(fromStationZones.get(0) == toStationZones.get(1)){
                fromZone = fromStationZones.get(0);
                toZone = toStationZones.get(1);
            }

        }else if(fromStationZones.size() > 1 && toStationZones.size() == 1) {

            toZone = toStationZones.get(0);
            fromZone = getCloserZone(fromStationZones, toZone);

        } else if(fromStationZones.size() == 1 && toStationZones.size() > 1){

            fromZone = fromStationZones.get(0);
            toZone = getCloserZone(toStationZones, fromZone);
        } else {

            fromZone = fromStationZones.get(0);
            toZone = toStationZones.get(0);
        }

        fare = stationDetails.getZoneFareByZone(fromZone+","+toZone);

        return fare;
    }

    /**
     * This method takes list of zones for a given station and find closer zone to reduce customer's fare
     * @param zonesByStation
     * @param zone
     * @return closest zone to reduce fare
     */
    protected int getCloserZone(List<Integer> zonesByStation, Integer zone) {

        int diff = Integer.MAX_VALUE;
        int closeZone = zonesByStation.get(0);
        for(Integer z : zonesByStation) {
            if(Math.abs(zone - z) < diff){
                diff = Math.abs(zone - z);
                closeZone = z;
            }
        }
        return closeZone;
    }

    /**
     * This method takes users single trip as string, parse it to find to-Station
     * @param trip
     * @return toStation
     */
    protected String getToStation(String trip) {

        String toStation = null;

        if(trip.trim().indexOf("to")+2 == trip.trim().length()){
            return "";
        }
        if(trip.startsWith("Tube")) {
            toStation = trip.substring(trip.indexOf("to")+3, trip.length());

        }else if(trip.contains("bus")) {
            toStation = trip.substring(trip.indexOf("to")+3,trip.length());
        }
        return toStation;
    }

    /**
     * This method takes users single trip as string, parse it to find from-Station
     * @param trip
     * @return fromStation
     */
    protected String getFromStation(String trip) {

        String fromStation = null;
        if(trip.startsWith("Tube")) {
            fromStation = trip.substring(5, trip.indexOf("to")).trim();

        }else if(trip.contains("bus")) {
            fromStation = trip.substring(trip.indexOf("from")+5,trip.indexOf("to")).trim();
        }
        return fromStation;
    }

    /**
     * This method validate given trip details
     * @param trip
     * @return true if given trip is valid else false
     */
    protected boolean isValidateTrip(String trip) {

        boolean isValid = false;
        if(trip.startsWith("Tube") && trip.contains("to")){
            String fromStation = trip.substring(5, trip.indexOf("to")).trim();
            if(stationDetails.getZonesByStation(fromStation.toLowerCase()) != null){
                isValid = isValidToStation(trip);
            }
        }else if(trip.contains("bus") && trip.contains("from") && trip.contains("to")) {

            String busNumber = trip.substring(0, trip.indexOf(" "));
            try{
                int busN = Integer.parseInt(busNumber);
                if(busN > 999 && busN < 1){
                    return false;
                }
            }catch (Exception e){
                return false;
            }
            String bus = trip.substring(trip.indexOf(" "), trip.indexOf("from")).trim();
            if("bus".equalsIgnoreCase(bus)){
                String from = trip.substring(trip.indexOf("bus")+3,trip.indexOf("from")+4).trim();
                if("from".equalsIgnoreCase(from)){
                    String fromStation = trip.substring(trip.indexOf("from")+5,trip.indexOf("to")).trim();
                    if(stationDetails.getZonesByStation(fromStation.toLowerCase()) != null){

                        String to = trip.substring(trip.indexOf(fromStation)+fromStation.length()+1,trip.indexOf(fromStation)+fromStation.length()+3);
                        if("to".equalsIgnoreCase(to)){

                            isValid = isValidToStation(trip);
                        }
                    }
                }
            }
        }

        return isValid;
    }

    /**
     * This method takes user's total balance and calculate remaining balance based on user's valid journey
     * @param balance
     * @param trips
     * @param validTrips
     * @param fareDetails
     * @return
     */
    private double getRemainingBalance(double balance, List<String> trips, List<String> validTrips, List<Double> fareDetails) {
        int size = trips.size();
        for (int i = 0, j = 0; i < size; i++) {
            if(validTrips.contains(trips.get(i))){
                System.out.println("Fare for trip -> "+trips.get(i) + " = "+fareDetails.get(j));
                balance -= fareDetails.get(j++);
            }else{
                System.out.println("Invalid trip -> "+trips.get(i));
            }

        }
        return balance;
    }


    private boolean isValidToStation(String trip) {

        boolean isValid = false;
        if(trip.trim().indexOf("to")+2 == trip.trim().length()){
            isValid = true;
        }else {
            String toStation = trip.substring(trip.indexOf("to") + 3, trip.length());
            if (stationDetails.getZonesByStation(toStation.toLowerCase()) != null) {
                isValid = true;
            }
        }
        return isValid;
    }
}
