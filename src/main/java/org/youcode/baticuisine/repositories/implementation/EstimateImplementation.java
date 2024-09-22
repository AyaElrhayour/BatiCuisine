package org.youcode.baticuisine.repositories.implementation;

import org.youcode.baticuisine.db.DBConnection;
import org.youcode.baticuisine.entities.Estimate;
import org.youcode.baticuisine.repositories.interfaces.EstimateInterface;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class EstimateImplementation implements EstimateInterface {

    private final Connection conn;

    public EstimateImplementation() {
        conn = DBConnection.getInstance().establishConnection();
    }

    @Override
    public Optional<Estimate> createEstimate(Estimate estimate) {
        String insertSQL = "INSERT INTO estimate (id, estimatedAmount, issuedDate, validityDate, accepted, projectId) " +
                "VALUES (?, ?, ?, ?, ?, ?::uuid)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
            preparedStatement.setObject(1, estimate.getId());
            preparedStatement.setDouble(2, estimate.getEstimatedAmount());
            preparedStatement.setDate(3, Date.valueOf(estimate.getIssuedDate()));
            preparedStatement.setDate(4, Date.valueOf(estimate.getValidityDate()));
            preparedStatement.setBoolean(5, estimate.getAccepted());
            preparedStatement.setObject(6, estimate.getProject().getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(estimate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Estimate> getEstimateByProject(UUID projectId) {
        String selectSQL = "SELECT * FROM estimate WHERE projectId = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setObject(1, projectId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Estimate estimate = mapRowToEstimate(resultSet);
                    return Optional.of(estimate);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Estimate> validateEstimate(UUID id, Estimate estimate) {
        String updateSQL = "UPDATE estimate SET accepted = TRUE WHERE id = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)) {
            preparedStatement.setObject(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                return Optional.empty();
            }
            estimate.setAccepted(true);
            return Optional.of(estimate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Estimate mapRowToEstimate(ResultSet resultSet) throws SQLException {
        Estimate estimate = new Estimate();
        estimate.setId(UUID.fromString(resultSet.getString("id")));
        estimate.setEstimatedAmount(resultSet.getDouble("estimatedAmount"));
        estimate.setIssuedDate(resultSet.getDate("issuedDate").toLocalDate());
        estimate.setValidityDate(resultSet.getDate("validityDate").toLocalDate());
        estimate.setAccepted(resultSet.getBoolean("accepted"));
        estimate.setProject(new ProjectImplementation().getProject(UUID.fromString(resultSet.getString("projectId"))).get());
        return estimate;
    }
}
