package org.example;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BookingsCollectionDAO implements BookingDAO{
    private List<Booking> bookings;
    public  BookingsCollectionDAO(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public  List<Booking> getAll() {
        return bookings;
    }

    @Override
    public Booking getByID(int id) {
        return (id >= 0 && !bookings.isEmpty())
                ? bookings.stream()
                .filter(flight -> flight.getID() == id)
                .findFirst()
                .orElse(null)
                : null;
    }

    @Override
    public void save(Booking booking) {
        if(!bookings.stream().anyMatch(b->b.equals(booking))){
            bookings.add(booking);
        }
        else{
            int index = bookings.indexOf(booking);
            bookings.set(index, booking);
        }
    }

    @Override
    public void delete(Booking booking) {
        bookings.remove(booking);
    }

    @Override
    public boolean deleteByID(int id) {
        if(getByID(id)!=null){
            delete(getByID(id));
            return true;
        }
        return false;
    }

    @Override
    public void saveToFile(String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(bookings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromFile(String filename) {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            bookings= (List<Booking>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public   int getMaxId(){
        return bookings.stream()
                .mapToInt(Booking::getID)
                .max()
                .orElse(0);
    }
    public List<Booking> getBookingsForFlight(Flight flight) {
        List<Booking> flightBookings = new ArrayList<>();
        return bookings.stream()
                .filter(booking -> booking.getFlight().getID() == flight.getID())
                .collect(Collectors.toList());
    }
}
