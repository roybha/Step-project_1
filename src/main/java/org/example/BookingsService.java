package org.example;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class BookingsService {
    private  BookingsCollectionDAO bookingsCollectionDAO;
    private static int currentPassengerNumber=0;
    public BookingsService() {
        bookingsCollectionDAO = new BookingsCollectionDAO(new ArrayList<>());
    }
    public List<Booking> getBookings() {
        return bookingsCollectionDAO.getAll();
    }
    public Booking addNewBooking(Flight flight, List<Passenger> passengers){
        Booking newBooking = new Booking(getMaxBookingID()+1, flight, passengers);
        bookingsCollectionDAO.save(newBooking);
        return newBooking;
    }
    public  List<Booking> generateBookingsList(Flight flight) {
        int size = flight.getGlobalSeats()-flight.getAvailableSeats();
        Random random = new Random();
        List<Booking> bookings = new ArrayList<>();
        List<String> firstNames = List.of(
                "Олександр", "Максим", "Артем", "Дмитро", "Андрій",
                "Іван", "Богдан", "Тарас", "Микола", "Володимир",
                "Юрій", "Олег", "Сергій", "Віктор", "Ігор",
                "Петро", "Роман", "Олексій", "Вадим", "Георгій"
        );

        List<String> lastNames = List.of(
                "Шевченко", "Коваленко", "Бондаренко", "Ткаченко", "Кравченко",
                "Олійник", "Іваненко", "Савченко", "Мороз", "Ковальчук",
                "Лисенко", "Гаврилюк", "Петренко", "Сидоренко", "Дмитрук",
                "Романюк", "Гончар", "Мельник", "Антонюк", "Поліщук"
        );

        Set<String> uniquePassengers = new HashSet<>();

        for (int i = 1; i <= size; i++) {
            List<Passenger> passengers = new ArrayList<>();

            while (passengers.isEmpty()) {
                String firstName = firstNames.get(random.nextInt(firstNames.size()));
                String lastName = lastNames.get(random.nextInt(lastNames.size()));
                String fullName = firstName + " " + lastName;

                if (!uniquePassengers.contains(fullName)) {
                    int passengerId = flight.getRegisteredPassengers().size() + uniquePassengers.size() + 1;
                    passengers.add(new Passenger(passengerId, firstName, lastName));
                    uniquePassengers.add(fullName);
                }
            }
            int newBookingId = getMaxBookingID() + 1;
            Booking booking = new Booking(newBookingId, flight, passengers);
            booking.setBookingDate(modifyBookingDate(booking.getBookingDate()));
            bookings.add(booking);
            getBookings().add(booking);
        }
        return bookings;
    }
    private static LocalDateTime modifyBookingDate(LocalDateTime bookingDate) {
        Random random = new Random();
        if (random.nextDouble() > 0.5) {
            return bookingDate.minusMinutes(random.nextInt(1,60));
        } else {
            return bookingDate.minusHours(random.nextInt(1,24));
        }
    }
    public List<Booking> getBookingsForFlight(Flight flight) {
        return bookingsCollectionDAO.getBookingsForFlight(flight);
    }
    public Booking getBookingById(int id){
        return bookingsCollectionDAO.getByID(id);
    }
    public boolean deleteBooking(int id){
        return bookingsCollectionDAO.deleteByID(id);
    }
    private int getMaxBookingID(){
        return bookingsCollectionDAO.getMaxId();
    }
    public void saveToFile(String filename){
        bookingsCollectionDAO.saveToFile(filename);
    }
    public void loadFromFile(String filename){
        bookingsCollectionDAO.loadFromFile(filename);
    }
}
