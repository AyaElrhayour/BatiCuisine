package org.youcode.baticuisine.subMenu;

import org.youcode.baticuisine.repositories.implementation.*;
import org.youcode.baticuisine.services.*;
import org.youcode.baticuisine.views.ProjectView;

import java.util.Scanner;

public class ProjectSubMenu {

    private static void displayMenu() {
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║                                               ║");
        System.out.println("║                Project Management             ║");
        System.out.println("║                                               ║");
        System.out.println("║         1 : Add A Project                     ║");
        System.out.println("║         2 : Search For A Project              ║");
        System.out.println("║         3 : Update Project                    ║");
        System.out.println("║         4 : Delete Project                    ║");
        System.out.println("║         5 : List All Projects                 ║");
        System.out.println("║         6 : List Projects By Client           ║");
        System.out.println("║         7 : Return                            ║");
        System.out.println("║                                               ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
    }

    private static int getValidChoice(Scanner scanner) {
        while (true) {
            System.out.println("Enter Your Choice :");
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 7) {
                    return choice;
                } else {
                    System.out.println("Please Enter A Number Between 1 and 7.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please Enter A Number.");
            }
        }
    }

    public static void projectManagementMenu() {
        Scanner scanner = new Scanner(System.in);
        ProjectImplementation projectImplementation = new ProjectImplementation();
        ProjectService projectService = new ProjectService(projectImplementation);
        ClientImplementation clientImplementation = new ClientImplementation();
        MaterialImplementation materialImplementation =new MaterialImplementation();
        WorkforceImplementation workforceImplementation = new WorkforceImplementation();
        EstimateImplementation estimateImplementation = new EstimateImplementation();
        ClientService clientService = new ClientService(clientImplementation);
        MaterialService materialService = new MaterialService(materialImplementation);
        WorkforceService workforceService = new WorkforceService(workforceImplementation);
        EstimateService estimateService = new EstimateService(estimateImplementation);
        ProjectView projectView = new ProjectView(projectService,  materialService, workforceService, estimateService, clientService);

        while (true) {
            displayMenu();
            int choice = getValidChoice(scanner);

            switch (choice) {
                case 1:
                    projectView.addProject();
                    break;
                case 2:
                    projectView.getProjectById();
                    break;
                case 3:
                    projectView.updateProject();
                    break;
                case 4:
                    projectView.deleteProject();
                    break;
                case 5:
                    projectView.getAllProjects();
                    break;
                case 6:
                    projectView.getProjectsByClientId();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice. Please Select A Valid Option.");
                    break;
            }
        }
    }
}
