package org.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightsService {
    private FlightsCollectionDAO flightsCollectionDAO;
    public FlightsService () {
        flightsCollectionDAO = new FlightsCollectionDAO(new ArrayList<>());
    }
    public List<Flight> getFlights(){
        return flightsCollectionDAO.getAll();
    }
    public void displayAllFlights(){
         flightsCollectionDAO.getAll().stream().forEach(System.out::println);
    }
    public void displayAllFlightsFromCity(String city,InputOutputClass inputOutputClass){
        if(flightsCollectionDAO.getAll().stream().anyMatch(flight -> flight.getOrigin().equals(city)
                && flight.getDepartureTime().isBefore(LocalDateTime.now().plusHours(24)))){
            inputOutputClass.getMessage(String.format("Всі рейси з міста %s", city));
            flightsCollectionDAO.getAll().stream()
                    .filter(flight -> flight.getOrigin().equals(city) && flight.getDepartureTime().isBefore(LocalDateTime.now().plusHours(24)))
                    .collect(Collectors.toCollection(ArrayList::new)).forEach(System.out::println);
        }
        else {
            throw new WrongInputException(String.format("Не знайдено рейсів з %s в найближчі 24 години", city));
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
    public void increaseAvailableSeatsByBookingId(int id){
        flightsCollectionDAO.increaseAvailableSeatsByBookingID(id);
    }
    public boolean isThereSuchFlightById(int id){
        return flightsCollectionDAO.isThereSuchFlightByID(id);
    }
    public void addFlight(Flight flight){
        flightsCollectionDAO.save(flight);
    }
    // Збереження списку рейсів до файлу
    public  void saveToFile(String fileName) {
       flightsCollectionDAO.saveToFile(fileName);
    }
    public void loadFromFile(String fileName) {
        flightsCollectionDAO.loadFromFile(fileName);
    }
}
