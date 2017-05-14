package com.test.oystercard.service.impl;

import com.test.oystercard.bean.UserDetails;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Ali on 5/14/2017.
 */
public class OystercardServiceImplTest {

    private OystercardServiceImpl oystercardServiceImpl = new OystercardServiceImpl();

    private UserDetails userDetails = new UserDetails();

    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void calcualateBalance_ShouldReturnRemainingBalanceForGivenTripDetailsInput1() throws Exception {

        List<String> listOfTrips = getListOfTrips("Tube Holborn to Earl's Court",
                "328 bus from Earl's Court to Chelsea",
                "Tube Holborn to ",
                "Tube Earl's court to Hammersmith");

        UserDetails userDetails = getUserDetails(10.0, listOfTrips);

        double actualBalance = oystercardServiceImpl.calcualateBalance(userDetails);

        double expectedBalance = 0.5;
        assertTrue(actualBalance == expectedBalance);
    }

    @Test
    public void calcualateBalance_ShouldReturnRemainingBalanceForGivenTripDetailsInput2() throws Exception {

        List<String> listOfTrips = getListOfTrips("Tube Wimbledon to Earl's Court",
                "328 bus from Earl's Court to Chelsea",
                "Tube Holborn to ",
                "Tube Earl's court to Holborn");

        UserDetails userDetails = getUserDetails(10.0, listOfTrips);

        double actualBalance = oystercardServiceImpl.calcualateBalance(userDetails);

        double expectedBalance = 0.25;
        assertTrue(actualBalance == expectedBalance);
    }

    @Test
    public void calcualateBalance_ShouldReturnRemainingBalanceForGivenTripDetailsInput3() throws Exception {

        List<String> listOfTrips = getListOfTrips("Tube Wimbledon to Earl's Court",
                "328 bus from Earl's Court to ",
                "Tube Earl's court to Earl's court",
                "Tube Earl's court to Hammersmith");

        UserDetails userDetails = getUserDetails(10.0, listOfTrips);

        double actualBalance = oystercardServiceImpl.calcualateBalance(userDetails);

        double expectedBalance = 1.95;
        assertTrue(actualBalance == expectedBalance);
    }

    @Test
    public void isValidateTrip_ShouldReturnFalseForInValidStringTokenFrom() throws Exception {

        boolean validTrip = oystercardServiceImpl.isValidateTrip("Tube Holborn from Earl's court");

        assertFalse(validTrip);
    }

    @Test
    public void isValidateTrip_ShouldReturnFalseForInValidFromStation() throws Exception {

        boolean validTrip = oystercardServiceImpl.isValidateTrip("Tube stratford to Earl's court");

        assertFalse(validTrip);
    }

    @Test
    public void isValidateTrip_ShouldReturnFalseForInValidToStation() throws Exception {

        boolean validTrip = oystercardServiceImpl.isValidateTrip("Tube holborn to oxford");

        assertFalse(validTrip);
    }

    @Test
    public void isValidateTrip_ShouldReturnTrueForValidTrip() throws Exception {

        boolean validateTrip = oystercardServiceImpl.isValidateTrip("Tube Holborn to Earl's Court");

        assertTrue(validateTrip);

    }

    @Test
    public void isValidateTrip_ShouldReturnTrueWithoutToStation() throws Exception {

        boolean validateTrip = oystercardServiceImpl.isValidateTrip("Tube Holborn to ");

        assertTrue(validateTrip);

    }

    @Test
    public void isValidateTrip_ShouldReturnTrueForValidBusTrip() throws Exception {

        boolean isValid = oystercardServiceImpl.isValidateTrip("328 bus from Holborn to Chelsea");

        assertTrue(isValid);

    }

    @Test
    public void isValidateTrip_ShouldReturnFalseForInValidBusNumber() throws Exception {

        boolean isValid = oystercardServiceImpl.isValidateTrip("bus bus from Holborn to Chelsea");

        assertFalse(isValid);

    }

    @Test
    public void isValidateTrip_ShouldReturnFalseForMissingBusToken() throws Exception {

        boolean isValid = oystercardServiceImpl.isValidateTrip("328 from Holborn to Chelsea");

        assertFalse(isValid);

    }

    @Test
    public void isValidateTrip_ShouldReturnFalseForMissingFromToken() throws Exception {

        boolean isValid = oystercardServiceImpl.isValidateTrip("328 bus Holborn to Chelsea");

        assertFalse(isValid);

    }

    @Test
    public void isValidateTrip_ShouldReturnFalseForMissingToToken() throws Exception {

        boolean isValid = oystercardServiceImpl.isValidateTrip("328 bus from Holborn Chelsea");

        assertFalse(isValid);

    }

    @Test
    public void isValidateTrip_ShouldReturnFalseForInvalidFromStation() throws Exception {

        boolean isValid = oystercardServiceImpl.isValidateTrip("328 bus from starford to Chelsea");

        assertFalse(isValid);

    }

    @Test
    public void isValidateTrip_ShouldReturnFalseForInvalidtoStation() throws Exception {

        boolean isValid = oystercardServiceImpl.isValidateTrip("328 bus from holborn to stratford");

        assertFalse(isValid);

    }

    @Test
    public void getFromStation_ShouldReturnValidFromStationForTube() throws Exception {

        String fromStation = oystercardServiceImpl.getFromStation("Tube Holborn to Earl's Court");

        assertEquals(fromStation, "Holborn");

    }

    @Test
    public void getFromStation_ShouldReturnValidFromStationForBus() throws Exception {

        String fromStation = oystercardServiceImpl.getFromStation("328 bus from Holborn to Chelsea");

        assertEquals(fromStation, "Holborn");

    }

    @Test
    public void getFromStation_ShouldReturnValidToStationForTube() throws Exception {

        String fromStation = oystercardServiceImpl.getToStation("Tube Holborn to Earl's Court");

        assertEquals(fromStation, "Earl's Court");

    }

    @Test
    public void getFromStation_ShouldReturnValidToStationForBus() throws Exception {

        String fromStation = oystercardServiceImpl.getToStation("328 bus from Holborn to Chelsea");

        assertEquals(fromStation, "Chelsea");

    }

    @Test
    public void getZoneWithLeastFare_ShouldReturnMaxZoneValue() throws Exception {

        ArrayList<Integer> zones = new ArrayList<>();
        zones.add(1);
        zones.add(2);
        int zoneWithLeastFare = oystercardServiceImpl.getCloserZone(zones,3);

        assertEquals(zoneWithLeastFare, 2);

    }

    @Test
    public void getFareForJourney_ShouldReturnValidFareForZone1To2() throws Exception {

        Double fareForJourney = oystercardServiceImpl.getFareForJourney("Holborn", "Hammersmith");

        assertTrue(fareForJourney == 3.0);

    }

    @Test
    public void getFareForJourney_ShouldReturnValidFareForZone3To2() throws Exception {

        Double fareForJourney = oystercardServiceImpl.getFareForJourney("Wimbledon", "Hammersmith");

        assertTrue(fareForJourney == 2.25);

    }

    @Test
    public void getFareForJourney_ShouldReturnValidFareForZone2To3() throws Exception {

        Double fareForJourney = oystercardServiceImpl.getFareForJourney("Hammersmith","Wimbledon");

        assertTrue(fareForJourney == 2.25);

    }

    @Test
    public void getFareForJourney_ShouldReturnValidFareWithoutToStation() throws Exception {

        Double fareForJourney = oystercardServiceImpl.getFareForJourney("Holborn","");

        assertTrue(fareForJourney == 3.20);

    }

    private List<String> getListOfTrips(String... trips) {
        List<String> listOfTrips = new ArrayList<>();
        for (int i = 0; i < trips.length; i++) {
            listOfTrips.add(trips[i]);
        }
        return listOfTrips;
    }

    private UserDetails getUserDetails(double totalBalance,List<String> trips){

        UserDetails userDetails = new UserDetails();
        userDetails.setTrips(trips);
        userDetails.setBalance(totalBalance);
        return userDetails;
    }
}