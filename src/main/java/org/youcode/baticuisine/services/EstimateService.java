package org.youcode.baticuisine.services;

import org.youcode.baticuisine.entities.Estimate;
import org.youcode.baticuisine.repositories.interfaces.EstimateInterface;
import org.youcode.baticuisine.utils.BaseValidation;

import java.util.Optional;
import java.util.UUID;

public class EstimateService {

    private final EstimateInterface estimateInterface;

    public EstimateService(EstimateInterface estimateInterface) {
        this.estimateInterface = estimateInterface;
    }

    public boolean createEstimate(Estimate estimate) {
        if (estimate.getEstimatedAmount() == null || estimate.getIssuedDate() == null || estimate.getValidityDate() == null
                 || estimate.getProject() == null) {
            return false;
        }
        Optional<Estimate> createdEstimate = estimateInterface.createEstimate(estimate);
        return createdEstimate.isPresent();
    }

    public Estimate getEstimateByProject(String projectIdInput) {
        if (!BaseValidation.isUUIDValid(projectIdInput)) {
            System.out.println("Invalid UUID Format. Please Enter a Valid UUID.");
            return null;
        }
        UUID projectId = UUID.fromString(projectIdInput);
        Optional<Estimate> retrievedEstimate = estimateInterface.getEstimateByProject(projectId);
        return retrievedEstimate.orElse(null);
    }

    public boolean validateEstimate(String estimateIdInput, Estimate estimate) {
        if (!BaseValidation.isUUIDValid(estimateIdInput)) {
            throw new IllegalArgumentException("Invalid UUID Format. Please Enter a Valid UUID.");
        }

        UUID estimateId = UUID.fromString(estimateIdInput);
        Optional<Estimate> validatedEstimate = estimateInterface.validateEstimate(estimateId, estimate);
        return validatedEstimate.isPresent();
    }

    public Optional<Estimate> getEstimateById(String estimateId) {
        try {
            UUID id = UUID.fromString(estimateId);

            return estimateInterface.getEstimateById(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Estimate ID: " + estimateId);
            return Optional.empty();
        }
    }
}
