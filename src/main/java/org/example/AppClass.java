package org.example;

import java.util.List;
import java.util.Optional;

public class AppClass {
    private PassengersController passengersController;
    private FlightsController flightsController;
    private  BookingsController bookingsController;
    private InputOutputClass inputOutputClass;
    public AppClass() {
        passengersController = new PassengersController();
        flightsController = new FlightsController();
        bookingsController = new BookingsController();
        inputOutputClass = new InputOutputClass();
        flightsController.getAllFlights().forEach(flight -> flight.setBookings(bookingsController.generateSomeBookingsForFlight(flight)));
        bookingsController.getBookings().forEach(booking -> passengersController.addPassengers(booking.getPassengers()));
    }
    private void displayAllFlights(){
        flightsController.displayAllFlights();
    }
    private void displayAllFlightsFromCity(){
        String city = inputOutputClass.getStringInput("Введіть місто");
        if(!city.equals("")){
            flightsController.displayAllFlightsFromCity(city);
        } else{
            inputOutputClass.getMessage("Ви не ввели місто");
        }
    }
    private void createBookingForFlight(Flight flight){
        int count = inputOutputClass.getIntInput("Введіть кількість людей,для яких потрібно створити бронювання");
        if(count == 0){
            inputOutputClass.getMessage("Ви ввели некоректне число");
        }
        else
        {
            if(count<=flight.getAvailableSeats()) {
               List<List<String>> namesAndSurnames=inputOutputClass.getNamesAndSurnames(count);
                Optional<List<Passenger>> passengers = passengersController.formNewPassengersForFlight(namesAndSurnames.get(0), namesAndSurnames.get(1), flight);
                if (passengers.isPresent()) {
                    Booking newBooking = bookingsController.addNewBooking(flight, passengers.get());
                    flight.getBookings().add(newBooking);
                    flight.setAvailableSeats(flight.getAvailableSeats() - count);
                    passengersController.addPassengers(passengers.get());
                } else {
                    inputOutputClass.getMessage("На рейсі вже присутній пасажир з таким ім'ям та прізвищем, як Ви ввели");
                }
            }else {
                inputOutputClass.getMessage("Не можна зробити бронювання для "+count+" людей, адже залишилось лише "+flight.getAvailableSeats()+" вільних місця на даному рейсі");
            }
        }
    }
    public void bookSomeFlight(){
        int index =inputOutputClass.getIntInput("Введіть ID рейсу");
        if(index >=0 && flightsController.isThereSuchFlightByID(index)){
            createBookingForFlight(flightsController.getFlightById(index));
        }
        else {
            inputOutputClass.getMessage("Введено некоректний ID");
        }
    }

    private void showBookingsForFlight(){
        int index = inputOutputClass.getIntInput("Введіть ID рейсу");
        if(index >= 0 && flightsController.isThereSuchFlightByID(index))
           inputOutputClass.printCollection(flightsController
                   .getBookingsForFlight(flightsController.getFlightById(index)));
        else{
            System.out.println("Введено некоректний ID");
        }
    }
    private void cancelBooking(){
        int bookingID = inputOutputClass.getIntInput("Введіть ID бронювання");

        if (bookingsController.deleteBooking(bookingID)) {
            flightsController.increaseAvailableSeatsByBookingId(bookingID);
            passengersController.removePassengers(flightsController.getFlightBookingById(bookingID).getPassengers());
            flightsController.removeFlightBookingById(bookingID);
            inputOutputClass.getMessage("Бронювання успішно видалено");
        } else {
            inputOutputClass.getMessage("Не знайдено бронювання з таким ID");
        }
    }
    public void mainProgram() {
        do{
            chooseOption();
        }while (true);
    }
    private void chooseOption(){
        optionsDescription();
        optionAction(inputOutputClass.getStringInput("Оберіть опцію"));
    }
    private void optionsDescription(){
       inputOutputClass.optionDescription();
    }
    private void optionAction(String option){
        switch (option.toLowerCase()) {
            case "1":displayAllFlights();break;
            case "2":displayAllFlightsFromCity();break;
            case "3":bookSomeFlight();break;
            case "4":showBookingsForFlight();break;
            case "5":cancelBooking();break;
            case "exit":System.exit(0);break;
            default:break;
        }
    }
}
