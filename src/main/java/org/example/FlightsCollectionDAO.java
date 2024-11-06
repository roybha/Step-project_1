package org.example;

import java.util.List;

public class FlightsCollectionDAO implements FlightDAO{
    private List<Flight> flights;
    public FlightsCollectionDAO(List<Flight> flights) {
        this.flights = flights;
    }
    @Override
    public List<Flight> getAll() {
        return flights;
    }
    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public Flight getByID(int id) {
        return(id >= 0 && id < flights.size()) ? flights.get(id) : null;
    }

    @Override
    public void save(Flight flight) {
     if(!flights.stream().anyMatch(f->f==flight)){
         flights.add(flight);
     }
     else {
         int i = flights.indexOf(flight);
         flights.set(i, flight);
     }
    }

    @Override
    public void delete(Flight flight) {
        flights.remove(flight);
    }

    @Override
    public boolean deleteByID(int id) {
        if(id>=0 && id<flights.size()) {
            delete(flights.get(id));
            return true;
        }
        return false;
    }
}
