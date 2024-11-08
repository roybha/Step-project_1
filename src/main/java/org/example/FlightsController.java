package org.example;

import java.util.List;

public class FlightsController {
    private FlightsService flightsService;

    public List<Flight> getAllFlights() {
        return flightsService.getFlights();
    }
    public FlightsController() {
        flightsService = new FlightsService();
    }
    public void displayAllFlights() {
        flightsService.displayAllFlights();
    }
    public void displayAllFlightsFromCity(String city) {
        flightsService.displayAllFlightsFromCity(city);
    }
    public  List<Booking> getBookingsForFlight(Flight flight) {
        return flightsService.getFlightBookings(flight);
    }
    public Flight getFlightById(int id) {
        return flightsService.getFlightById(id);
    }
    public Booking getFlightBookingById(int id) {
        return flightsService.getFlightBookingById(id);
    }
    public void removeFlightBookingById(int bookingID) {
        flightsService.removeFlightBookingById(bookingID);
    }
    public void increaseAvailableSeatsByBookingId(int bookingID) {
        flightsService.increaseAvailableSeatsByBookingId(bookingID);
    }
    public boolean isThereSuchFlightByID(int flightID) {
        return flightsService.isThereSuchFlightById(flightID);
    }
}
