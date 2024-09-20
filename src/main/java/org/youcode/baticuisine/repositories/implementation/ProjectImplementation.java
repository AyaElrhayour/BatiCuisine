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

    private Project projectResultSet(ResultSet resultSet) throws SQLException {
        Project project = new Project();
        project.setId(UUID.fromString(resultSet.getString("id")));
        project.setProjectName(resultSet.getString("project_name"));
        project.setProfitMargin(resultSet.getDouble("profit_margin"));
        project.setTotalCost(resultSet.getDouble("total_cost"));
        project.setProjectState(ProjectState.valueOf(resultSet.getString("project_state")));

        Client client = new Client();
        client.setId(UUID.fromString(resultSet.getString("client_id")));
        project.setClient(client);

        return project;
    }

    @Override
    public Optional<Project> addProject(Project project) {
        String insertSQL = "INSERT INTO project (id, project_name, profit_margin, total_cost, project_state, client_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
            preparedStatement.setObject(1, project.getId());
            preparedStatement.setString(2, project.getProjectName());
            preparedStatement.setDouble(3, project.getProfitMargin());
            preparedStatement.setDouble(4, project.getTotalCost());
            preparedStatement.setString(5, project.getProjectState().name());
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
        String selectProjectSQL = "SELECT * FROM project WHERE id = ?";
        Project project = null;
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectProjectSQL)) {
            preparedStatement.setObject(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    project = projectResultSet(resultSet);
                    return Optional.of(project);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Project> updateProject(UUID id, Project project) {
        String updateSQL = "UPDATE project SET project_name = ?, profit_margin = ?, total_cost = ?, project_state = ?, client_id = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setDouble(2, project.getProfitMargin());
            preparedStatement.setDouble(3, project.getTotalCost());
            preparedStatement.setString(4, project.getProjectState().name());
            preparedStatement.setObject(5, project.getClient().getId());
            preparedStatement.setObject(6, project.getId());

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
    public boolean deleteProject(UUID id) {
        String deleteSQL = "DELETE FROM project WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL)) {
            preparedStatement.setObject(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM project";
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(selectAllSQL)) {
            while (resultSet.next()) {
                projects.add(projectResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projects;
    }

    @Override
    public List<Project> getProjectsByClientId(UUID clientId) {
        List<Project> projects = new ArrayList<>();
        String selectByClientSQL = "SELECT * FROM project WHERE client_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectByClientSQL)) {
            preparedStatement.setObject(1, clientId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    projects.add(projectResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projects;
    }


}
