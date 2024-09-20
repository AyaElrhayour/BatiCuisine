package org.youcode.baticuisine.subMenu;

import org.youcode.baticuisine.repositories.implementation.ClientImplementation;
import org.youcode.baticuisine.services.ClientService;
import org.youcode.baticuisine.views.ClientViews;

import java.util.Scanner;

public class ClientSubMenu {

        private static void displayMenu() {
            System.out.println("╔═══════════════════════════════════════════════╗");
            System.out.println("║                                               ║");
            System.out.println("║                Client Management              ║");
            System.out.println("║                                               ║");
            System.out.println("║         1 : Add A Client                      ║");
            System.out.println("║         2 : Search For A Client               ║");
            System.out.println("║         3 : Update Client                     ║");
            System.out.println("║         4 : Delete Client                     ║");
            System.out.println("║         5 : List Clients                      ║");
            System.out.println("║         6 : Return                            ║");
            System.out.println("║                                               ║");
            System.out.println("╚═══════════════════════════════════════════════╝");
        }

        private static int getValidChoice(Scanner scanner){
            while (true){
                System.out.println("Enter Your Choice :");
                String input = scanner.nextLine().trim();
                try{
                    int choice = Integer.parseInt(input);
                    if (choice >= 1 && choice <= 6){
                        return choice;
                    } else {
                        System.out.println("Please Enter A Number Between 1 and 6.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please Enter A Number.");
                }
            }
        }

        public static void clientManagementMenu(){
            Scanner scanner = new Scanner(System.in);
            ClientImplementation clientImplementation = new ClientImplementation();
            ClientService clientService = new ClientService(clientImplementation);
            ClientViews clientViews = new ClientViews(clientService);

            while (true) {
                displayMenu();
                int choice = getValidChoice(scanner);

                switch (choice) {
                    case 1:
                        clientViews.addClient();
                        break;
                    case 2:
                        clientViews.getClientById();
                        break;
                    case 3:
                        clientViews.updateClient();
                        break;
                    case 4:
                        clientViews.deleteClient();
                        break;
                    case 5:
                        clientViews.getAllClients();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Invalid choice. Please Select A Valid Option.");
                        break;
                }
            }

        }
}
