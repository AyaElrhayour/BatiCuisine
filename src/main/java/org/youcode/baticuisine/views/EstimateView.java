package org.youcode.baticuisine.views;

import org.youcode.baticuisine.entities.Estimate;
import org.youcode.baticuisine.entities.Project;
import org.youcode.baticuisine.enums.ProjectState;
import org.youcode.baticuisine.services.EstimateService;
import org.youcode.baticuisine.services.ProjectService;

import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class EstimateView {
    private final EstimateService estimateService;
    private final ProjectService projectService;
    private final Scanner sc;

    public EstimateView(EstimateService estimateService, ProjectService projectService) {
        this.estimateService = estimateService;
        this.projectService = projectService;
        this.sc = new Scanner(System.in);
    }


    public void getEstimateByProject() {
        System.out.println("Enter Project ID:");
        String projectIdInput = sc.nextLine();

        try {
            Estimate estimate = estimateService.getEstimateByProject(projectIdInput);

            if (estimate != null) {
                displayEstimateDetails(estimate);
            } else {
                System.out.println("No Estimate Found for the Given Project ID.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Project ID Format: " + e.getMessage());
        }
    }


    public void getEstimateById() {
        System.out.println("Enter Estimate ID:");
        String estimateIdInput = sc.nextLine();

        try {
            UUID estimateId = UUID.fromString(estimateIdInput);
            Optional<Estimate> estimate = estimateService.getEstimateById(String.valueOf(estimateId));

            if (estimate.isPresent()) {
                displayEstimateDetails(estimate.get());
            } else {
                System.out.println("Estimate Not Found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Estimate ID Format!");
        }
    }


    public void validateEstimate() {
        System.out.println("Enter Estimate ID for validation:");
        String estimateIdInput = sc.nextLine();

        try {
            Estimate estimate = estimateService.getEstimateById(estimateIdInput)
                    .orElseThrow(() -> new IllegalArgumentException("Estimate Not Found"));

            boolean validationResult = estimateService.validateEstimate(estimateIdInput, estimate);

            if (validationResult) {
                Project project = projectService.getProjectById(String.valueOf(estimate.getProject().getId()));
                project.setTotalCost(estimate.getEstimatedAmount());
                project.setProjectState(ProjectState.COMPLETED);
                Project updatedProject = projectService.updateProject(String.valueOf(project.getId()), project);
                if (updatedProject != null)
                    System.out.println("Estimate Validated Successfully.");
            } else {
                System.out.println("Estimate Validation Failed.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void displayEstimateDetails(Estimate estimate) {
        System.out.println("╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                 Estimate Details                                   ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("║ %-36s │ %-20s │ %-15s │ %-15s ║%n",
                "Estimate ID", "Estimated Amount", "Issued Date", "Accepted");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.printf("║ %-36s │ %-20s │ %-15s │ %-15s ║%n",
                estimate.getId(),
                estimate.getEstimatedAmount(),
                estimate.getIssuedDate(),
                estimate.getAccepted() ? "Yes" : "No");
    }
}
