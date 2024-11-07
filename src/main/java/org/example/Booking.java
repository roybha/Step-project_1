package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class Booking implements HasID{
    private int id;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Booking booking = (Booking) obj;

        return id == booking.id &&
                Objects.equals(flight, booking.flight) &&
                Objects.equals(passengers, booking.passengers) &&
                Objects.equals(bookingDate, booking.bookingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flight, passengers, bookingDate);
    }

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
    private LocalDateTime bookingDate;
    public LocalDateTime getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }
    public Booking(int id, Flight flight, List<Passenger> passengers) {
        this.id = id;
        this.flight = flight;
        this.passengers = passengers;
        this.bookingDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String passengersInfo = passengers.stream()
                .map(passenger -> passenger.getFirstName() + " " + passenger.getLastName())
                .reduce((p1, p2) -> p1 + ", " + p2)
                .orElse("Немає пасажирів");

        return "Бронювання № " + id +
                "\nРейс № " + flight.getID() +
                "\nПасажири: " + passengersInfo +
                "\nДата бронювання: " + bookingDate.format(formatter);
    }
}
