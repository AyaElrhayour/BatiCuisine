package org.youcode.baticuisine.repositories.implementation;

import org.youcode.baticuisine.db.DBConnection;
import org.youcode.baticuisine.entities.Project;
import org.youcode.baticuisine.entities.Workforce;
import org.youcode.baticuisine.enums.ComponentType;
import org.youcode.baticuisine.repositories.interfaces.WorkforceInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class WorkforceImplementation implements WorkforceInterface {

    private final Connection conn;

    public WorkforceImplementation() {
        conn = DBConnection.getInstance().establishConnection();
    }

    // Helper method to map ResultSet to Workforce object
    private Workforce workforceResultSet(ResultSet resultSet) throws SQLException {
        Workforce workforce = new Workforce();
        workforce.setId(UUID.fromString(resultSet.getString("id")));
        workforce.setName(resultSet.getString("name"));
        workforce.setComponentType(ComponentType.WORKFORCE);
        workforce.setTvaRate(resultSet.getDouble("tvaRate"));
        workforce.setUnitaryPay(resultSet.getDouble("unitaryPay"));
        workforce.setQuantity(resultSet.getDouble("quantity"));
        workforce.setOutputFactor(resultSet.getDouble("outputFactor"));

        Project project = new Project();
        project.setId(UUID.fromString(resultSet.getString("projectId")));
        workforce.setProject(project);

        return workforce;
    }

    @Override
    public Optional<Workforce> addWorkforce(Workforce workforce) {
        String insertSQL = "INSERT INTO workforce (id, name, componentType, tvaRate, unitaryPay, quantity, " +
                "outputFactor, projectId) VALUES (?, ?, ?::componentType, ?, ?, ?, ?, ?::uuid)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
            preparedStatement.setObject(1, workforce.getId());
            preparedStatement.setString(2, workforce.getName());
            preparedStatement.setString(3, workforce.getComponentType().toString());
            preparedStatement.setDouble(4, workforce.getTvaRate());
            preparedStatement.setDouble(5, workforce.getUnitaryPay());
            preparedStatement.setDouble(6, workforce.getQuantity());
            preparedStatement.setDouble(7, workforce.getOutputFactor());
            preparedStatement.setObject(8, workforce.getProject().getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(workforce);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Workforce> getWorkforceByProject(UUID projectId) {
        List<Workforce> workforces = new ArrayList<>();
        String selectSQL = "SELECT * FROM workforce WHERE projectId = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setObject(1, projectId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    workforces.add(workforceResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }
}
