package org.youcode.baticuisine.services;

import org.youcode.baticuisine.entities.Workforce;
import org.youcode.baticuisine.repositories.interfaces.WorkforceInterface;
import org.youcode.baticuisine.utils.BaseValidation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class WorkforceService {

    private final WorkforceInterface workforceInterface;

    public WorkforceService(WorkforceInterface workforceInterface) {
        this.workforceInterface = workforceInterface;
    }

    public boolean addWorkforce(Workforce workforce) {
        if (workforce.getName() == null || workforce.getComponentType() == null || workforce.getTvaRate() == null
                || workforce.getUnitaryPay() == null || workforce.getQuantity() == null || workforce.getOutputFactor() == null
                || workforce.getProject() == null) {
            System.out.println("Required fields are unfilled!");
            return false;
        }

        Optional<Workforce> addedWorkforce = workforceInterface.addWorkforce(workforce);
        if (addedWorkforce.isPresent()) {
            System.out.println("Workforce added successfully!");
            return true;
        } else {
            System.out.println("Failed to add workforce.");
        }
        return false;
    }

    public List<Workforce> getWorkforceByProjectId(String projectIdInput) {
        if (!BaseValidation.isUUIDValid(projectIdInput)) {
            System.out.println("Invalid UUID format. Please enter a valid UUID.");
            return null;
        }

        UUID projectId = UUID.fromString(projectIdInput);
        Optional<Workforce> retrievedWorkforce = workforceInterface.getWorkforceByProject(projectId);
        return (List<Workforce>) retrievedWorkforce.orElse(null);
    }

    public double calculateTotalCost(List<Workforce> workforceList) {
        return workforceList.stream()
                .mapToDouble(workforce -> workforce.getUnitaryPay() * workforce.getQuantity())
                .sum();
    }

}
