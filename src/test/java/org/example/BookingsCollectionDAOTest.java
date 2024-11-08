package org.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BookingsCollectionDAOTest {
    private BookingsCollectionDAO bookingsDAO;
    private List<Booking> bookings;
    private Flight flight;

    @BeforeEach
    public void setUp() {
        flight = new Flight(1, LocalDateTime.now(), LocalDateTime.now().plusHours(3), 50, 50, "Франкфурт", "Київ", new ArrayList<>());
        bookings = new ArrayList<>();
        Passenger passenger1 = new Passenger(1, "Ілля", "Федчук");
        Passenger passenger2 = new Passenger(2, "Олександр", "Флорчук");
        bookings.add(new Booking(1, flight, List.of(passenger1)));
        bookings.add(new Booking(2, flight, List.of(passenger2)));
        flight.setBookings(bookings);
        bookingsDAO = new BookingsCollectionDAO(bookings);
    }

    @Test
    public void testGetAll() {
        // Перевірка, що метод повертає всі бронювання
        List<Booking> allBookings = bookingsDAO.getAll();
        assertEquals(2, allBookings.size(), "Кількість бронювань повинна бути 2");
    }

    @Test
    public void testGetByID_ExistingID() {
        // Перевірка, що бронювання повертається за існуючим ID
        Booking booking = bookingsDAO.getByID(1);
        assertNotNull(booking, "Бронювання не повинно бути null");
        assertEquals(1, booking.getID(), "ID бронювання повинен бути 1");
    }

    @Test
    public void testGetByID_NonExistingID() {
        // Перевірка, що метод повертає null для неіснуючого ID
        Booking booking = bookingsDAO.getByID(999);
        assertNull(booking, "Бронювання повинно бути null для неіснуючого ID");
    }

    @Test
    public void testSave_NewBooking() {
        // Перевірка, що нове бронювання додається
        Passenger passenger3 = new Passenger(3, "Дмитро", "Сидорчук");
        Booking newBooking = new Booking(3, flight, List.of(passenger3));
        bookingsDAO.save(newBooking);
        assertEquals(3, bookingsDAO.getAll().size(), "Кількість бронювань повинна бути 3");
        assertEquals(newBooking, bookingsDAO.getByID(3), "Збережене бронювання повинно бути знайдене за ID");
    }

    @Test
    public void testSave_ExistingBooking() {
        Booking existingBooking = new Booking(1, flight, List.of(bookingsDAO.getAll().getFirst().getPassengers().getFirst()));
        bookingsDAO.save(existingBooking);
        assertEquals(2, bookingsDAO.getAll().size(), "Кількість бронювань повинна залишатись 2");
        assertEquals("Ілля", bookingsDAO.getByID(1).getPassengers().getFirst().getFirstName(), "Ім'я пасажира повинно бути оновлене");
    }

    @Test
    public void testDelete_ExistingBooking() {
        // Перевірка, що бронювання видаляється
        Booking bookingToDelete = bookings.get(0);
        bookingsDAO.delete(bookingToDelete);
        assertEquals(1, bookingsDAO.getAll().size(), "Кількість бронювань повинна бути 1 після видалення");
        assertNull(bookingsDAO.getByID(bookingToDelete.getID()), "Видалене бронювання не повинно існувати");
    }

    @Test
    public void testDeleteByID_ExistingID() {
        // Перевірка, що бронювання видаляється за ID
        boolean deleted = bookingsDAO.deleteByID(1);
        assertTrue(deleted, "Метод deleteByID повинен повернути true для існуючого ID");
        assertNull(bookingsDAO.getByID(1), "Бронювання з ID 1 повинно бути видалене");
    }

    @Test
    public void testDeleteByID_NonExistingID() {
        // Перевірка, що метод повертає false для неіснуючого ID
        boolean deleted = bookingsDAO.deleteByID(999);
        assertFalse(deleted, "Метод deleteByID повинен повернути false для неіснуючого ID");
    }

    @Test
    public void testGetMaxId() {
        // Перевірка, що повертається максимальний ID
        int maxId = bookingsDAO.getMaxId();
        assertEquals(2, maxId, "Максимальний ID повинен бути 2");
    }
}
