package com.sg.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO{
    private final Scanner scanner = new Scanner(System.in);;

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public double readDouble(String prompt) {
        System.out.println(prompt);
        return scanner.nextDouble();
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        System.out.println(prompt);
        double value = scanner.nextDouble();
        if (value < min || value > max){
            System.out.println("The value must be between " + min + " and " + max
                    + "\nPlease enter a new value: ");
            value = scanner.nextInt();
        }
        return value;
    }

    @Override
    public float readFloat(String prompt) {
        System.out.println(prompt);
        return scanner.nextFloat();
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        System.out.println(prompt);
        float value = scanner.nextInt();
        if (value < min || value > max){
            System.out.println("The value must be between " + min + " and " + max
                    + "\nPlease enter a new value: ");
            value = scanner.nextInt();
        }
        return value;
    }

    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);
        return Integer.parseInt(scanner.nextLine());
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        System.out.println(prompt);
        int value = scanner.nextInt();
        if (value < min || value > max){
            System.out.println("The value must be between " + min + " and " + max
                    + "\nPlease enter a new value: ");
            value = scanner.nextInt();
        }
        return value;
    }

    @Override
    public long readLong(String prompt) {
        System.out.println(prompt);
        return scanner.nextLong();
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        System.out.println(prompt);
        long value = scanner.nextInt();
        if (value < min || value > max){
            System.out.println("The value must be between " + min + " and " + max
                    + "\nPlease enter a new value: ");
            value = scanner.nextInt();
        }
        return value;
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    @Override
    public BigDecimal readBigDecimal(String prompt){
        System.out.println(prompt);
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            return new BigDecimal(0);
        }
        else {
            return new BigDecimal(scanner.nextLine());
        }
    }

    @Override
    public LocalDate readLocalDate(String prompt){
        System.out.println(prompt);
        System.out.println("The date must be in the following format: MM-dd-yyyy");
        return LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    }
}
