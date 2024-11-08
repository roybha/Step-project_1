package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookingsControllerTest {
    private BookingsController bookingsController;

    @BeforeEach
    void setUp() {
        bookingsController = new BookingsController();
    }

    @Test
    void testGetBookings() {
        // Act
        List<Booking> bookings = bookingsController.getBookings();

        // Assert
        assertNotNull(bookings);
        assertEquals(0, bookings.size()); // Спочатку немає бронювань

        // Arrange
        Flight flight = new Flight(1, LocalDateTime.now(),LocalDateTime.now().plusHours(3), 200, 180,"Стокгольм","Київ",new ArrayList<>());
        List<Passenger> passengers = List.of(new Passenger(1, "John", "Doe"));
        bookingsController.addNewBooking(flight, passengers);

        // Act
        bookings = bookingsController.getBookings();

        // Assert
        assertEquals(1, bookings.size()); // Після додавання бронювання кількість повинна бути 1
    }

    @Test
    void testAddNewBooking() {
        // Arrange
        Flight flight = new Flight(1, LocalDateTime.now(),LocalDateTime.now().plusHours(3), 200, 180,"Стокгольм","Київ",new ArrayList<>());
        List<Passenger> passengers = List.of(new Passenger(1, "John", "Doe"));

        // Act
        Booking newBooking = bookingsController.addNewBooking(flight, passengers);

        // Assert
        assertNotNull(newBooking);
        assertEquals(1, newBooking.getID());
        assertEquals(flight, newBooking.getFlight());
        assertEquals(passengers, newBooking.getPassengers());
        assertEquals(1, bookingsController.getBookings().size());
    }

    @Test
    void testGenerateSomeBookingsForFlight() {
        // Arrange
        Flight flight = new Flight(1, LocalDateTime.now(),LocalDateTime.now().plusHours(3), 200, 180,"Стокгольм","Київ",new ArrayList<>()); // 2 місця вже заброньовані
        List<Booking> generatedBookings = bookingsController.generateSomeBookingsForFlight(flight);

        // Act & Assert
        assertEquals(flight.getGlobalSeats()-flight.getAvailableSeats(), generatedBookings.size()); // Має бути 2 бронювання
        assertEquals(flight.getGlobalSeats()-flight.getAvailableSeats(), bookingsController.getBookings().size());
    }

    @Test
    void testGetBookingById() {
        // Arrange
        Flight flight = new Flight(1, LocalDateTime.now(),LocalDateTime.now().plusHours(3), 200, 180,"Стокгольм","Київ",new ArrayList<>());
        List<Passenger> passengers = List.of(new Passenger(1, "John", "Doe"));
        Booking newBooking = bookingsController.addNewBooking(flight, passengers);

        // Act
        Optional<Booking> foundBooking = bookingsController.getBookingById(newBooking.getID());

        // Assert
        assertTrue(foundBooking.isPresent());
        assertEquals(newBooking.getID(), foundBooking.get().getID());
        assertEquals(newBooking.getFlight(), foundBooking.get().getFlight());
    }

    @Test
    void testDeleteBooking() {
        // Arrange
        Flight flight = new Flight(1, LocalDateTime.now(),LocalDateTime.now().plusHours(3), 200, 180,"Стокгольм","Київ",new ArrayList<>());
        List<Passenger> passengers = List.of(new Passenger(1, "John", "Doe"));
        Booking newBooking = bookingsController.addNewBooking(flight, passengers);

        // Act
        boolean isDeleted = bookingsController.deleteBooking(newBooking.getID());

        // Assert
        assertTrue(isDeleted);
        assertEquals(0, bookingsController.getBookings().size());
        assertTrue(bookingsController.getBookingById(newBooking.getID()).isEmpty());
    }
}