package org.example;

import java.time.LocalDateTime;
import java.util.*;

public class BookingsController {
    private  BookingsService bookingsService;
    public BookingsService getBookingsService() {
        return bookingsService;
    }
    public BookingsController() {
        bookingsService = new BookingsService();
    }
    public List<Booking> getBookings() {
        return bookingsService.getBookings();
    }
    public Booking addNewBooking(Flight flight,List<Passenger> passengers) {
         return bookingsService.addNewBooking(flight,passengers);
    }
    public List<Booking> generateSomeBookingsForFlight(Flight flight) {
        return bookingsService.generateBookingsList(flight);
    }
    public List<Booking> getBookingsForFlight(Flight flight) {
        return bookingsService.getBookingsForFlight(flight);
    }
    public Optional<Booking> getBookingById(int id) {
        return Optional.ofNullable(bookingsService.getBookingById(id));
    }
    public boolean deleteBooking(int id) {
        return bookingsService.deleteBooking(id);
    }
    public void saveToFile(String filename) {
        bookingsService.saveToFile(filename);
    }
    public void loadFromFile(String filename) {
        bookingsService.loadFromFile(filename);
    }
}

