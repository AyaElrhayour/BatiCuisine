package org.youcode.baticuisine.utils;

import org.youcode.baticuisine.enums.ProjectState;

import java.util.Scanner;

public class ProjectStateValidation {

    public static ProjectState getValidateProjectState() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Enter Contract Status (PENDING, COMPLETED, SUSPENDED): ");
                String statusInput = scanner.nextLine().trim();
                return validateProjectState(statusInput);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static ProjectState validateProjectState(String status) {
        try {
            return ProjectState.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid contract status: " + status);
        }
    }
}
