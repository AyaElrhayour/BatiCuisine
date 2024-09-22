package org.youcode.baticuisine.entities;

import java.time.LocalDate;
import java.util.UUID;

public class Estimate {

    private UUID id;
    private Double estimatedAmount;
    private LocalDate issuedDate;
    private LocalDate validityDate;
    private Boolean accepted;
    private Project project;

    public Estimate () {}

    public Estimate (UUID id, Double estimatedAmount, LocalDate issuedDate, LocalDate validityDate, Boolean accepted, Project project) {
        this.id = id;
        this.estimatedAmount = estimatedAmount;
        this.issuedDate = issuedDate;
        this.validityDate = validityDate;
        this.accepted = accepted;
        this.project = project;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(Double estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
