package org.youcode.baticuisine.services;

import org.youcode.baticuisine.entities.Material;
import org.youcode.baticuisine.repositories.interfaces.MaterialInterface;
import org.youcode.baticuisine.utils.BaseValidation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MaterialService {

    private final MaterialInterface materialInterface;

    public MaterialService(MaterialInterface materialInterface) {
        this.materialInterface = materialInterface;
    }

    public boolean addMaterial(Material material) {
        if (material.getName() == null || material.getComponentType() == null || material.getTvaRate() == null
                || material.getUnitaryPay() == null || material.getQuantity() == null || material.getOutputFactor() == null
                || material.getTransportCost() == null || material.getProject() == null) {
            System.out.println("Required fields are unfilled!");
            return false;
        }
        Optional<Material> addedMaterial = materialInterface.addMaterial(material);
        if (addedMaterial.isPresent()) {
            System.out.println("Material added successfully!");
            return true;
        }
        return false;
    }

    public List<Material> getMaterialByProjectId(String projectIdInput) {
        if (!BaseValidation.isUUIDValid(projectIdInput)) {
            System.out.println("Invalid UUID format. Please enter a valid UUID.");
            return null;
        }

        UUID projectId = UUID.fromString(projectIdInput);
        Optional<Material> retrievedMaterial = materialInterface.getMaterialByProject(projectId);
        return (List<Material>) retrievedMaterial.orElse(null);
    }

    public double calculateTotalCost(List<Material> materials) {
        return materials.stream()
                .mapToDouble(material -> (material.getUnitaryPay() + material.getTransportCost()) * material.getQuantity())
                .sum();
    }

}
