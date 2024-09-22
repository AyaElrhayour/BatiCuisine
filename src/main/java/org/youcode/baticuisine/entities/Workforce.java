package org.youcode.baticuisine.entities;

import org.youcode.baticuisine.enums.ComponentType;

import java.util.UUID;

public class Workforce extends Component{

    public Workforce () {}

    public Workforce (UUID id, String name, ComponentType componentType, Double tvaRate,
                      Double unitaryPay, Double quantity, Double outputFactor, Project project) {
        super(id, name, ComponentType.WORKFORCE, tvaRate, unitaryPay, quantity, outputFactor, project);
    }
}
