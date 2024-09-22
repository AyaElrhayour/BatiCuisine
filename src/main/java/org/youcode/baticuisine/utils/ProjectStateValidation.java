package org.youcode.baticuisine.utils;

import org.youcode.baticuisine.enums.ProjectState;

import static org.youcode.baticuisine.utils.BaseValidation.getValidInput;

public class ProjectStateValidation {

    public static ProjectState validateProjectState() {
        while (true) {
            try {
                return validateProjectState();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
