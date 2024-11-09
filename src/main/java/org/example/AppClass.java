package org.example;

import java.util.List;
import java.util.Optional;

public class AppClass {
    private PassengersController passengersController;
    private FlightsController flightsController;
    private  BookingsController bookingsController;
    private InputOutputClass inputOutputClass;
    private LoggerService loggerService;
    public AppClass() {
        passengersController = new PassengersController();
        flightsController = new FlightsController();
        bookingsController = new BookingsController();
        loggerService = new LoggerService();
        flightsController.loadFromFile("flights.dat");
        passengersController.loadFromFile("passengers.dat");
        bookingsController.loadFromFile("bookings.dat");
        inputOutputClass = new InputOutputClass();
        flightsController.getAllFlights().forEach(flight -> flight.setBookings(bookingsController.getBookingsForFlight(flight)));

    }
    private void displayAllFlights(){
        flightsController.displayAllFlights();
        loggerService.info("Виведення інформації про всі рейси");
    }
    private void displayAllFlightsFromCity(){
        String city = inputOutputClass.getStringInput("Введіть місто");
        if(!city.isEmpty()){
            flightsController.displayAllFlightsFromCity(city,inputOutputClass);
            loggerService.info("Виведення інформації про всі рейси в найближчі 24 години з міста "+city);
        } else{
            loggerService.error("Місто не було введено");
            throw new WrongInputException("Місто не було введено");
        }
    }
    private void createBookingForFlight(Flight flight){
        int count = inputOutputClass.getIntInput("Введіть кількість людей,для яких потрібно створити бронювання");
        if(count == 0){
            loggerService.error("Кількість людей для бронювання має бути > 0");
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
                    loggerService.info("Спроба записати пасажира, що присутній на даному рейсі");
                    inputOutputClass.getMessage("На рейсі вже присутній пасажир з таким ім'ям та прізвищем, як Ви ввели");
                }
            }else {
                loggerService.info("Перевищення кількості введених бронювань над кількістю вільних місць");
                inputOutputClass.getMessage("Не можна зробити бронювання для "+count+" людей, адже залишилось лише "+flight.getAvailableSeats()+" вільних місця на даному рейсі");
            }
        }
    }
    public void bookSomeFlight(){
        int index =inputOutputClass.getIntInput("Введіть ID рейсу");
        if(index >=0 && flightsController.isThereSuchFlightByID(index)){
            createBookingForFlight(flightsController.getFlightById(index));
            loggerService.info("Створення бронювання на рейс № "+index);
        }
        else {
            loggerService.error(String.format("Спроба створити бронювання на неіснуючий рейс з ID %d",index));
            throw new WrongInputException(String.format("Не знайдено рейсу з ID %d", index));
        }
    }

    private void showBookingsForFlight(){
        int index = inputOutputClass.getIntInput("Введіть ID рейсу");
        if(index >= 0 && flightsController.isThereSuchFlightByID(index))
        { inputOutputClass.printCollection(flightsController
                   .getBookingsForFlight(flightsController.getFlightById(index)));
            loggerService.info(String.format("Показ усіх бронювань на рейс з ID %d",index));
        }
        else{
            loggerService.error(String.format("Спроба показати бронювання неіснуючого рейсу з ID %d",index));
            throw new WrongInputException(String.format("Не знайдено рейсу з ID %d", index));
        }
    }
    private void cancelBooking(){
        int bookingID = inputOutputClass.getIntInput("Введіть ID бронювання");
        if (bookingsController.deleteBooking(bookingID)) {
            flightsController.increaseAvailableSeatsByBookingId(bookingID);
            passengersController.removePassengers(
                    flightsController.getFlightBookingById(bookingID).getPassengers());
            flightsController.removeFlightBookingById(bookingID);
            inputOutputClass.getMessage("Бронювання успішно видалено");
            loggerService.info(String.format("Скасування бронювання з ID %d",bookingID));
        } else {
            loggerService.error(String.format("Спроба скасувати  неіснуюче бронювання з ID %d",bookingID));
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
    private void saveDataToFiles(){
        flightsController.saveToFile("flights.dat");
        passengersController.saveToFile("passengers.dat");
        bookingsController.saveToFile("bookings.dat");
        loggerService.info("Завершення роботи програми,збереження інформації до файлів");
    }
    private void optionAction(String option){
        try {
            switch (option.toLowerCase()) {
                case "1":displayAllFlights();break;
                case "2":displayAllFlightsFromCity();break;
                case "3":bookSomeFlight();break;
                case "4":showBookingsForFlight();break;
                case "5":cancelBooking();break;
                case "exit":
                    inputOutputClass.getMessage("Завершення роботи програми(SAVING DATA)");
                    saveDataToFiles();
                    System.exit(0);
                    break;
                default:
                {
                    loggerService.error(String.format("Спроба виконати відсутню опцію %s", option));
                    throw new WrongInputException("Відсутня опція "+option);
                }
            }
        }catch (WrongInputException ex){
            inputOutputClass.getMessage(ex.getMessage());
        }
    }
}
