package com.test.oystercard.constants;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Ali on 5/14/2017.
 */
public class StationDetailsTest {

    private StationDetails stationDetails;

    @Before
    public void setUp() throws Exception {

        stationDetails = new StationDetails();

    }

    @Test
    public void getStationsZone_ShouldReturnStationsWithZoneDetails(){

        Map<String, List<Integer>> stationsZone = stationDetails.getStationsZone();

        assertNotNull(stationsZone);
        assertTrue(stationsZone.containsKey("Hammersmith"));
        assertTrue(stationsZone.containsKey("Holborn"));
        assertTrue(stationsZone.containsKey("Wimbledon"));
        assertTrue(stationsZone.containsKey("Earl's Court"));

    }

    @Test
    public void getZonesByStation_ShouldReturnValidZonesForGivenStation() throws Exception {

        List<Integer> actualZones = stationDetails.getZonesByStation("Earl's Court");

        ArrayList<Integer> expectedHolbornZone = new ArrayList<>();
        expectedHolbornZone.add(1);
        expectedHolbornZone.add(2);
        assertEquals(actualZones, expectedHolbornZone);

    }

    @Test
    public void getZoneFares_ShouldReturnAllZonesFare() throws Exception {

        Map<String, Double> zoneFares = stationDetails.getZoneFares();

        assertNotNull(zoneFares);
        assertTrue(zoneFares.containsKey("1"));
        assertTrue(zoneFares.containsKey("2"));
        assertTrue(zoneFares.containsKey("1,2,3"));

    }

    @Test
    public void getZoneFares_ShouldReturnZoneFareForGivenZoneJourney() throws Exception {

        Double fare = stationDetails.getZoneFareByZone("1,2");

        assertTrue(fare == 3.0);

    }
}