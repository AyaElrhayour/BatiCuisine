package org.youcode.baticuisine.utils;

import org.youcode.baticuisine.enums.ProjectState;

public class ProjectStateValidation {

    public static ProjectState validateTransportType(String transportType) {
        try {
            return ProjectState.valueOf(transportType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Project State. Valid values are PENDING, COMPLETED, CANCELLED.");
        }
    }
}
