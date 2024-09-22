package org.youcode.baticuisine.utils;

import org.youcode.baticuisine.enums.ComponentType;

public class ComponentTypeValidation {

    public static ComponentType validateComponentType(){
        while (true){
            try {
                return validateComponentType();
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
