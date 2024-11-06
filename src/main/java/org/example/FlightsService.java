package org.example;

import java.util.ArrayList;

public class FlightsService {
    private FlightsCollectionDAO flightsCollectionDAO;
    public FlightsService () {
        flightsCollectionDAO = new FlightsCollectionDAO(new ArrayList<>());
    }
    public FlightsCollectionDAO getFlights(){
        return flightsCollectionDAO;
    }
    public void displayAllFlights(){
         flightsCollectionDAO.getAll().stream().forEach(System.out::println);
    }
}
