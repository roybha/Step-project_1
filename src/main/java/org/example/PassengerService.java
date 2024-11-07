package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PassengerService {
   private PassengersCollectionDAO passengersCollectionDAO;
   public PassengerService() {
       passengersCollectionDAO = new PassengersCollectionDAO(new ArrayList<>());
   }
   public PassengersCollectionDAO getPassengers(){
       return passengersCollectionDAO;
   }
   public void addPassengers(List<Passenger> passengers) {
       passengersCollectionDAO.addPassengers(passengers);
   }
   public void removePassengers(List<Passenger> passengers) {
       passengersCollectionDAO.removePassengers(passengers);
   }
    private boolean isPassengerAlreadyBooked(List<String> names, List<String> surnames, Flight flight) {
        return flight.getBookings().stream()
                .flatMap(booking -> booking.getPassengers().stream())
                .anyMatch(passenger ->
                        names.contains(passenger.getFirstName()) &&
                                surnames.contains(passenger.getLastName())
                );
    }
    public Optional<List<Passenger>> formNewPassengersForFlight(List<String> names, List<String> surnames, Flight flight) {
        if (!isPassengerAlreadyBooked(names, surnames, flight)) {
            List<Passenger> passengers = new ArrayList<>();
            for (int i = 0; i < names.size(); i++) {
                String firstName = names.get(i);
                String lastName = surnames.get(i);
                Passenger passenger = new Passenger(flight.getRegisteredPassengers().size()+1,firstName, lastName);
                passengers.add(passenger);
            }
            return Optional.of(passengers);
        }
        return Optional.empty();
    }
}
