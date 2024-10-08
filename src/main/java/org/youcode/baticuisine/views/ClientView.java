package org.youcode.baticuisine.views;

import org.youcode.baticuisine.entities.Client;
import org.youcode.baticuisine.services.ClientService;
import org.youcode.baticuisine.utils.BaseValidation;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ClientView {
    private final ClientService clientService;
    private final Scanner sc;

    public ClientView(ClientService clientService) {
        this.clientService = clientService;
        this.sc = new Scanner(System.in);
    }

    public void addClient() {
        Scanner scanner = new Scanner(System.in);
        try {
            String name = BaseValidation.getValidInput("Enter Client Name:", "name");
            String address = BaseValidation.getValidInput("Enter Client Address:", "address");
            String telephone = BaseValidation.getValidInput("Enter Client Phone Number:", "telephone");
            boolean isProfessional = true;
            while (true) {
                System.out.print("Is The Client Professional (yes/no): ");
                String choice = scanner.nextLine();

                if (choice.trim().toLowerCase().equals("yes")) {
                    break;
                } else if (choice.trim().toLowerCase().equals("no")) {
                    isProfessional = false;
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            }

            Client client = new Client(
                    UUID.randomUUID(),
                    name,
                    address,
                    telephone,
                    isProfessional
            );

            clientService.addClient(client);
            System.out.println("Client Added Successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void getAllClients() {
        List<Client> clients = clientService.getAllClients();

        if (clients.isEmpty()) {
            System.out.println("No Clients In The Database So Far!");
        } else {
            System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                                                                 Clients List                                                                           ║");
            System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.printf("║ %-36s │ %-15s │ %-15s │ %-20s │ %-25s ║%n",
                    "Client ID", "Name", "Address", "Telephone", "Is Professional");
            System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");

            for (Client client : clients) {
                System.out.printf("║ %-36s │ %-15s │ %-15s │ %-20s │ %-25s ║%n",
                        client.getId(),
                        client.getName(),
                        client.getAddress(),
                        client.getTelephone(),
                        client.getIsProfessional());
            }

        }
    }

    public void getClientById() {
        System.out.println("Enter Partner ID: ");
        String userInput = sc.nextLine();
        Client client = clientService.getClientById(userInput);

        if (client != null) {
            System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                                                                 Clients List                                                                           ║");
            System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.printf("║ %-36s │ %-15s │ %-15s │ %-20s │ %-25s ║%n",
                    "Client ID", "Name", "Address", "Telephone", "Is Professional");
            System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.printf("║ %-36s │ %-15s │ %-15s │ %-20s │ %-25s ║%n",
                    client.getId(),
                    client.getName(),
                    client.getAddress(),
                    client.getTelephone(),
                    client.getIsProfessional());

        } else {
            System.out.println("Client Not Found");
        }
    }

    public void updateClient() {
        try {
            String id = BaseValidation.getValidInput("Enter The ID Of the Client You Wish To Update:", "Id");
            if (clientService.getClientById(String.valueOf(UUID.fromString(id))) == null) {
                throw new IllegalArgumentException("Client Not Found");
            }
            String name = BaseValidation.getValidInput("Enter New Client Name:", "name");
            String address = BaseValidation.getValidInput("Enter New Address:", "address");
            String telephone = BaseValidation.getValidInput("Enter New Telephone:", "telephone");
            String isProfessional = BaseValidation.getValidInput("Is The Client Professional (yes/no)", "isProfessional");

            Client client = new Client(
                    UUID.fromString(id),
                    name,
                    address,
                    telephone,
                    Boolean.parseBoolean(isProfessional)
            );

            Client updateClient = clientService.updateClient(id, client);

            if (updateClient != null) {
                System.out.println("Client Updated Successfully!");
            } else {
                System.out.println("Client With ID :" + id + " Not Found");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public  void deleteClient(){
        System.out.println("Enter Client's ID You Want To Delete:");
        String id = sc.nextLine();

        try {
            if (clientService.deleteClient(id)){
                System.out.println("Client Was Deleted Successfully!");
            }else {
                System.out.println("Client With ID :" + id + "Not Found");
            }
        }catch (IllegalArgumentException e){
            System.out.println("Error : Invalid ID");
        }
    }
}
