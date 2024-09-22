package org.youcode.baticuisine.views;

import org.youcode.baticuisine.entities.Material;
import org.youcode.baticuisine.entities.Project;
import org.youcode.baticuisine.enums.ComponentType;
import org.youcode.baticuisine.services.MaterialService;
import org.youcode.baticuisine.services.ProjectService;
import org.youcode.baticuisine.utils.BaseValidation;

import java.util.Scanner;
import java.util.UUID;

public class MaterialView {
    private final MaterialService materialService;
    private final ProjectService projectService;
    private final Scanner sc;

    public MaterialView(MaterialService materialService, ProjectService projectService) {
        this.materialService = materialService;
        this.projectService = projectService;
        this.sc = new Scanner(System.in);
    }

    public void addMaterial() {
        try {
            String materialName = BaseValidation.getValidInput("Enter Material Name:", "name");
            Double tvaRate = Double.valueOf(BaseValidation.getValidInput("Enter TVA Rate:", "tvaRate"));
            Double unitaryPay = Double.valueOf(BaseValidation.getValidInput("Enter Unitary Pay:", "unitaryPay"));
            Double quantity = Double.valueOf(BaseValidation.getValidInput("Enter Quantity:", "quantity"));
            Double outputFactor = Double.valueOf(BaseValidation.getValidInput("Enter Output Factor:", "outputFactor"));
            Double transportCost = Double.valueOf(BaseValidation.getValidInput("Enter Transport Cost:", "transportCost"));
            String projectId = BaseValidation.getValidInput("Enter Project ID:", "projectId");
            Project project = projectService.getProjectById(projectId);

            if (project == null) {
                System.out.println("Project not found. Cannot create material.");
                return;
            }

            Material material = new Material(
                    UUID.randomUUID(),
                    materialName,
                    ComponentType.MATERAILS,
                    tvaRate,
                    unitaryPay,
                    quantity,
                    outputFactor,
                    project,
                    transportCost

            );
            materialService.addMaterial(material);
            System.out.println("Material Added Successfully!");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void getMaterialByProjectId() {
        System.out.println("Enter Project ID: ");
        String userInput = sc.nextLine();

        try {
            UUID projectId = UUID.fromString(userInput);
            Material material = materialService.getMaterialByProjectId(userInput);

            if (material != null) {
                System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║                                      Material Details                                  ║");
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════════╣");
                System.out.printf("║ %-36s │ %-20s │ %-10s │ %-10s │ %-10s ║%n",
                        "Material ID", "Material Name", "Unitary Pay", "Quantity", "Transport Cost");
                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════╝");
                System.out.printf("║ %-36s │ %-20s │ %-10s │ %-10s │ %-10s ║%n",
                        material.getId(),
                        material.getName(),
                        material.getUnitaryPay(),
                        material.getQuantity(),
                        material.getTransportCost());
            } else {
                System.out.println("Material Not Found");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Project ID Format!");
        }
    }

}
