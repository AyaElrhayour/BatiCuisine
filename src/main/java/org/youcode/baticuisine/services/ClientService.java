package org.youcode.baticuisine.services;

import org.youcode.baticuisine.entities.Client;
import org.youcode.baticuisine.repositories.inertface.ClientInterface;
import org.youcode.baticuisine.utils.BaseValidation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientService {

    private final ClientInterface clientInterface;

    public ClientService(ClientInterface clientInterface) {
        this.clientInterface = clientInterface;
    }

    public void addClient (Client client){
        if(client.getName() == null || client.getAddress() == null
                || client.getTelephone() == null || client.getIsProfessional() == null ){
            System.out.println("The Fields Unfilled!");
        }
        Optional<Client> addedClient = clientInterface.addClient(client);
        if (addedClient.isPresent())
            System.out.println("Client Is Added Successfully!");
    }

    public List<Client> getAllClients(){
        return clientInterface.getAllClients();
    }

    public boolean deleteClient(String clientIdInput){
        if (!BaseValidation.isUUIDValid(clientIdInput)) {
             throw new IllegalArgumentException("Invalid UUID Format. Please Enter a Valid UUID.");
         }

        UUID id = UUID.fromString(clientIdInput);
        Optional<Client> retrievedClient = clientInterface.getClient(id);
        if (retrievedClient.isPresent()){
            return clientInterface.deleteClient(id);
        }else {
            return false;
        }
    }

    public Client getClientById(String clientIdInput){
        if (!BaseValidation.isUUIDValid(clientIdInput)){
            System.out.println("Invalid UUID Format. Please Enter a Valid UUID.");
            return null;
        }
        UUID id = UUID.fromString(clientIdInput);
        Optional<Client> retrievedClient = clientInterface.getClient(id);
        return retrievedClient.orElse(null);
    }

}
