package org.youcode.baticuisine.repositories.interfaces;

import org.youcode.baticuisine.entities.Workforce;

import java.util.Optional;
import java.util.UUID;

public interface WorkforceInterface {

    Optional<Workforce> addWorkforce(Workforce workforce);
    Optional<Workforce> getWorkforceByProject (UUID projectId);
}
