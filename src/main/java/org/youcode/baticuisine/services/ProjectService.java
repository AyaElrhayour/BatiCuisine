package org.youcode.baticuisine.services;

import org.youcode.baticuisine.entities.Project;
import org.youcode.baticuisine.repositories.interfaces.ProjectInterface;
import org.youcode.baticuisine.utils.BaseValidation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProjectService {

    private final ProjectInterface projectInterface;

    public ProjectService(ProjectInterface projectInterface) {
        this.projectInterface = projectInterface;
    }

    public void addProject(Project project) {
        if (project.getProjectName() == null || project.getProfitMargin() == null
                || project.getTotalCost() == null || project.getProjectState() == null || project.getClient() == null) {
            System.out.println("The Fields Unfilled!");
            return;
        }
        Optional<Project> addedProject = projectInterface.addProject(project);
        if (addedProject.isPresent())
            System.out.println("Project Added Successfully!");
    }

    public Project getProjectById(String projectIdInput) {
        if (!BaseValidation.isUUIDValid(projectIdInput)) {
            System.out.println("Invalid UUID Format. Please Enter a Valid UUID.");
            return null;
        }
        UUID id = UUID.fromString(projectIdInput);
        Optional<Project> retrievedProject = projectInterface.getProject(id);
        return retrievedProject.orElse(null);
    }

    public Project updateProject(String projectIdInput, Project project) {
        if (!BaseValidation.isUUIDValid(projectIdInput)) {
            throw new IllegalArgumentException("Invalid UUID Format");
        }

        UUID id = UUID.fromString(projectIdInput);
        Optional<Project> retrievedProject = projectInterface.getProject(id);
        if (retrievedProject.isPresent()) {
            Optional<Project> updatedProject = projectInterface.updateProject(id, project);
            return updatedProject.orElse(null);
        } else {
            return null;
        }
    }

    public boolean deleteProject(String projectIdInput) {
        if (!BaseValidation.isUUIDValid(projectIdInput)) {
            throw new IllegalArgumentException("Invalid UUID Format. Please Enter a Valid UUID.");
        }

        UUID id = UUID.fromString(projectIdInput);
        Optional<Project> retrievedProject = projectInterface.getProject(id);
        if (retrievedProject.isPresent()) {
            return projectInterface.deleteProject(id);
        } else {
            return false;
        }
    }

    public List<Project> getAllProjects() {
        return projectInterface.getAllProjects();
    }

    public List<Project> getProjectsByClientId(UUID clientId) {
        return projectInterface.getProjectsByClientId(clientId);
    }

}
