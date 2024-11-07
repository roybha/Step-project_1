package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PassengersController {
    private PassengerService passengerService;
    public PassengersController(){
        passengerService= new PassengerService();
    }
    public Optional<List<Passenger>> formNewPassengersForFlight(List<String> names, List<String> surnames, Flight flight){
        return passengerService.formNewPassengersForFlight(names, surnames, flight);
    }
    public void addPassengers(List<Passenger> passengers) {
        passengerService.addPassengers(passengers);
    }
    public void removePassengers(List<Passenger> passengers) {
        passengerService.removePassengers(passengers);
    }
}
