package org.youcode.baticuisine.entities;

import org.youcode.baticuisine.enums.ComponentType;

import java.util.UUID;

public class Material extends Component{
    private Double transportCost;

    public Material () {}

    public Material (UUID id, String name, ComponentType componentType, Double tvaRate,
                     Double unitaryPay, Double quantity, Double outputFactor, Project project, Double transportCost){
        super(id, name, ComponentType.MATERAILS, tvaRate, unitaryPay, quantity, outputFactor, project);
        this.transportCost = transportCost;

    }

    public Double getTransportCost() {
        return transportCost;
    }

    public void setTransportCost(Double transportCost) {
        this.transportCost = transportCost;
    }
}
