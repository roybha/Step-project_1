package org.example;

import java.time.LocalDateTime;
import java.util.*;

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

    public   int getMaxId(){
        return bookings.stream()
                .mapToInt(Booking::getID)
                .max()
                .orElse(0);
    }
}
