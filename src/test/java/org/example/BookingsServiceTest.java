package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingsServiceTest {
    private BookingsService bookingsService;

    @BeforeEach
    void setUp() {
        bookingsService = new BookingsService();
    }

    @Test
    void testAddNewBooking() {
        // Arrange
        Flight flight = new Flight(1, LocalDateTime.now(),LocalDateTime.now().plusHours(3), 200, 180,"Стокгольм","Київ",new ArrayList<>());
        List<Passenger> passengers = List.of(new Passenger(1, "Олаф", "Стрюдсхольм"));

        // Act
        Booking newBooking = bookingsService.addNewBooking(flight, passengers);

        // Assert
        assertNotNull(newBooking);
        assertEquals(1, newBooking.getID());
        assertEquals(flight, newBooking.getFlight());
        assertEquals(passengers, newBooking.getPassengers());
        assertEquals(1, bookingsService.getBookings().size());
    }

    @Test
    void testGetBookings() {
        // Act
        List<Booking> bookings = bookingsService.getBookings();

        // Assert
        assertNotNull(bookings);
        assertEquals(0, bookings.size()); // На початку немає бронювань

        // Arrange
        Flight flight = new Flight(1, LocalDateTime.now(),LocalDateTime.now().plusHours(3), 200, 180,"Стокгольм","Київ",new ArrayList<>());
        List<Passenger> passengers = List.of(new Passenger(1, "John", "Doe"));
        bookingsService.addNewBooking(flight, passengers);

        // Act
        bookings = bookingsService.getBookings();

        // Assert
        assertEquals(1, bookings.size());
    }

    @Test
    void testGenerateBookingsList() {
        // Arrange
        Flight flight = new Flight(1, LocalDateTime.now(),LocalDateTime.now().plusHours(3), 200, 180,"Стокгольм","Київ",new ArrayList<>()); // 2 місця заброньовані
        flight.setBookings(bookingsService.generateBookingsList(flight));
        List<Booking> generatedBookings = flight.getBookings();

        // Act & Assert
        assertEquals(flight.getGlobalSeats()-flight.getAvailableSeats(), generatedBookings.size());
        assertEquals(flight.getGlobalSeats()-flight.getAvailableSeats(), bookingsService.getBookings().size());
    }

    @Test
    void testGetBookingById() {
        // Arrange
        Flight flight = new Flight(1, LocalDateTime.now(),LocalDateTime.now().plusHours(3), 200, 180,"Стокгольм","Київ",new ArrayList<>());
        List<Passenger> passengers = List.of(new Passenger(1, "John", "Doe"));
        Booking newBooking = bookingsService.addNewBooking(flight, passengers);

        // Act
        Booking foundBooking = bookingsService.getBookingById(newBooking.getID());

        // Assert
        assertNotNull(foundBooking);
        assertEquals(newBooking.getID(), foundBooking.getID());
        assertEquals(newBooking.getFlight(), foundBooking.getFlight());
    }

    @Test
    void testDeleteBooking() {
        // Arrange
        Flight flight = new Flight(1, LocalDateTime.now(),LocalDateTime.now().plusHours(3), 200, 180,"Стокгольм","Київ",new ArrayList<>());
        List<Passenger> passengers = List.of(new Passenger(1, "John", "Doe"));
        Booking newBooking = bookingsService.addNewBooking(flight, passengers);

        // Act
        boolean isDeleted = bookingsService.deleteBooking(newBooking.getID());

        // Assert
        assertTrue(isDeleted);
        assertNull(bookingsService.getBookingById(newBooking.getID()));
        assertEquals(0, bookingsService.getBookings().size());
    }
}
