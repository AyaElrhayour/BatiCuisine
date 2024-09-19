package org.youcode.baticuisine.utils;

import java.time.LocalDate;
import java.util.UUID;

public class BaseValidation {
    public static boolean isUUIDValid(String uuid){
        try{
            UUID.fromString(uuid);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }

    public static void validateNotEmpty(String value, String fieldName){
        if (value == null || value.trim().isEmpty()){
            throw new IllegalArgumentException(fieldName + "Can't Be Empty!");
        }
    }
}
