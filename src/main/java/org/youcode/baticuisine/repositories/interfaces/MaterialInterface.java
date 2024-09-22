package org.youcode.baticuisine.repositories.interfaces;

import org.youcode.baticuisine.entities.Material;

import java.util.Optional;
import java.util.UUID;

public interface MaterialInterface {

    Optional<Material> addMaterial(Material material);
    Optional<Material> getMaterialByProject (UUID projectId);

}
