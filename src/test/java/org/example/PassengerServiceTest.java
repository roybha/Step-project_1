package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PassengerServiceTest {
    private PassengerService passengerService;
    private Flight flight;

    @BeforeEach
    void setUp() {
        passengerService = new PassengerService();
        flight = new Flight(0, LocalDateTime.now(),LocalDateTime.now().plusHours(2),50,50,"Київ","Рига", List.of(new Booking(1,flight,new ArrayList<>())));
    }

    @Test
    void testGetPassengersInitiallyEmpty() {
        assertTrue(passengerService.getPassengers().getAll().isEmpty(), "Список пасажирів повинен бути порожнім");
    }

    @Test
    void testAddPassengers() {
        List<Passenger> passengersToAdd = List.of(
                new Passenger(1, "John", "Doe"),
                new Passenger(2, "Jane", "Doe")
        );

        passengerService.addPassengers(passengersToAdd);

        List<Passenger> passengers = passengerService.getPassengers().getAll();
        assertEquals(2, passengers.size(), "Кількість пасажирів після додавання повинна дорівнювати 2");
        assertTrue(passengers.containsAll(passengersToAdd), "Додані пасажири повинні бути в списку");
    }

    @Test
    void testRemovePassengers() {
        Passenger passenger1 = new Passenger(1, "John", "Doe");
        Passenger passenger2 = new Passenger(2, "Jane", "Doe");

        List<Passenger> passengersToAdd = List.of(passenger1, passenger2);
        passengerService.addPassengers(passengersToAdd);

        passengerService.removePassengers(List.of(passenger1));

        List<Passenger> remainingPassengers = passengerService.getPassengers().getAll();
        assertEquals(1, remainingPassengers.size(), "Кількість пасажирів після видалення повинна дорівнювати 1");
        assertFalse(remainingPassengers.contains(passenger1), "Пасажир, якого видалили, не повинен бути в списку");
        assertTrue(remainingPassengers.contains(passenger2), "Другий пасажир повинен залишитися в списку");
    }

    @Test
    void testFormNewPassengersForFlightNotBooked() {
        List<String> names = List.of("Аліса");
        List<String> surnames = List.of("Юрцевич");

        Optional<List<Passenger>> result = passengerService.formNewPassengersForFlight(names, surnames, flight);
        assertTrue(result.isPresent(), "Повинні створитися нові пасажири, якщо вони ще не зарезервовані");

        List<Passenger> newPassengers = result.get();
        assertEquals(1, newPassengers.size(), "Має бути створений один новий пасажир");
        assertEquals("Аліса", newPassengers.get(0).getFirstName(), "Ім'я нового пасажира має бути 'Alice'");
        assertEquals("Юрцевич", newPassengers.get(0).getLastName(), "Прізвище нового пасажира має бути 'Johnson'");
    }

    @Test
    void testFormNewPassengersForFlightAlreadyBooked() {
        Passenger bookedPassenger = new Passenger(1, "Bob", "Smith");
        Booking booking = new Booking(flight.getBookings().size()+1,flight,List.of(bookedPassenger));
        flight.setBookings(List.of(booking));
        flight.setAvailableSeats(flight.getGlobalSeats()-flight.getBookings().size());

        List<String> names = List.of("Bob");
        List<String> surnames = List.of("Smith");

        try {
            Optional<List<Passenger>> result = passengerService.formNewPassengersForFlight(names, surnames, flight);
            assertTrue(result.isEmpty(), "Якщо пасажир вже заброньований, не повинні створюватися нові пасажири");
        }catch (WrongInputException e){
            assertTrue(!e.equals(null));
        }

    }
}