package org.youcode.baticuisine.views;

import org.youcode.baticuisine.entities.Client;
import org.youcode.baticuisine.entities.Project;
import org.youcode.baticuisine.enums.ProjectState;
import org.youcode.baticuisine.services.ClientService;
import org.youcode.baticuisine.services.ProjectService;
import org.youcode.baticuisine.utils.BaseValidation;
import org.youcode.baticuisine.utils.ProjectStateValidation;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ProjectView {
    private final ProjectService projectService;
    private final ClientService clientService;
    private final Scanner sc;

    public ProjectView(ProjectService projectService, ClientService clientService) {
        this.projectService = projectService;
        this.clientService = clientService;
        this.sc = new Scanner(System.in);
    }

    public void addProject() {
        try {
            String projectName = BaseValidation.getValidInput("Enter Project Name:", "name");
            Double profitMargin = Double.valueOf(BaseValidation.getValidInput("Enter Project Profit Margin:", "profitMargin"));
            Double totalCost = Double.valueOf(BaseValidation.getValidInput("Enter Project Total Cost:", "totalCost"));
            String clientId = BaseValidation.getValidInput("Enter Client ID:", "clientId");
            Client client = clientService.getClientById(clientId);
            ProjectState projectState = ProjectStateValidation.validateProjectState();


            if (client == null) {
                System.out.println("Client not found. Cannot create project.");
                return;
            }

            Project project = new Project(
                    UUID.randomUUID(),
                    projectName,
                    profitMargin,
                    totalCost,
                    projectState,
                    client
            );
            projectService.addProject(project);
            System.out.println("Project Added Successfully!");

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
            ProjectState projectState = ProjectStateValidation.validateProjectState();

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
