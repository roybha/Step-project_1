package org.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightsControllerTest {
    private FlightsController flightsController;
    private BookingsController bookingsController;
    private InputOutputClass inputOutputClass;
    private Flight flight1;
    private Flight flight2;
    private List<Passenger> passengers;

    @BeforeEach
    public void setUp() {
        flightsController = new FlightsController();
        inputOutputClass = new InputOutputClass();
        flightsController.loadFromFile("flights.dat");
        bookingsController = new BookingsController();
        flightsController.getAllFlights().forEach(flight -> flight.setBookings(bookingsController.generateSomeBookingsForFlight(flight)));
        flight1 = new Flight(1, LocalDateTime.now() ,LocalDateTime.now().plusHours(3) , 100, 50,"Київ","Лондон",new ArrayList<>());
        flight2 = new Flight(2, LocalDateTime.now(),LocalDateTime.now().plusHours(2) , 80, 40,"Париж","Київ",new ArrayList<>());
    }

    @Test
    public void testGetAllFlights() {
        List<Flight> flights = flightsController.getAllFlights();
        assertEquals(30, flights.size(), "Має бути 30 рейсів в списку");
    }

    @Test
    public void testDisplayAllFlights() {
        // Зберігаємо оригінальний потік виведення
        PrintStream originalOut = System.out;

        // Створюємо потік для захоплення виведення
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream newOut = new PrintStream(baos);

        // Перенаправляємо System.out
        System.setOut(newOut);

        // Викликаємо метод, який має вивести дані
        flightsController.displayAllFlights();

        // Повертаємо System.out на його оригінальне місце
        System.setOut(originalOut);

        // Перевіряємо, що виведений потік не порожній
        String output = baos.toString();
        assertFalse(output.isEmpty(), "Виведення не повинно бути порожнім");

        // Додатково можна перевірити на наявність певного тексту, наприклад:
        assertTrue(output.contains("Київ"), "Виведення має містити назву міста Київ");
    }

    @Test
    public void testDisplayAllFlightsFromCity() {
        // Тест для перевірки виведення рейсів з певного міста
        String city = "Київ";  // Місто, для якого потрібно перевірити рейси

        // Зберігаємо оригінальний потік виведення
        PrintStream originalOut = System.out;

        // Створюємо потік для захоплення виведення
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream newOut = new PrintStream(baos);

        // Перенаправляємо System.out
        System.setOut(newOut);

        // Викликаємо метод для виведення рейсів з міста Київ
        flightsController.displayAllFlightsFromCity(city,inputOutputClass);

        // Повертаємо System.out на його оригінальне місце
        System.setOut(originalOut);

        // Перевіряємо, що виведення містить правильну інформацію
        String output = baos.toString();
        assertFalse(output.isEmpty(), "Виведення не повинно бути порожнім");
        assertTrue(output.contains(city), "Виведення має містити місто: " + city);

    }

    @Test
    public void testGetBookingsForFlight() {


        List<Booking> bookings = flightsController.getBookingsForFlight(flightsController.getAllFlights().getFirst());
        assertNotEquals(0, bookings.size(), "Не має не бути жодного бронювання для цього рейсу");
    }

    @Test
    public void testGetFlightById() {
        Flight foundFlight = flightsController.getFlightById(flightsController.getAllFlights().getFirst().getID());
        assertNotNull(foundFlight, "Перший рейс  має бути знайдений");
        assertEquals(flightsController.getAllFlights().getFirst(), foundFlight, "Знайдений рейс повинен відповідати доданому");
    }

    @Test
    public void testGetFlightBookingById() {

        Booking targetBooking=flightsController.getAllFlights().getFirst().getBookings().getFirst();
        Booking foundBooking = flightsController.getFlightBookingById(flightsController.getAllFlights().getFirst().getBookings().getFirst().getID());
        assertNotNull(foundBooking, "Бронювання з ID 1 має бути знайдено");
        assertEquals(targetBooking, foundBooking, "Знайдене бронювання має відповідати доданому");
    }

    @Test
    public void testRemoveFlightBookingById() {
        assertEquals(flightsController.getAllFlights().getFirst().getGlobalSeats()-flightsController.getAllFlights().getFirst().getAvailableSeats(), flightsController.getAllFlights().getFirst().getBookings().size(), "До видалення має бути 1 бронювання");
        int someBookingID=flightsController.getAllFlights().getFirst().getBookings().getLast().getID();
        int beforeSize = flightsController.getAllFlights().getFirst().getBookings().size();
        flightsController.removeFlightBookingById(someBookingID);
        assertTrue(beforeSize > flightsController.getAllFlights().getFirst().getBookings().size(), "Після видалення має бути 0 бронювань");
    }

    @Test
    public void testIncreaseAvailableSeatsByBookingId() {
        int initialAvailableSeats = flightsController.getAllFlights().getFirst().getAvailableSeats();

        flightsController.increaseAvailableSeatsByBookingId(flightsController.getAllFlights().getFirst().getBookings().getFirst().getID());
        assertEquals(initialAvailableSeats + 1, flightsController.getAllFlights().getFirst().getAvailableSeats(), "Кількість доступних місць має збільшитися на 1");
    }

    @Test
    public void testIsThereSuchFlightByID() {
        assertTrue(flightsController.isThereSuchFlightByID(flightsController.getAllFlights().getFirst().getID()), "Рейс з ID 1-го рейсу має існувати");
        assertFalse(flightsController.isThereSuchFlightByID(101), "Рейс з ID 101 не має існувати");
    }
}
