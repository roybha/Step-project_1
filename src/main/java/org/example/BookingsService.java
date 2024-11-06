package org.example;

import java.util.ArrayList;

public class BookingsService {
    private BookingsCollectionDAO bookingsCollectionDAO;
    public BookingsService() {
        bookingsCollectionDAO = new BookingsCollectionDAO(new ArrayList<>());
    }
    public BookingsCollectionDAO getBookings(){
        return bookingsCollectionDAO;
    }
}
