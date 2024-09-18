package org.youcode.baticuisine.repositories.inertface;

import org.youcode.baticuisine.entities.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientInterface {

    Optional<Client> addClient(Client client);
    Optional<Client> getClient(UUID id);
    List<Client> getAllClients();
    Optional<Client> deleteClient(UUID id);


}
