package org.example;

import java.util.*;

public class InputOutputClass {
    public void optionDescription(){
        System.out.println("Оберіть потрібну опцію");
        System.out.println("1 -> Вивести всі рейси");
        System.out.println("2 -> Вивести всі рейси з певного міста в найближчі 24 години");
        System.out.println("3 -> Забронювати рейс");
        System.out.println("4 -> Вивести всі бронювання по певному рейсу");
        System.out.println("5 -> Скасувати бронювання");
        System.out.println("exit -> Завершити роботу програми");
    }
    public String getStringInput(String message){
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print(message + " ");
            input = scanner.nextLine();

            if (!input.trim().isEmpty()) {
                return input;
            } else {
                System.out.println("Будь ласка, введіть непустий рядок.");
            }
        }
    }
    public int getIntInput(String message){
       Scanner scanner;
        while (true) {

            System.out.print(message+" ");
            scanner = new Scanner(System.in);


            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();

                if (input > 0) {
                    return Optional.of(input).orElse(0);
                } else {
                    System.out.println("Число повинно бути більшим за 0.");
                }
            } else {

                System.out.println("Будь ласка, введіть коректне ціле число.");
                scanner.next();
            }
        }
    }
    public void getMessage(String message){
        System.out.println(message);
    }
    public List<List<String>> getNamesAndSurnames(int count){
        List<String> names = new ArrayList<>();
        List<String> surnames = new ArrayList<>();
        if (count == 1) {
            names.add(getStringInput("Введіть ім'я"));
            surnames.add(getStringInput("Введіть прізвище"));
        } else {

            for (int i = 0; i < count; i++) {
                names.add(getStringInput("Введіть ім'я для людини " + (i + 1)));
                surnames.add(getStringInput("Введіть прізвище для людини " + (i + 1)));
            }
        }
        return List.of(names, surnames);
    }
    public <T> void printCollection(Collection<T> collection) {
        collection.forEach(System.out::println);
    }
}
