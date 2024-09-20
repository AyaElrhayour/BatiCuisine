package org.youcode.baticuisine.repositories.interfaces;

import org.youcode.baticuisine.entities.Project;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectInterface {

    Optional<Project> addProject(Project project);
    Optional<Project> getProject(UUID id);
    Optional<Project> updateProject(UUID id, Project project);
    boolean deleteProject(UUID id);
    List<Project> getAllProjects();
    List<Project> getProjectsByClientId(UUID clientId);
}
