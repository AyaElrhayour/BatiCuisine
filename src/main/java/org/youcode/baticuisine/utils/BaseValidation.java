package org.youcode.baticuisine.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class BaseValidation {

    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static boolean isUUIDValid(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " can't be empty!");
        }
    }

    public static String getValidInput(String prompt, String fieldName) {
        System.out.println(prompt);
        String input = sc.nextLine();
        validateNotEmpty(input, fieldName);
        return input;
    }

    public static LocalDate validateLocalDate(String prompt, String fieldName) {
        while (true) {
            System.out.println(prompt);
            String dateInput = sc.nextLine();
            validateNotEmpty(dateInput, fieldName);

            try {
                return LocalDate.parse(dateInput, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format for " + fieldName + "! Please enter a valid date in the format yyyy-MM-dd.");
            }
        }
    }
}
