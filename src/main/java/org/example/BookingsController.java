package org.example;

public class BookingsController {
    private BookingsService bookingsService;
    public BookingsService getBookingsService() {
        return bookingsService;
    }
    public BookingsController() {
        bookingsService = new BookingsService();
    }
}
