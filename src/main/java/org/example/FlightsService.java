package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlightsService {
    private FlightsCollectionDAO flightsCollectionDAO;
    public FlightsService () {
        flightsCollectionDAO = new FlightsCollectionDAO(new ArrayList<>());
        flightsCollectionDAO.CreateSomeFlights();
    }
    public List<Flight> getFlights(){
        return flightsCollectionDAO.getAll();
    }
    public void displayAllFlights(){
         flightsCollectionDAO.getAll().stream().forEach(System.out::println);
    }
    public void displayAllFlightsFromCity(String city){
        if(flightsCollectionDAO.getAll().stream().anyMatch(flight -> flight.getOrigin().equals(city))){
            System.out.printf("Всі рейси з %s в найближчі 24 години\n",city);
            flightsCollectionDAO.getAll().stream()
                    .filter(flight -> flight.getOrigin().equals(city) && flight.getDepartureTime().isBefore(LocalDateTime.now().plusHours(24)))
                    .collect(Collectors.toCollection(ArrayList::new)).forEach(System.out::println);
        }
        else {
            System.out.printf("Не знайдено рейсів з %s в найближчі 24 години\n", city);
        }
    }
    public Flight getFlightById(int id){
        return flightsCollectionDAO.getByID(id);
    }
    public List<Booking> getFlightBookings(Flight flight){
        return flight.getBookings();
    }
    public Booking getFlightBookingById(int id){
        return flightsCollectionDAO.getFlightBookingByID(id);
    }
    public void removeFlightBookingById(int id){
        flightsCollectionDAO.removeFlightBookingByID(id);
    }
    public void increaseAvailableSeatsById(int id){
        flightsCollectionDAO.increaseAvailableSeatsByID(id);
    }
    public boolean isThereSuchFlightById(int id){
        return flightsCollectionDAO.isThereSuchFlightByID(id);
    }
}
