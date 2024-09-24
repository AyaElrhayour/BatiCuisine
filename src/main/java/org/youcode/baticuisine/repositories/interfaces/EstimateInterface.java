package org.youcode.baticuisine.repositories.interfaces;

import org.youcode.baticuisine.entities.Estimate;
import org.youcode.baticuisine.entities.Project;

import java.util.Optional;
import java.util.UUID;

public interface EstimateInterface {

    Optional<Estimate> createEstimate (Estimate estimate);
    Optional<Estimate> getEstimateByProject (UUID projectId);
    Optional<Estimate> validateEstimate(UUID id, Estimate estimate);
    Optional<Estimate> getEstimateById (UUID id);
}
