package org.youcode.baticuisine.entities;

import org.youcode.baticuisine.enums.ComponentType;

import java.util.UUID;

public class Component {

    private UUID id;
    private String name;
    private ComponentType componentType;
    private Double tvaRate;
    private Double unitaryPay;
    private Double quantity;
    private Double outputFactor;
    private Project project;

    public Component (){}

    public Component (UUID id, String name, ComponentType componentType, Double tvaRate,
                        Double unitaryPay, Double quantity, Double outputFactor, Project project){

        this.id = id;
        this.name = name;
        this.componentType = componentType;
        this.tvaRate = tvaRate;
        this.unitaryPay = unitaryPay;
        this.quantity = quantity;
        this.outputFactor = outputFactor;
        this.project = project;

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    public Double getTvaRate() {
        return tvaRate;
    }

    public void setTvaRate(Double tvaRate) {
        this.tvaRate = tvaRate;
    }

    public Double getUnitaryPay() {
        return unitaryPay;
    }

    public void setUnitaryPay(Double unitaryPay) {
        this.unitaryPay = unitaryPay;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getOutputFactor() {
        return outputFactor;
    }

    public void setOutputFactor(Double outputFactor) {
        this.outputFactor = outputFactor;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
