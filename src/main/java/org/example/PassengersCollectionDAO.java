package org.example;

import java.util.List;

public class PassengersCollectionDAO implements PassengerDAO {
    List<Passenger> passengers;
    public PassengersCollectionDAO(List<Passenger> passengers) {
        this.passengers = passengers;
    }
    @Override
    public List<Passenger> getAll(){
        return passengers;
    }

    @Override
    public Passenger getByID(int id) {
        return (id >= 0)
                ? passengers.stream()
                .filter(passenger ->passenger.getID() == id)
                .findFirst()
                .orElse(null)
                : null;
    }

    @Override
    public void save(Passenger passenger) {
        if(!passengers.stream().anyMatch(p->passenger==p)){
            passengers.add(passenger);
        }else {
            int index = passengers.indexOf(passenger);
            passengers.set(index, passenger);
        }
    }

    @Override
    public void delete(Passenger passenger) {
        passengers.remove(passenger);
    }

    @Override
    public boolean deleteByID(int id) {
         if(getByID(id)!=null){
             delete(getByID(id));
             return true;
         }
         return false;
    }
    public void addPassengers(List<Passenger> passengers) {
        this.passengers.addAll(passengers);
    }
    public void removePassengers(List<Passenger> passengers) {
        this.passengers.removeAll(passengers);
    }
}
