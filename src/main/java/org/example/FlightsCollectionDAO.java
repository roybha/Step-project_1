package org.example;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class FlightsCollectionDAO implements FlightDAO{
    private List<Flight> flights;
    public FlightsCollectionDAO(List<Flight> flights) {
        this.flights = flights;
    }
    @Override
    public List<Flight> getAll() {
        return flights;
    }
    @Override
    public Flight getByID(int id) {
        return (id >= 0)
                ? flights.stream()
                .filter(flight -> flight.getID() == id)
                .findFirst()
                .orElse(null)
                : null;
    }

    @Override
    public void save(Flight flight) {
     if(!flights.stream().anyMatch(f->f==flight)){
         flights.add(flight);
     }
     else {
         int i = flights.indexOf(flight);
         flights.set(i, flight);
     }
    }

    @Override
    public void delete(Flight flight) {
        flights.remove(flight);
    }
    @Override
    public boolean deleteByID(int id) {
        if(getByID(id)!=null) {
            delete(getByID(id));
            return true;
        }
        return false;
    }
    public Booking getFlightBookingByID(int id) {

        return flights.stream()
                .flatMap(flight -> flight.getBookings().stream())
                .filter(booking -> booking.getID() == id)
                .findFirst()
                .orElse(null);
    }
    public  void removeFlightBookingByID(int id) {
        flights.stream()
                .filter(flight -> flight.getBookings().stream().anyMatch(booking -> booking.getID() == id))
                .findFirst()
                .ifPresent(flight -> flight.getBookings().removeIf(booking -> booking.getID() == id));
    }
    public void increaseAvailableSeatsByBookingID(int id) {
        flights.stream()
                .filter(flight -> flight.getBookings().stream().anyMatch(booking -> booking.getID() == id))
                .findFirst()
                .ifPresent(flight -> {
                    flight.getBookings().stream()
                            .filter(booking -> booking.getID() == id)
                            .findFirst()
                            .ifPresent(booking -> {
                                int passengers = booking.getPassengers().size(); // Отримуємо кількість пасажирів
                                flight.setAvailableSeats(flight.getAvailableSeats() + passengers); // Збільшуємо кількість вільних місць
                            });
                });
    }
    public boolean isThereSuchFlightByID(int id) {
        return flights.stream().anyMatch(flight -> flight.getID() == id);
    }
    public void CreateSomeFlights() {
        Random rand = new Random();
        List<Integer> previousID = new ArrayList<>();
        String[] destinations = {"Софія", "Лондон", "Париж", "Токіо", "Берлін"};
        String[] origins = {"Київ", "Варшава", "Стамбул", "Рим", "Мадрид"};

        Stream.generate(() -> {
                    int id;
                    do{
                        id = rand.nextInt(1,100);
                    }while (previousID.contains(id));
                    previousID.add(id);
                    int globalSeats = rand.nextInt(20, 50);
                    int availableSeats = rand.nextInt(1, globalSeats);

                    LocalDateTime departureTime = LocalDateTime.of(
                            LocalDateTime.now().getYear(),
                            LocalDateTime.now().getMonth(),
                            rand.nextInt(LocalDateTime.now().getDayOfMonth(), LocalDateTime.now().getMonth().length(false)),
                            rand.nextInt(LocalDateTime.now().getHour(), 24),
                            rand.nextInt(0, 60)
                    );

                    LocalDateTime arrivalTime = departureTime.plusHours(rand.nextInt(1, 11));
                    String destination = destinations[rand.nextInt(destinations.length)];
                    String origin = "Київ";
                    List<Booking> bookings = new ArrayList<>();

                    return new Flight(id, departureTime, arrivalTime, globalSeats, availableSeats, destination, origin, bookings);
                })
                .limit(10)
                .forEach(this::save);

        for (int i = 0; i < 20; i++) {
            int id;
            do{
                id = rand.nextInt(1,100);
            }while (previousID.contains(id));
            previousID.add(id);
            int globalSeats = rand.nextInt(20, 50);
            int availableSeats = rand.nextInt(1, globalSeats);


            LocalDateTime departureTime = LocalDateTime.of(
                    LocalDateTime.now().getYear(),
                    LocalDateTime.now().getMonth(),
                    rand.nextInt(LocalDateTime.now().getDayOfMonth(), LocalDateTime.now().getMonth().length(false)),
                    rand.nextInt(LocalDateTime.now().getHour(), 24),
                    rand.nextInt(0, 60)
            );


            LocalDateTime arrivalTime = departureTime.plusHours(rand.nextInt(1, 11));

            String destination = destinations[rand.nextInt(destinations.length)];
            String origin = origins[rand.nextInt(origins.length)];
            List<Booking> bookings = new ArrayList<>();

            Flight fl = new Flight(id, departureTime, arrivalTime, globalSeats, availableSeats, destination, origin, bookings);
            save(fl);
        }
    }
    @Override
    public void saveToFile(String fileName){
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(flights);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromFile(String filename) {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            flights = (List<Flight>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
