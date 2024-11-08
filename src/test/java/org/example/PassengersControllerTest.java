package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PassengersControllerTest {

    private PassengersController passengersController;
    private Flight flight;

    @BeforeEach
    void setUp() {
        passengersController = new PassengersController();

        // Ініціалізація рейсу з порожнім списком бронювань
        flight = new Flight(13, LocalDateTime.now(),LocalDateTime.now().plusHours(3),50,50,"Київ","Рига", List.of(new Booking(1,flight,new ArrayList<>())));
    }

    @Test
    void testFormNewPassengersForFlightNotBooked() {
        List<String> names = List.of("Alice");
        List<String> surnames = List.of("Johnson");

        // Викликаємо метод для створення нових пасажирів
        Optional<List<Passenger>> result = passengersController.formNewPassengersForFlight(names, surnames, flight);

        assertTrue(result.isPresent(), "Повинні створитися нові пасажири, якщо вони ще не заброньовані");
        assertEquals(1, result.get().size(), "Повинна створитися одна запис для пасажира");
        assertEquals("Alice", result.get().get(0).getFirstName(), "Ім'я першого пасажира повинно бути Alice");
        assertEquals("Johnson", result.get().get(0).getLastName(), "Прізвище першого пасажира повинно бути Johnson");
    }

    @Test
    void testFormNewPassengersForFlightAlreadyBooked() {
        List<String> names = List.of("Bob");
        List<String> surnames = List.of("Smith");

        // Додаємо бронювання на цього пасажира у рейс
        List<Passenger> bookedPassengers = new ArrayList<>();
        bookedPassengers.add(new Passenger(1, names.getFirst(), surnames.getFirst()));
        flight.setBookings(List.of(new Booking(flight.getBookings().size()+1,flight,bookedPassengers)));

        // Спроба додати вже існуючого пасажира
        try {
            Optional<List<Passenger>> result = passengersController.formNewPassengersForFlight(names, surnames, flight);
            assertTrue(result.isEmpty(), "Якщо пасажир вже заброньований, не повинні створюватися нові пасажири");
        }
        catch (WrongInputException e){
            assertTrue(!equals(null));
        }



    }

    @Test
    void testAddPassengers() {
        List<Passenger> passengersToAdd = List.of(
                new Passenger(1, "John", "Doe"),
                new Passenger(2, "Jane", "Doe")
        );

        // Викликаємо метод для додавання пасажирів
        passengersController.addPassengers(passengersToAdd);

        // Перевіряємо, чи пасажири додані у сервіс
        List<Passenger> allPassengers = passengersController.getPassengers();
        assertTrue(allPassengers.containsAll(passengersToAdd), "Всі додані пасажири повинні бути в колекції");
    }

    @Test
    void testRemovePassengers() {
        List<Passenger> passengersToAdd = List.of(
                new Passenger(1, "John", "Doe"),
                new Passenger(2, "Jane", "Doe")
        );

        // Додаємо пасажирів
        passengersController.addPassengers(passengersToAdd);

        // Видаляємо пасажирів
        passengersController.removePassengers(passengersToAdd);

        // Перевіряємо, чи пасажири були видалені
        List<Passenger> allPassengers = passengersController.getPassengers();
        assertFalse(allPassengers.containsAll(passengersToAdd), "Всі видалені пасажири не повинні бути в колекції");
    }
}

