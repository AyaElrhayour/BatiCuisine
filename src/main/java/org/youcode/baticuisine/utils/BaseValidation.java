package org.youcode.baticuisine.utils;

import java.util.Scanner;
import java.util.UUID;

public class BaseValidation {

    private static final Scanner sc = new Scanner(System.in);

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
}
