package org.example;

public class PassengersController {
    private PassengerService passengerService;

    public PassengerService getPassengerService() {
        return passengerService;
    }
    public PassengersController(){
        passengerService= new PassengerService();
    }
}
