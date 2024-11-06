package org.example;

public class FlightsController {
    FlightsService flightsService;

    public FlightsService getFlightsService() {
        return flightsService;
    }
    public FlightsController() {
        flightsService = new FlightsService();
    }
}
