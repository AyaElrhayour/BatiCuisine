package org.youcode.baticuisine.views;

import org.youcode.baticuisine.entities.Workforce;
import org.youcode.baticuisine.entities.Project;
import org.youcode.baticuisine.enums.ComponentType;
import org.youcode.baticuisine.services.WorkforceService;
import org.youcode.baticuisine.services.ProjectService;
import org.youcode.baticuisine.utils.BaseValidation;

import java.util.Scanner;
import java.util.UUID;

public class WorkforceView {
    private final WorkforceService workforceService;
    private final ProjectService projectService;
    private final Scanner sc;

    public WorkforceView(WorkforceService workforceService, ProjectService projectService) {
        this.workforceService = workforceService;
        this.projectService = projectService;
        this.sc = new Scanner(System.in);
    }


    public void addWorkforce() {
        try {
            String workforceName = BaseValidation.getValidInput("Enter Workforce Name:", "name");
            Double tvaRate = Double.valueOf(BaseValidation.getValidInput("Enter TVA Rate:", "tvaRate"));
            Double unitaryPay = Double.valueOf(BaseValidation.getValidInput("Enter Unitary Pay:", "unitaryPay"));
            Double quantity = Double.valueOf(BaseValidation.getValidInput("Enter Quantity:", "quantity"));
            Double outputFactor = Double.valueOf(BaseValidation.getValidInput("Enter Output Factor:", "outputFactor"));
            String projectId = BaseValidation.getValidInput("Enter Project ID:", "projectId");
            Project project = projectService.getProjectById(projectId);

            if (project == null) {
                System.out.println("Project not found. Cannot create workforce.");
                return;
            }

            Workforce workforce = new Workforce(
                    UUID.randomUUID(),
                    workforceName,
                    ComponentType.WORKFORCE,
                    tvaRate,
                    unitaryPay,
                    quantity,
                    outputFactor,
                    project
            );

            workforceService.addWorkforce(workforce);
            System.out.println("Workforce Added Successfully!");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public void getWorkforceByProjectId() {
        System.out.println("Enter Project ID: ");
        String userInput = sc.nextLine();

        try {
            UUID projectId = UUID.fromString(userInput);
            Workforce workforce = (Workforce) workforceService.getWorkforceByProjectId(userInput);

            if (workforce != null) {
                System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║                                      Workforce Details                                ║");
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════════╣");
                System.out.printf("║ %-36s │ %-20s │ %-10s │ %-10s │ %-10s ║%n",
                        "Workforce ID", "Workforce Name", "Unitary Pay", "Quantity", "Output Factor");
                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════╝");
                System.out.printf("║ %-36s │ %-20s │ %-10s │ %-10s │ %-10s ║%n",
                        workforce.getId(),
                        workforce.getName(),
                        workforce.getUnitaryPay(),
                        workforce.getQuantity(),
                        workforce.getOutputFactor());
            } else {
                System.out.println("Workforce Not Found");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Project ID Format!");
        }
    }

}
