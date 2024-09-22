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

    public void addMaterial(Material material) {
        if (material.getName() == null || material.getComponentType() == null || material.getTvaRate() == null
                || material.getUnitaryPay() == null || material.getQuantity() == null || material.getOutputFactor() == null
                || material.getTransportCost() == null || material.getProject() == null) {
            System.out.println("Required fields are unfilled!");
            return;
        }
        Optional<Material> addedMaterial = materialInterface.addMaterial(material);
        if (addedMaterial.isPresent()) {
            System.out.println("Material added successfully!");
        }
    }

    public Material getMaterialByProjectId(String projectIdInput) {
        if (!BaseValidation.isUUIDValid(projectIdInput)) {
            System.out.println("Invalid UUID format. Please enter a valid UUID.");
            return null;
        }

        UUID projectId = UUID.fromString(projectIdInput);
        Optional<Material> retrievedMaterial = materialInterface.getMaterialByProject(projectId);
        return retrievedMaterial.orElse(null);
    }

}
