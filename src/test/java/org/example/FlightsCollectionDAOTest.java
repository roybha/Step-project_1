package org.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FlightsCollectionDAOTest {
    private FlightsCollectionDAO flightsDAO;
    private Flight flight1;
    private Flight flight2;

    @BeforeEach
    public void setUp() {
        List<Flight> flights = new ArrayList<>();
        flightsDAO = new FlightsCollectionDAO(flights);

        flight1 = new Flight(1, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(2), 100, 50, "Лондон", "Київ", new ArrayList<>());
        flight2 = new Flight(2, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(3), 80, 40, "Париж", "Київ", new ArrayList<>());

        flightsDAO.save(flight1);
        flightsDAO.save(flight2);
    }

    @Test
    public void testGetAll() {
        List<Flight> flights = flightsDAO.getAll();
        assertEquals(2, flights.size(), "Повинно бути 2 рейси в колекції");
    }

    @Test
    public void testGetByID() {
        Flight foundFlight = flightsDAO.getByID(1);
        assertNotNull(foundFlight, "Рейс з ID 1 має існувати");
        assertEquals(flight1, foundFlight, "Знайдений рейс має співпадати з доданим");
    }

    @Test
    public void testSave() {
        Flight newFlight = new Flight(3, LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(3).plusHours(4), 120, 60, "Токіо", "Київ", new ArrayList<>());
        flightsDAO.save(newFlight);
        assertEquals(3, flightsDAO.getAll().size(), "Після збереження має бути 3 рейси");
    }

    @Test
    public void testDelete() {
        flightsDAO.delete(flight1);
        assertEquals(1, flightsDAO.getAll().size(), "Після видалення має залишитись 1 рейс");
        assertNull(flightsDAO.getByID(1), "Рейс з ID 1 не повинен існувати після видалення");
    }

    @Test
    public void testDeleteByID() {
        assertTrue(flightsDAO.deleteByID(2), "Рейс з ID 2 повинен бути видалений");
        assertEquals(1, flightsDAO.getAll().size(), "Після видалення за ID має залишитись 1 рейс");
        assertNull(flightsDAO.getByID(2), "Рейс з ID 2 не повинен існувати після видалення");
    }

    @Test
    public void testGetFlightBookingByID() {
        Booking booking = new Booking(1, flight1, new ArrayList<>());
        flight1.getBookings().add(booking);
        Booking foundBooking = flightsDAO.getFlightBookingByID(1);
        assertNotNull(foundBooking, "Бронювання з ID 1 повинно бути знайдено");
        assertEquals(booking, foundBooking, "Знайдене бронювання повинно відповідати доданому");
    }

    @Test
    public void testRemoveFlightBookingByID() {
        Booking booking = new Booking(1, flight1, new ArrayList<>());
        flight1.getBookings().add(booking);
        flightsDAO.removeFlightBookingByID(1);
        assertTrue(flight1.getBookings().isEmpty(), "Бронювання має бути видалено");
    }

    @Test
    public void testIncreaseAvailableSeatsByBookingID() {
        Booking booking = new Booking(1, flight1, List.of(new Passenger(1, "John", "Doe")));
        flight1.getBookings().add(booking);
        int initialAvailableSeats = flight1.getAvailableSeats();
        flightsDAO.increaseAvailableSeatsByBookingID(1);
        assertEquals(initialAvailableSeats + 1, flight1.getAvailableSeats(), "Кількість доступних місць повинна збільшитися на 1");
    }

    @Test
    public void testIsThereSuchFlightByID() {
        assertTrue(flightsDAO.isThereSuchFlightByID(1), "Рейс з ID 1 повинен існувати");
        assertFalse(flightsDAO.isThereSuchFlightByID(999), "Рейс з ID 999 не повинен існувати");
    }

    @Test
    public void testCreateSomeFlights() {
        flightsDAO.CreateSomeFlights();
        assertTrue(flightsDAO.getAll().size() >= 23, "Після створення має бути щонайменше 23 рейси");
    }
}
