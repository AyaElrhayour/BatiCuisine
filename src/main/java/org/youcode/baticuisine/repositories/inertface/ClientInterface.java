package org.youcode.baticuisine.repositories.inertface;

import org.youcode.baticuisine.entities.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientInterface {

    Optional<Client> addClient(Client client);
    Optional<Client> getClient(UUID id);
    Optional<Client> updateClient(UUID id, Client client);
    boolean deleteClient(UUID id);
    List<Client> getAllClients();

}
