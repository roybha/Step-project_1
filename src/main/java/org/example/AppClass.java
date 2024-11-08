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
        if(!city.isEmpty()){
            flightsController.displayAllFlightsFromCity(city);
        } else{
            throw new WrongInputException("Місто не було введено");
        }
    }
    private void createBookingForFlight(Flight flight){
        int count = inputOutputClass.getIntInput("Введіть кількість людей,для яких потрібно створити бронювання");
        if(count == 0){
            throw new WrongInputException("Кількість людей для бронювання має бути > 0");
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
            throw new WrongInputException(String.format("Не знайдено рейсу з ID %d", index));
        }
    }

    private void showBookingsForFlight(){
        int index = inputOutputClass.getIntInput("Введіть ID рейсу");
        if(index >= 0 && flightsController.isThereSuchFlightByID(index))
           inputOutputClass.printCollection(flightsController
                   .getBookingsForFlight(flightsController.getFlightById(index)));
        else{
            throw new WrongInputException(String.format("Не знайдено рейсу з ID %d", index));
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
            throw new WrongInputException(String.format("Не занйдено бронювання з ID %d", bookingID));
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
        try {
            switch (option.toLowerCase()) {
                case "1":displayAllFlights();break;
                case "2":displayAllFlightsFromCity();break;
                case "3":bookSomeFlight();break;
                case "4":showBookingsForFlight();break;
                case "5":cancelBooking();break;
                case "exit":inputOutputClass.getMessage("Завершення роботи програми");System.exit(0);break;
                default:throw new WrongInputException("Відсутня опція "+option);
            }
        }catch (WrongInputException ex){
            System.out.println(ex.getMessage());
        }

    }
}
