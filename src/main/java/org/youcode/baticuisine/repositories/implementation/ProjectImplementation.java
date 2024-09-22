package org.youcode.baticuisine.repositories.implementation;

import org.youcode.baticuisine.db.DBConnection;
import org.youcode.baticuisine.entities.Project;
import org.youcode.baticuisine.repositories.interfaces.ProjectInterface;
import org.youcode.baticuisine.enums.ProjectState;
import org.youcode.baticuisine.entities.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProjectImplementation implements ProjectInterface {

    private final Connection conn;

    public ProjectImplementation() {
        conn = DBConnection.getInstance().establishConnection();
    }

    @Override
    public Optional<Project> addProject(Project project) {
        String insertSQL = "INSERT INTO project (id, ,name, profitMargin, totalCost, projectState, clientId) " +
                "VALUES (?, ?, ?, ?, ?::projectstate, ?::uuid)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
            preparedStatement.setObject(1, project.getId());
            preparedStatement.setString(2, project.getProjectName());
            preparedStatement.setDouble(3, project.getProfitMargin());
            preparedStatement.setDouble(4, project.getTotalCost());
            preparedStatement.setString(5, project.getProjectState().toString());
            preparedStatement.setObject(6, project.getClient().getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(project);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Project> getProject(UUID id) {
        String selectSQL = "SELECT * FROM project WHERE projectId = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setObject(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Project project = mapRowToProject(resultSet);
                    return Optional.of(project);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM project";
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(selectAllSQL)) {
            while (resultSet.next()) {
                projects.add(mapRowToProject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projects;
    }

    @Override
    public List<Project> getProjectsByClientId(UUID clientId) {
        List<Project> projects = new ArrayList<>();
        String selectSQL = "SELECT * FROM project WHERE clientId = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setObject(1, clientId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    projects.add(mapRowToProject(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projects;
    }

    @Override
    public boolean deleteProject(UUID projectId) {
        String deleteSQL = "DELETE FROM project WHERE projectId = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL)) {
            preparedStatement.setObject(1, projectId);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Project> updateProject(UUID projectId, Project project) {
        String updateSQL = "UPDATE project SET projectName = ?, profitMargin = ?, totalCost = ?, projectState = ?::projectstate, clientId = ?::uuid WHERE projectId = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setDouble(2, project.getProfitMargin());
            preparedStatement.setDouble(3, project.getTotalCost());
            preparedStatement.setString(4, project.getProjectState().toString());
            preparedStatement.setObject(5, project.getClient().getId());
            preparedStatement.setObject(6, projectId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(project);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Project mapRowToProject(ResultSet resultSet) throws SQLException {
        Project project = new Project();
        project.setId(UUID.fromString(resultSet.getString("projectId")));
        project.setProjectName(resultSet.getString("projectName"));
        project.setProfitMargin(resultSet.getDouble("profitMargin"));
        project.setTotalCost(resultSet.getDouble("totalCost"));
        project.setProjectState(ProjectState.valueOf(resultSet.getString("projectState")));
        project.setClient(new ClientImplementation().getClient(UUID.fromString(resultSet.getString("clientId"))).get());
        return project;
    }
}
