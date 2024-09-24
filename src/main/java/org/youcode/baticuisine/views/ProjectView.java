package org.youcode.baticuisine.views;

import org.youcode.baticuisine.entities.*;
import org.youcode.baticuisine.enums.ComponentType;
import org.youcode.baticuisine.enums.ProjectState;
import org.youcode.baticuisine.services.*;
import org.youcode.baticuisine.utils.BaseValidation;
import org.youcode.baticuisine.utils.ProjectStateValidation;

import java.time.LocalDate;
import java.util.*;

public class ProjectView {
    private final ProjectService projectService;
    private final MaterialService materialService;
    private final WorkforceService workforceService;
    private final EstimateService estimateService;
    private final ClientService clientService;
    private final Scanner sc;

    public ProjectView(ProjectService projectService, MaterialService materialService, WorkforceService workforceService, EstimateService estimateService, ClientService clientService) {
        this.projectService = projectService;
        this.materialService = materialService;
        this.workforceService = workforceService;
        this.estimateService = estimateService;
        this.clientService = clientService;
        this.sc = new Scanner(System.in);
    }

    public void addProject() {
        List<Material> materialList = new ArrayList<>();
        List<Workforce> workforceList = new ArrayList<>();
        try {
            String projectName = BaseValidation.getValidInput("Enter Project Name:", "name");
            String clientId = BaseValidation.getValidInput("Enter Client ID:", "clientId");
            Client client = clientService.getClientById(clientId);

            if (client == null) {
                System.out.println("Client not found. Cannot create project.");
                return;
            }
            Project project = new Project(
                    UUID.randomUUID(),
                    projectName,
                    null,
                    null,
                    ProjectState.PENDING,
                    client
            );

            System.out.println("--- Add materials ---");
            boolean addMoreMaterials = true;
            while (addMoreMaterials) {
                String materialName = BaseValidation.getValidInput("Enter the name of the material:", "materialName");
                ComponentType componentType = ComponentType.MATERIALS;
                double tvaRate = Double.parseDouble(BaseValidation.getValidInput("Enter the TVA rate of this material:", "tvaRate"));
                double unitaryPay = Double.parseDouble(BaseValidation.getValidInput("Enter the unit cost of this material:", "unitaryPay"));
                double quantity = Double.parseDouble(BaseValidation.getValidInput("Enter the quantity of this material:", "quantity"));
                double transportCost = Double.parseDouble(BaseValidation.getValidInput("Enter the transport cost of this material:", "transportCost"));
                double qualityCoefficient = Double.parseDouble(BaseValidation.getValidInput("Enter the material quality coefficient:", "qualityCoefficient"));

                Material material = new Material(UUID.randomUUID(), materialName, componentType, tvaRate, unitaryPay, quantity, qualityCoefficient, project, transportCost);
                if(materialService.addMaterial(material)) {
                    materialList.add(material);
                }

                String response = BaseValidation.getValidInput("Do you want to add another material (y/n):", "yesNo");
                addMoreMaterials = response.equalsIgnoreCase("y");
            }

            System.out.println("--- Adding workforce ---");
            boolean addMoreWorkforce = true;
            while (addMoreWorkforce) {
                String workforceName = BaseValidation.getValidInput("Enter the name of the workforce (e.g., Basic Worker, Specialist):", "workforceName");
                ComponentType workforceType = ComponentType.WORKFORCE;
                double tvaRate = Double.parseDouble(BaseValidation.getValidInput("Enter the TVA rate of this workforce:", "tvaRate"));
                double unitaryPay = Double.parseDouble(BaseValidation.getValidInput("Enter the unit pay for this workforce (€/unit):", "unitaryPay"));
                double quantity = Double.parseDouble(BaseValidation.getValidInput("Enter the quantity for this workforce:", "quantity"));
                double outputFactor = Double.parseDouble(BaseValidation.getValidInput("Enter the output factor (1.0 = standard, > 1.0 = high output):", "outputFactor"));

                Workforce workforce = new Workforce(UUID.randomUUID(), workforceName, workforceType, tvaRate, unitaryPay, quantity, outputFactor, project);
                if(workforceService.addWorkforce(workforce)) {
                    workforceList.add(workforce);
                }

                String response = BaseValidation.getValidInput("Do you want to add another type of workforce (y/n):", "yesNo");
                addMoreWorkforce = response.equalsIgnoreCase("y");
            }

            double totalMaterialCost = materialService.calculateTotalCost(materialList);
            double totalWorkforceCost = workforceService.calculateTotalCost(workforceList);
            double totalCost = totalMaterialCost + totalWorkforceCost;

            project.setTotalCost(null);

            double profitMargin = Double.parseDouble(BaseValidation.getValidInput("Enter Project Profit Margin (%):", "profitMargin"));
            project.setProfitMargin(profitMargin);

            Optional<Project> addedProject = projectService.addProject(project);
            System.out.println("Project added successfully!");
            LocalDate validityDate = BaseValidation.validateLocalDate("Enter Validity Date (yyyy-MM-dd):", "validityDate");
            Estimate estimate = new Estimate(UUID.randomUUID(), totalCost, LocalDate.now(), validityDate, false, addedProject.get());
            if (estimateService.createEstimate(estimate))
                System.out.println("Estimate created Successfully");

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