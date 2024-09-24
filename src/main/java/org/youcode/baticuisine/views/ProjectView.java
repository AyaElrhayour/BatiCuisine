package org.youcode.baticuisine.views;

import org.youcode.baticuisine.entities.Client;
import org.youcode.baticuisine.entities.Material;
import org.youcode.baticuisine.entities.Project;
import org.youcode.baticuisine.entities.Workforce;
import org.youcode.baticuisine.enums.ComponentType;
import org.youcode.baticuisine.enums.ProjectState;
import org.youcode.baticuisine.services.ClientService;
import org.youcode.baticuisine.services.MaterialService;
import org.youcode.baticuisine.services.ProjectService;
import org.youcode.baticuisine.services.WorkforceService;
import org.youcode.baticuisine.utils.BaseValidation;
import org.youcode.baticuisine.utils.ProjectStateValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ProjectView {
    private final ProjectService projectService;
    private final MaterialService materialService;
    private final WorkforceService workforceService;
    private final ClientService clientService;
    private final Scanner sc;

    public ProjectView(ProjectService projectService, MaterialService materialService, WorkforceService workforceService, ClientService clientService) {
        this.projectService = projectService;
        this.materialService = materialService;
        this.workforceService = workforceService;
        this.clientService = clientService;
        this.sc = new Scanner(System.in);
    }

//    public void addProject() {
//        try {
//            String projectName = BaseValidation.getValidInput("Enter Project Name:", "name");
//            Double profitMargin = Double.valueOf(BaseValidation.getValidInput("Enter Project Profit Margin:", "profitMargin"));
//            Double totalCost = Double.valueOf(BaseValidation.getValidInput("Enter Project Total Cost:", "totalCost"));
//            String clientId = BaseValidation.getValidInput("Enter Client ID:", "clientId");
//            Client client = clientService.getClientById(clientId);
//            ProjectState projectState = ProjectStateValidation.getValidateProjectState();
//
//
//            if (client == null) {
//                System.out.println("Client not found. Cannot create project.");
//                return;
//            }
//
//            Project project = new Project(
//                    UUID.randomUUID(),
//                    projectName,
//                    profitMargin,
//                    totalCost,
//                    projectState,
//                    client
//            );
//            projectService.addProject(project);
//            System.out.println("Project Added Successfully!");
//
//        } catch (IllegalArgumentException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }


    public void addProject() {
        List<Material> materialList = new ArrayList<>();
        List<Workforce> workforceList = new ArrayList<>();
        try {
            // Step 1: Collect project name and client
            String projectName = BaseValidation.getValidInput("Enter Project Name:", "name");
            String clientId = BaseValidation.getValidInput("Enter Client ID:", "clientId");
            Client client = clientService.getClientById(clientId);

            if (client == null) {
                System.out.println("Client not found. Cannot create project.");
                return;
            }

            // Step 2: Initialize Project entity
            Project project = new Project(
                    UUID.randomUUID(),
                    projectName,
                    null,  // Profit margin to be filled after materials and workforce
                    null,  // Total cost remains null during creation
                    ProjectState.PENDING,  // Default state or prompt for input later
                    client
            );

            // Step 3: Add materials
            System.out.println("--- Add materials ---");
            boolean addMoreMaterials = true;
            while (addMoreMaterials) {
                String materialName = BaseValidation.getValidInput("Enter the name of the material:", "materialName");
                ComponentType componentType = ComponentType.MATERIALS; // Assuming MATERAILS is a valid constant
                double tvaRate = Double.parseDouble(BaseValidation.getValidInput("Enter the TVA rate of this material:", "tvaRate"));
                double unitaryPay = Double.parseDouble(BaseValidation.getValidInput("Enter the unit cost of this material:", "unitaryPay"));
                double quantity = Double.parseDouble(BaseValidation.getValidInput("Enter the quantity of this material:", "quantity"));
                double transportCost = Double.parseDouble(BaseValidation.getValidInput("Enter the transport cost of this material:", "transportCost"));
                double qualityCoefficient = Double.parseDouble(BaseValidation.getValidInput("Enter the material quality coefficient:", "qualityCoefficient"));

                // Create Material instance
                Material material = new Material(UUID.randomUUID(), materialName, componentType, tvaRate, unitaryPay, quantity, qualityCoefficient, project, transportCost);
                if(materialService.addMaterial(material)) {
                    // Save the material using the service
                    materialList.add(material);
                }

                // Ask if they want to add another material
                String response = BaseValidation.getValidInput("Do you want to add another material (y/n):", "yesNo");
                addMoreMaterials = response.equalsIgnoreCase("y");
            }

            // Step 4: Add workforce
            System.out.println("--- Adding workforce ---");
            boolean addMoreWorkforce = true;
            while (addMoreWorkforce) {
                String workforceName = BaseValidation.getValidInput("Enter the name of the workforce (e.g., Basic Worker, Specialist):", "workforceName");
                ComponentType workforceType = ComponentType.WORKFORCE; // Assuming WORKFORCE is a valid constant
                double tvaRate = Double.parseDouble(BaseValidation.getValidInput("Enter the TVA rate of this workforce:", "tvaRate"));
                double unitaryPay = Double.parseDouble(BaseValidation.getValidInput("Enter the unit pay for this workforce (€/unit):", "unitaryPay"));
                double quantity = Double.parseDouble(BaseValidation.getValidInput("Enter the quantity for this workforce:", "quantity"));
                double outputFactor = Double.parseDouble(BaseValidation.getValidInput("Enter the output factor (1.0 = standard, > 1.0 = high output):", "outputFactor"));

                // Create Workforce instance
                Workforce workforce = new Workforce(UUID.randomUUID(), workforceName, workforceType, tvaRate, unitaryPay, quantity, outputFactor, project);
                if(workforceService.addWorkforce(workforce)) {
                    workforceList.add(workforce);// Save the workforce using the service
                }

                // Ask if they want to add another type of workforce
                String response = BaseValidation.getValidInput("Do you want to add another type of workforce (y/n):", "yesNo");
                addMoreWorkforce = response.equalsIgnoreCase("y");
            }

            // Step 5: Calculate the total cost for workforce and materials
            double totalMaterialCost = materialService.calculateTotalCost(materialList);
            double totalWorkforceCost = workforceService.calculateTotalCost(workforceList);
            double totalCost = totalMaterialCost + totalWorkforceCost;

            project.setTotalCost(totalCost);  // Set the total cost for the project

            // Step 6: After materials and workforce, calculate the profit margin
            double profitMargin = Double.parseDouble(BaseValidation.getValidInput("Enter Project Profit Margin (%):", "profitMargin"));
            project.setProfitMargin(profitMargin);

            // Step 7: Finally, save the project
            projectService.addProject(project);
            System.out.println("Project added successfully!");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }





    public void getProjectById() {
        System.out.println("Enter Project ID: ");
        String userInput = sc.nextLine();

        try {
            UUID projectId = UUID.fromString(userInput);
            Project project = projectService.getProjectById(String.valueOf(projectId));

            if (project != null) {
                System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║                                                                                  Project Details                                                                        ║");
                System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
                System.out.printf("║ %-36s │ %-20s │ %-15s │ %-15s │ %-25s ║%n",
                        "Project ID", "Project Name", "Profit Margin", "Total Cost", "Project State");
                System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
                System.out.printf("║ %-36s │ %-20s │ %-15s │ %-15s │ %-25s ║%n",
                        project.getId(),
                        project.getProjectName(),
                        project.getProfitMargin(),
                        project.getTotalCost(),
                        project.getProjectState());
            } else {
                System.out.println("Project Not Found");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Project ID Format!");
        }
    }

    public void updateProject() {
        try {
            String id = BaseValidation.getValidInput("Enter The ID Of the Project You Wish To Update:", "Id");
            if (projectService.getProjectById(String.valueOf(UUID.fromString(id))) == null) {
                throw new IllegalArgumentException("Project Not Found");
            }
            String projectName = BaseValidation.getValidInput("Enter New Project Name:", "name");
            Double profitMargin = Double.valueOf(BaseValidation.getValidInput("Enter New Profit Margin:", "profitMargin"));
            Double totalCost = Double.valueOf(BaseValidation.getValidInput("Enter New Total Cost:", "totalCost"));
            ProjectState projectState = ProjectStateValidation.getValidateProjectState();

            Project project = new Project(
                    UUID.randomUUID(),
                    projectName,
                    profitMargin,
                    totalCost,
                    projectState,
                    null
            );

            Project updatedProject = projectService.updateProject(id, project);

            if (updatedProject != null) {
                System.out.println("Project Updated Successfully!");
            } else {
                System.out.println("Project With ID :" + id + " Not Found");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteProject() {
        System.out.println("Enter Project's ID You Want To Delete:");
        String id = sc.nextLine();

        try {
            if (projectService.deleteProject(id)) {
                System.out.println("Project Was Deleted Successfully!");
            } else {
                System.out.println("Project With ID :" + id + " Not Found");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid ID");
        }
    }

    public void getAllProjects() {
        List<Project> projects = projectService.getAllProjects();

        if (projects.isEmpty()) {
            System.out.println("No Projects In The Database So Far!");
        } else {
            System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                                                                 Projects List                                                                           ║");
            System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.printf("║ %-36s │ %-20s │ %-15s │ %-15s │ %-25s ║%n",
                    "Project ID", "Project Name", "Profit Margin", "Total Cost", "Project State");
            System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");

            for (Project project : projects) {
                System.out.printf("║ %-36s │ %-20s │ %-15s │ %-15s │ %-25s ║%n",
                        project.getId(),
                        project.getProjectName(),
                        project.getProfitMargin(),
                        project.getTotalCost(),
                        project.getProjectState());
            }
        }
    }

    public void getProjectsByClientId() {
        System.out.println("Enter Client ID:");
        String clientIdInput = sc.nextLine();

        try {
            UUID clientId = UUID.fromString(clientIdInput);
            List<Project> projects = projectService.getProjectsByClientId(clientId);

            if (projects.isEmpty()) {
                System.out.println("No Projects Found For This Client!");
            } else {
                System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║                                                                                 Projects For Client                                                                      ║");
                System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
                System.out.printf("║ %-36s │ %-20s │ %-15s │ %-15s │ %-25s ║%n",
                        "Project ID", "Project Name", "Profit Margin", "Total Cost", "Project State");
                System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");

                for (Project project : projects) {
                    System.out.printf("║ %-36s │ %-20s │ %-15s │ %-15s │ %-25s ║%n",
                            project.getId(),
                            project.getProjectName(),
                            project.getProfitMargin(),
                            project.getTotalCost(),
                            project.getProjectState());
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Client ID Format!");
        }
    }
}