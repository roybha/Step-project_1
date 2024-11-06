package org.example;

import java.util.ArrayList;

public class PassengerService {
   private PassengersCollectionDAO passengersCollectionDAO;
   public PassengerService() {
       passengersCollectionDAO = new PassengersCollectionDAO(new ArrayList<>());
   }
   public PassengersCollectionDAO getPassengers(){
       return passengersCollectionDAO;
   }
}
