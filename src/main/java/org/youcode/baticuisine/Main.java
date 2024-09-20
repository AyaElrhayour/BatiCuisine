package org.youcode.baticuisine;

import org.youcode.baticuisine.db.DBConnection;
import org.youcode.baticuisine.subMenu.ClientSubMenu;

import java.util.Scanner;

public class Main {

    private static void displayMenu(){
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║                                               ║");
        System.out.println("║                  BatiCuisine                  ║");
        System.out.println("║                                               ║");
        System.out.println("║           1 : Client Management               ║");
        System.out.println("║           2 : Project Management              ║");
        System.out.println("║           3 : Estimate Management             ║");
        System.out.println("║           4 : EXIT                            ║");
        System.out.println("║                                               ║");
        System.out.println("╚═══════════════════════════════════════════════╝");

    }

    private static  int getValidChoice(Scanner scanner){
        while (true){
            System.out.println("Enter Your Choice : ");
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 4){
                    return choice;
                }else {
                    System.out.println("Please Enter a Number Between 1 and 4.");
                }
            }catch (IllegalArgumentException e){
                System.out.println("Invalid input. Please Enter A Number.");
            }
        }
    }

public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true){
            displayMenu();
            int choice = getValidChoice(scanner);

            switch (choice){
                case 1:
                    ClientSubMenu.clientManagementMenu();
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:
                    System.out.println("Thank You For Your Hard Work! Have A Good Day!");
                    DBConnection.closeConnection();
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Choice. Please Select A Valid Option.");
                    break;
            }


        }
    }
}