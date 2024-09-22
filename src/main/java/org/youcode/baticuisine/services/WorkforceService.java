package org.youcode.baticuisine.services;

import org.youcode.baticuisine.entities.Workforce;
import org.youcode.baticuisine.repositories.interfaces.WorkforceInterface;
import org.youcode.baticuisine.utils.BaseValidation;

import java.util.Optional;
import java.util.UUID;

public class WorkforceService {

    private final WorkforceInterface workforceInterface;

    public WorkforceService(WorkforceInterface workforceInterface) {
        this.workforceInterface = workforceInterface;
    }

    // Method to add a new workforce
    public void addWorkforce(Workforce workforce) {
        if (workforce.getName() == null || workforce.getComponentType() == null || workforce.getTvaRate() == null
                || workforce.getUnitaryPay() == null || workforce.getQuantity() == null || workforce.getOutputFactor() == null
                || workforce.getProject() == null) {
            System.out.println("Required fields are unfilled!");
            return;
        }

        Optional<Workforce> addedWorkforce = workforceInterface.addWorkforce(workforce);
        if (addedWorkforce.isPresent()) {
            System.out.println("Workforce added successfully!");
        } else {
            System.out.println("Failed to add workforce.");
        }
    }

    // Method to retrieve workforce by project ID
    public Workforce getWorkforceByProjectId(String projectIdInput) {
        if (!BaseValidation.isUUIDValid(projectIdInput)) {
            System.out.println("Invalid UUID format. Please enter a valid UUID.");
            return null;
        }

        UUID projectId = UUID.fromString(projectIdInput);
        Optional<Workforce> retrievedWorkforce = workforceInterface.getWorkforceByProject(projectId);
        return retrievedWorkforce.orElse(null);
    }

}
