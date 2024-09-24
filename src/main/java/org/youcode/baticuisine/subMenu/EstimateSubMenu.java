package org.youcode.baticuisine.subMenu;

import org.youcode.baticuisine.repositories.implementation.EstimateImplementation;
import org.youcode.baticuisine.repositories.implementation.ProjectImplementation;
import org.youcode.baticuisine.services.EstimateService;
import org.youcode.baticuisine.services.ProjectService;
import org.youcode.baticuisine.views.EstimateView;

import java.util.Scanner;

public class EstimateSubMenu {

    private static void displayMenu() {
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║                                               ║");
        System.out.println("║                Estimate Management            ║");
        System.out.println("║                                               ║");
        System.out.println("║         1 : Get Estimate By ID                ║");
        System.out.println("║         2 : Get Estimate By Project           ║");
        System.out.println("║         3 : Validate Estimate                 ║");
        System.out.println("║         4 : Return                            ║");
        System.out.println("║                                               ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
    }

    private static int getValidChoice(Scanner scanner) {
        while (true) {
            System.out.println("Enter Your Choice :");
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 4) {
                    return choice;
                } else {
                    System.out.println("Please Enter A Number Between 1 and 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please Enter A Number.");
            }
        }
    }

    public static void estimateManagementMenu() {
        Scanner scanner = new Scanner(System.in);
        EstimateImplementation estimateImplementation = new EstimateImplementation();
        EstimateService estimateService = new EstimateService(estimateImplementation);
        ProjectImplementation projectImplementation = new ProjectImplementation();
        ProjectService projectService = new ProjectService(projectImplementation);
        EstimateView estimateView = new EstimateView(estimateService, projectService);

        while (true) {
            displayMenu();
            int choice = getValidChoice(scanner);

            switch (choice) {
                case 1:
                    estimateView.getEstimateById();
                    break;
                case 2:
                    estimateView.getEstimateByProject();
                    break;
                case 3:
                    estimateView.validateEstimate();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please Select A Valid Option.");
                    break;
            }
        }
    }
}
