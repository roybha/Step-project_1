package org.example;

import java.util.List;

public class BookingsCollectionDAO implements BookingDAO{
    private List<Booking> bookings;
    public BookingsCollectionDAO(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public List<Booking> getAll() {
        return bookings;
    }

    @Override
    public Booking getByID(int id) {
        return (id>=0 && id<bookings.size()) ? bookings.get(id) : null;
    }

    @Override
    public void save(Booking booking) {
        if(!bookings.stream().anyMatch(b->b==booking)){
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
        if(id>=0 && id<bookings.size()){
            bookings.remove(bookings.get(id));
            return true;
        }
        return false;
    }
}
