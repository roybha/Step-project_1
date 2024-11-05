package org.example;

import java.time.LocalDateTime;


public class Flight {
    private int flightNumber;
    public int GetNumber(){
        return flightNumber;
    }
    public void SetNumber(int number){
        flightNumber = number;
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
    public Flight(int flightNumber, LocalDateTime departureTime, LocalDateTime arrivalTime, int globalSeats, int availableSeats,String destination,String origin){
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.globalSeats = globalSeats;
        this.availableSeats = availableSeats;
        this.destination = destination;
        this.origin = origin;
    }
}
