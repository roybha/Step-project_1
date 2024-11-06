package org.example;

import java.time.LocalDateTime;
import java.util.List;

public class Booking implements HasID{
    private int id;
    @Override
    public int getID() {
        return id;
    }
    @Override
    public void setID(int id) {
        this.id = id;
    }

    private Flight flight;
    public Flight getFlight() {
        return flight;
    }
    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    private List<Passenger> passengers;
    public List<Passenger> getPassengers() {
        return passengers;
    }
    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }
    private LocalDateTime bookingDate;
    public LocalDateTime getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }
    private boolean isActive;
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Booking(int id, Flight flight, List<Passenger> passengers) {
        this.id = id;
        this.flight = flight;
        this.passengers = passengers;
        this.bookingDate = LocalDateTime.now();
        this.isActive = true;
    }
}
