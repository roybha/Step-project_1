package org.example;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class Flight implements HasID{
    private int id;
    @Override
    public int getID(){
        return id;
    }
    @Override
    public void setID(int id){
        this.id = id;
    }
    private LocalDateTime departureTime;
    public LocalDateTime getDepartureTime(){
        return departureTime;
    }
    public void SetDepartureTime(LocalDateTime time){
        departureTime = time;
    }
    private LocalDateTime arrivalTime;
    public LocalDateTime getArrivalTime(){
        return arrivalTime;
    }
    private String destination;
    public String getDestination(){
        return destination;
    }
    private String origin;
    public String getOrigin(){
        return origin;
    }
    public void SetOrigin(String origin){
        this.origin = origin;
    }
    private int availableSeats;
    public int getAvailableSeats(){
        return availableSeats;
    }
    public void SetAvailableSeats(int seats){
        availableSeats = seats;
    }
    private int globalSeats;
    public int getGlobalSeats(){
        return globalSeats;
    }
    public void SetGlobalSeats(int seats){
        globalSeats = seats;
    }
    public List<Booking> bookings;
    public List<Booking> getBookings(){
        return bookings;
    }
    public void SetBookings(List<Booking> bookings){
        this.bookings = bookings;
    }
    public Flight(int id, LocalDateTime departureTime, LocalDateTime arrivalTime, int globalSeats, int availableSeats, String destination, String origin,List<Booking> bookings) {
        this.id = id;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.globalSeats = globalSeats;
        this.availableSeats = availableSeats;
        this.destination = destination;
        this.origin = origin;
        this.bookings = bookings;
    }
    @Override
    public String toString() {
        String dateFormat="yyyy-MM-dd HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        return "Рейс №" + id + "| Час вильоту: "+ formatter.format(ZonedDateTime.of(departureTime, ZoneId.of("GMT+2"))) + "| Час прибуття "+formatter.format(ZonedDateTime.of(arrivalTime, ZoneId.of("GMT+2")))+
                "| Місто посадки: "+destination+"| Місто вильоту: "+origin+"| Загальна кількість місць: "+globalSeats +
                "| Вільні місця: "+availableSeats;
    }
}
