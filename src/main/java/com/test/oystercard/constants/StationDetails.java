package com.test.oystercard.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ali on 5/14/2017.
 * This class stores stations , zones & fare details
 */
public class StationDetails {

    private static Map<String, List<Integer>> stationsZone = new HashMap<>();

    private static Map<String, Double> zoneFares = new HashMap<>();

    static {
        //below station, zone and fare details are loaded at start
        List<Integer> zones = new ArrayList<>();
        zones.add(1);
        stationsZone.put("holborn", zones);
        zones = new ArrayList<>();
        zones.add(1);
        zones.add(2);
        stationsZone.put("earl's court".toLowerCase(), zones);
        zones = new ArrayList<>();
        zones.add(2);
        stationsZone.put("hammersmith",zones);
        zones = new ArrayList<>();
        zones.add(3);
        stationsZone.put("wimbledon",zones);
        zones = new ArrayList<>();
        zones.add(1);
        stationsZone.put("chelsea",zones);

        zoneFares.put("1,1",2.5);
        zoneFares.put("2,2",2.0);
        zoneFares.put("3,3",2.0);
        zoneFares.put("1,2",3.0);
        zoneFares.put("2,1",3.0);
        zoneFares.put("2,3",2.25);
        zoneFares.put("3,2",2.25);
        zoneFares.put("1,3",3.2);
        zoneFares.put("3,1",3.2);
        zoneFares.put("bus",1.80);

    }

    public Map<String , List<Integer>> getStationsZone(){
        return stationsZone;
    }

    public List<Integer> getZonesByStation(String station){
        return stationsZone.get(station);
    }

    public Map<String, Double> getZoneFares(){
        return zoneFares;
    }

    public Double getZoneFareByZone(String zone){
        return zoneFares.get(zone);
    }

}
