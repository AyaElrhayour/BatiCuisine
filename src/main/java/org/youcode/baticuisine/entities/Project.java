package org.youcode.baticuisine.entities;

import org.youcode.baticuisine.enums.ProjectState;

import java.util.List;
import java.util.UUID;

public class Project {

    private UUID id;
    private String projectName;
    private Double profitMargin;
    private Double totalCost;
    private ProjectState projectState;
    private Client client;
    private List<Estimate> estimates;
    private List<Component> components;

    public Project() {}

    public Project(UUID id, String projectName, Double profitMargin,
                   Double totalCost, ProjectState projectState, Client client){
        this.id = id;
        this.projectName = projectName;
        this.profitMargin = profitMargin;
        this.totalCost = totalCost;
        this.projectState = projectState;
        this.client = client;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(Double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public ProjectState getProjectState() {
        return projectState;
    }

    public void setProjectState(ProjectState projectState) {
        this.projectState = projectState;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
