package org.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlightsServiceTest {
    private FlightsService flightsService;
    private BookingsController bookingsController;
    private InputOutputClass inputOutputClass;

    private Flight flight1;
    private Flight flight2;
    @BeforeEach
    public void setUp() {
        // Ініціалізація FlightsService перед кожним тестом
        flightsService = new FlightsService();
        inputOutputClass = new InputOutputClass();
        flightsService.loadFromFile("flights.dat");
        bookingsController = new BookingsController();
        flightsService.getFlights().forEach(flight -> flight.setBookings(bookingsController.generateSomeBookingsForFlight(flight)));
    }

    @Test
    public void testGetFlights() {
        // Перевірка, що метод повертає список рейсів
        List<Flight> flights = flightsService.getFlights();
        assertNotNull(flights, "Список рейсів не може бути null");
        assertTrue(flights.size() > 0, "Список рейсів має містити хоча б один рейс");
    }

    @Test
    public void testGetFlightById() {
        // Перевірка, що метод повертає правильний рейс за ID
        List<Flight> flights = flightsService.getFlights();
        int validId = flights.get(0).getID(); // Отримуємо ID першого рейсу
        Flight flight = flightsService.getFlightById(validId);
        assertNotNull(flight, "Рейс з таким ID не знайдений");

        // Перевірка, що ID співпадає
        assertEquals(validId, flight.getID(), "ID рейсу не співпадає");
    }

    @Test
    public void testGetFlightBookingById() {
        // Перевірка, що метод знаходить бронювання за ID
        List<Flight> flights = flightsService.getFlights();
        int validFlightId = flights.get(0).getID(); // Отримуємо ID першого рейсу
        Flight flight = flightsService.getFlightById(validFlightId);

        // Перевірка, що рейс має бронювання
        if (!flight.getBookings().isEmpty()) {
            int validBookingId = flight.getBookings().get(0).getID();
            Booking booking = flightsService.getFlightBookingById(validBookingId);
            assertNotNull(booking, "Бронювання з таким ID не знайдено");
            assertEquals(validBookingId, booking.getID(), "ID бронювання не співпадає");
        }
    }

    @Test
    public void testIncreaseAvailableSeatsByBookingId() {
        // Перевірка, що метод збільшує кількість доступних місць для рейсу
        List<Flight> flights = flightsService.getFlights();
        int validBookingId = flights.getFirst().getBookings().getFirst().getID(); // Отримуємо ID першого бронювання першого рейсу
        Flight flight = flightsService.getFlightById(flights.get(0).getID());
        int availableSeatsBefore = flight.getAvailableSeats();

        flightsService.increaseAvailableSeatsByBookingId(validBookingId);
        flight = flightsService.getFlightById(flights.get(0).getID());
        int availableSeatsAfter = flight.getAvailableSeats();

        assertTrue(availableSeatsAfter > availableSeatsBefore, "Кількість доступних місць не збільшилася");
    }

    @Test
    public void testDisplayAllFlights() {
        // Перевірка, що метод виводить всі рейси
        // Тут не буде прямого асерту, оскільки ми перевіряємо консольний вивід
        flightsService.displayAllFlights();
    }

    @Test
    public void testDisplayAllFlightsFromCity() {
        // Перевірка, що метод виводить всі рейси з міста
        try
        {
            String city = "Київ";
            flightsService.displayAllFlightsFromCity(city,inputOutputClass);
            // Тест на випадок, коли немає рейсів
            flightsService.displayAllFlightsFromCity("NonExistingCity",inputOutputClass);
        }catch (WrongInputException e){
            assertTrue(e!=null);
        }



    }

    @Test
    public void testIsThereSuchFlightById() {
        // Перевірка, що метод правильно перевіряє наявність рейсу за ID
        List<Flight> flights = flightsService.getFlights();
        int validId = flights.get(0).getID(); // Отримуємо ID першого рейсу
        boolean exists = flightsService.isThereSuchFlightById(validId);
        assertTrue(exists, "Рейс з таким ID має існувати");

        // Перевірка для невірного ID
        boolean notExists = flightsService.isThereSuchFlightById(9999); // Некоректний ID
        assertFalse(notExists, "Рейс з таким ID не має існувати");
    }
}